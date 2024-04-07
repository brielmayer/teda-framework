package com.brielmayer.teda.handler.impl;

import com.brielmayer.teda.handler.ExecutionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link ExecutionHandler} that logs the value to the console.
 */
@Slf4j
public class LogExecutionHandler implements ExecutionHandler {

    @Override
    public void execute(final String value) {
        log.info("Execute: {}", value);
    }
}
