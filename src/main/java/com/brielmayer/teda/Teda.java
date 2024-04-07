package com.brielmayer.teda;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseFactory;
import com.brielmayer.teda.exception.TedaException;
import com.brielmayer.teda.handler.ExecutionHandler;
import com.brielmayer.teda.handler.impl.LogExecutionHandler;
import com.brielmayer.teda.model.Document;
import com.brielmayer.teda.model.DocumentType;
import com.brielmayer.teda.parser.Parser;
import com.brielmayer.teda.parser.ParserFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Teda {

    private final BaseDatabase loadDatabase;
    private final BaseDatabase testDatabase;

    private final ExecutionHandler executionHandler;

    public Teda(final DataSource dataSource) {
        this(dataSource, dataSource, new LogExecutionHandler());
    }

    public Teda(final DataSource dataSource, final ExecutionHandler executionHandler) {
        this(dataSource, dataSource, executionHandler);
    }

    public Teda(final DataSource loadDataSource, final DataSource testDataSource, final ExecutionHandler executionHandler) {
        this.loadDatabase = DatabaseFactory.createDatabase(loadDataSource);
        this.testDatabase = DatabaseFactory.createDatabase(testDataSource);
        this.executionHandler = executionHandler;
    }

    public void execute(final String filePath) {
        execute(Paths.get(filePath));
    }

    public void execute(final Path filePath) {
        try {
            DocumentType documentType = DocumentType.fromPath(filePath);
            execute(Files.newInputStream(filePath), documentType);
        } catch (IOException e) {
            throw TedaException.builder()
                    .appendMessage("Unable to read file %s", filePath)
                    .cause(e)
                    .build();
        }
    }

    public void execute(final InputStream inputStream, DocumentType documentType) {
        final TedaExecutor executor = new TedaExecutor(loadDatabase, testDatabase, executionHandler);
        final Parser parser = ParserFactory.getParser(documentType);
        final Document document = parser.parse(inputStream);
        executor.execute(document);
    }
}
