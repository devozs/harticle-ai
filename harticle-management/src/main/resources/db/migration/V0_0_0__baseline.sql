create table if not exists flyway_schema_history
(
    installed_rank integer                 not null
    constraint flyway_schema_history_pk
    primary key,
    version        varchar(50),
    description    varchar(200)            not null,
    type           varchar(20)             not null,
    script         varchar(1000)           not null,
    checksum       integer,
    installed_by   varchar(100)            not null,
    installed_on   timestamp default now() not null,
    execution_time integer                 not null,
    success        boolean                 not null
    );

create index if not exists flyway_schema_history_s_idx
    on flyway_schema_history (success);


create table if not exists article
(
    id uuid not null
        constraint article_pkey
        primary key,
    created_at   timestamp,
    updated_at   timestamp,
    version      bigint,
    keywords     varchar(255),
    title        varchar(255),
    sub_title    varchar(255),
    content      varchar(2048),
    completed  boolean not null,
    faulted    boolean not null,
    reporter     integer,
    article_type integer,
    votes        integer default 0,
    image        varchar(255),
    temperature integer default 0.5
);

create table if not exists data_kube_job
(
    id uuid not null
        constraint data_kube_job_pkey
        primary key,
    created_at timestamp,
    updated_at timestamp,
    version bigint,
--     articleid uuid,
    asynctaskid uuid,
    callerfilter varchar(255),
    kubernetesjobid varchar(255),
    securityidentifier uuid
);

create table if not exists asynctask
(
    id uuid not null
        constraint asynctask_pkey
            primary key,
    created_at timestamp,
    updated_at timestamp,
    version bigint,
    errormessage varchar(255),
    errortype integer,
    progress integer not null,
    taskstatus integer,
    tasktype integer
);