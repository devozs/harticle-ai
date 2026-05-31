package com.devozs.components.common.repository;

import com.devozs.components.common.entity.flow.AsyncTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AsyncTaskRepository extends CrudRepository<AsyncTask, UUID> {
    List<AsyncTask> findAll();
}
