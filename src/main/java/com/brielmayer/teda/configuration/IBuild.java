package com.brielmayer.teda.configuration;

import com.brielmayer.teda.handler.IExecutionHandler;
import com.brielmayer.teda.handler.ILoadHandler;
import com.brielmayer.teda.handler.ITestHandler;
import com.brielmayer.teda.handler.ITruncateHandler;

/**
 * Terminal stage of the {@link TedaConfiguration} fluent builder. Optional
 * handler overrides can be chained before {@link #build()} produces the final
 * configuration.
 */
public interface IBuild {

    IBuild withTruncateHandler(ITruncateHandler val);

    IBuild withLoadHandler(ILoadHandler val);

    IBuild withExecutionHandler(IExecutionHandler val);

    IBuild withTestHandler(ITestHandler val);

    TedaConfiguration build();
}
