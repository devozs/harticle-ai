package com.devozs.components.common.exception;

import com.devozs.components.common.domain.TaskStatus;

public class IllegalAsyncTaskUpdateException extends Exception {
    public IllegalAsyncTaskUpdateException() {
    }

    public IllegalAsyncTaskUpdateException(TaskStatus from, TaskStatus to) {
        super(getMessageFormat(from, to));
    }

    public IllegalAsyncTaskUpdateException(TaskStatus from, TaskStatus to, Throwable cause) {
        super(getMessageFormat(from, to), cause);
    }

    private static String getMessageFormat(TaskStatus from, TaskStatus to) {
        return String.format("can't update async task from %s status to %s status", from.getValue(), to.getValue());
    }
}
