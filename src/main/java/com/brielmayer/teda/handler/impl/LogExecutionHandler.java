package com.brielmayer.teda.handler.impl;

import com.brielmayer.teda.handler.IExecutionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of {@link IExecutionHandler} that logs the value to the console.
 */
public class LogExecutionHandler implements IExecutionHandler {

    private static final Logger log = LoggerFactory.getLogger(LogExecutionHandler.class);

    @Override
    public void execute(final String value) {
        log.info("Execute: {}", value);
    }
}
