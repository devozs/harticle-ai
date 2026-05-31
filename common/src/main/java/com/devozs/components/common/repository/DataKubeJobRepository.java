package com.devozs.components.common.repository;

import com.devozs.components.common.entity.DataKubeJob;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DataKubeJobRepository extends CrudRepository<DataKubeJob, UUID> {
    List<DataKubeJob> findByAsyncTaskIdIn(List<UUID> taskIds);
}
