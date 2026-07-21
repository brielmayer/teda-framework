package com.brielmayer.teda.handler.impl;

import com.brielmayer.teda.handler.IExecutionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of {@link IExecutionHandler} that logs the value to the console.
 */
@Slf4j
public class LogExecutionHandler implements IExecutionHandler {

    @Override
    public void execute(final String value) {
        log.info("Execute: {}", value);
    }
}
