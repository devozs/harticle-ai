package com.devozs.components.common.service;

import com.devozs.components.common.domain.ErrorType;
import com.devozs.components.common.domain.TaskStatus;
import com.devozs.components.common.dto.datakubeservice.DataKubeServiceProtos;
import com.devozs.components.common.entity.flow.AsyncTask;
import com.devozs.components.common.exception.AsyncTaskIdNotExistException;
import com.devozs.components.common.exception.IllegalAsyncTaskUpdateException;
import com.devozs.components.common.repository.AsyncTaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class TaskManagementService {
    private final AsyncTaskRepository asyncTaskRepository;

    @Autowired
    public TaskManagementService(AsyncTaskRepository asyncTaskRepository) {
        this.asyncTaskRepository = asyncTaskRepository;
//        this.flowsService = flowsService;
    }

    public List<AsyncTask> findAll() {
        return asyncTaskRepository.findAll();
    }


    public AsyncTask createNewTask() {
        return createNewTask(null);
    }

    public AsyncTask createNewTask(DataKubeServiceProtos.DataKubeStep stepType) {
        AsyncTask asyncTask = new AsyncTask();
        asyncTask.setTaskStatus(TaskStatus.INITIALIZING);
        asyncTask.setStepType(stepType);
        return asyncTaskRepository.save(asyncTask);
    }

    public void deleteAsyncTask(UUID taskId) {
        asyncTaskRepository.deleteById(taskId);
    }

    public AsyncTask save(AsyncTask asyncTask) {
        return asyncTaskRepository.save(asyncTask);
    }

    public AsyncTask setTaskValidate(UUID taskId, boolean isValidated) throws AsyncTaskIdNotExistException{
        AsyncTask task = findById(taskId).orElseThrow(AsyncTaskIdNotExistException::new);
        task.setValid(isValidated);
        return save(task);
    }

    public AsyncTask failTask(AsyncTask task, String message, ErrorType errorType) {
        if (task.getTaskStatus() == TaskStatus.ERROR) {
            return task;
        }

        task.setErrorMessage(message);
        task.setProgress(100);
        task.setErrorType(errorType);
        task.setTaskStatus(TaskStatus.ERROR);
        return save(task);
    }

    public AsyncTask failTask(UUID taskId, String message, ErrorType errorType) throws AsyncTaskIdNotExistException {
        return failTask(findById(taskId).orElseThrow(AsyncTaskIdNotExistException::new), message, errorType);
    }


    public Optional<AsyncTask> findById(UUID asyncTaskId) {
        return asyncTaskRepository.findById(asyncTaskId);
    }

    public void updateAsyncTaskProgress(UUID asyncTaskId, int progress) throws AsyncTaskIdNotExistException {
        AsyncTask asyncTask = findById(asyncTaskId).orElseThrow(() -> asyncTaskIdNotFound(asyncTaskId));
        asyncTask.setProgress(progress);

        if (asyncTask.getTaskStatus().equals(TaskStatus.INITIALIZING)) {
            asyncTask.setTaskStatus(TaskStatus.IN_PROGRESS);
        }

        save(asyncTask);
    }

    public void updateAsyncTaskStatus(UUID asyncTaskId, TaskStatus status) throws AsyncTaskIdNotExistException {
        AsyncTask asyncTask = findById(asyncTaskId).orElseThrow(() -> asyncTaskIdNotFound(asyncTaskId));
        asyncTask.setTaskStatus(status);
        save(asyncTask);
    }

    public void updateAsyncTaskCompleted(UUID asyncTaskId) throws AsyncTaskIdNotExistException, IllegalAsyncTaskUpdateException {
        AsyncTask asyncTask = findById(asyncTaskId).orElseThrow(() -> asyncTaskIdNotFound(asyncTaskId));
        if (asyncTask.getTaskStatus().equals(TaskStatus.COMPLETED)) {
            log.info(String.format("Task %s is already completed", asyncTaskId));
            return;
        }
        if (asyncTask.getTaskStatus().equals(TaskStatus.IN_PROGRESS)) {
            asyncTask.setTaskStatus(TaskStatus.COMPLETED);
        } else {
            throw new IllegalAsyncTaskUpdateException(asyncTask.getTaskStatus(), TaskStatus.COMPLETED);
        }
        save(asyncTask);

    }

    private AsyncTaskIdNotExistException asyncTaskIdNotFound(UUID asyncTaskId) {
        String message = "Can't find Async Task with id: " + asyncTaskId;
        log.error(message);
        return new AsyncTaskIdNotExistException(message);
    }
}
