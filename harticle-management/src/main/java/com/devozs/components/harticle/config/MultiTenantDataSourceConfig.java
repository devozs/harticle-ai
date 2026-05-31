package com.devozs.components.harticle.config;


import com.devozs.components.common.dto.TenantDatasourceConfigDto;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
public class MultiTenantDataSourceConfig implements MultiTenantConnectionProvider {
    private transient DataSource dataSource;
    private transient Cache<TenantDatasourceConfigDto, Boolean> tenantDescriptionCache;
    private transient boolean migrationEnable;
    private transient AtomicReference<Map<String, HikariDataSource>> dataSources;
    private final transient DatasourceConfig datasourceConfig;

    @Autowired
    public MultiTenantDataSourceConfig(DataSource dataSource,
                                       @Value("${multi-tenant.enable}") final boolean migrationEnable,
                                       DatasourceConfig datasourceConfig) {
        this.dataSource = dataSource;
        this.tenantDescriptionCache = Caffeine.newBuilder()
                .expireAfterAccess(1, TimeUnit.DAYS)
                .build();
        this.migrationEnable = migrationEnable;
        this.datasourceConfig = datasourceConfig;
        dataSources = new AtomicReference<>(new HashMap<>());
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    // Ignore param and use context instead
    @Override
    public Connection getConnection(Object tenantId) throws SQLException {
/*
        try {
            TenantDatasourceConfigDto tenantDatasourceConfigDto = securityUtils.getTenantDatasourceConfigDto();

            if (tenantDatasourceConfigDto == null) {
                throw new NullPointerException("tenantDatasourceConfigDto is invalid");
            }
            return checkMigration(tenantDatasourceConfigDto).getConnection();

        } catch (NullPointerException e) {
            String errorMessage = String.format("tenant id: %s doesn't exist or invalid.", tenantId);
            log.error(errorMessage);
            return getAnyConnection();
        }
*/
        return getAnyConnection();
    }

    private DataSource checkMigration(TenantDatasourceConfigDto tenantDatasourceConfigDto) {
        if (!migrationEnable ) {
            return dataSource;
        }

        tenantDescriptionCache.put(tenantDatasourceConfigDto, Boolean.TRUE);
        return  migrate(tenantDatasourceConfigDto);
    }

    private DataSource migrate(TenantDatasourceConfigDto tenantDatasourceConfigDto) {
        HikariDataSource hikariDataSource = dataSources.get().get(tenantDatasourceConfigDto.getRealmName());
        if(hikariDataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(tenantDatasourceConfigDto.getConnectionString());
            config.setUsername(tenantDatasourceConfigDto.getUsername());
            config.setPassword(tenantDatasourceConfigDto.getPassword());
            config.setDriverClassName(tenantDatasourceConfigDto.getDriver());
            config.setIdleTimeout(datasourceConfig.getIdleTimeout());
            config.setMaximumPoolSize(datasourceConfig.getMaxPoolSize());
            config.setMinimumIdle(datasourceConfig.getMinIdle());
            hikariDataSource = new HikariDataSource(config);

            Flyway flyway = Flyway.configure()
                    .baselineOnMigrate(true)
                    .ignoreMigrationPatterns("*:ignored")
                    .placeholders(Map.of("postgres_user", datasourceConfig.getUserName()))
                    .dataSource(hikariDataSource)
                    .connectRetries(10)
                    .load();

            flyway.migrate();
            dataSources.get().put(tenantDatasourceConfigDto.getRealmName(), hikariDataSource);
        }
        return hikariDataSource;
    }

    @Override
    public void releaseConnection(Object tenantIdentifier, Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }
}