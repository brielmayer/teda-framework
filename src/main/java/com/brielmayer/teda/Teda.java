package com.brielmayer.teda;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.brielmayer.teda.configuration.TedaConfiguration;
import com.brielmayer.teda.database.DatabaseFactory;
import com.brielmayer.teda.exception.TedaException;
import com.brielmayer.teda.executor.TedaExecutor;
import com.brielmayer.teda.model.Document;
import com.brielmayer.teda.model.DocumentType;
import com.brielmayer.teda.parser.Parser;
import com.brielmayer.teda.parser.ParserFactory;

public class Teda {

    private final TedaConfiguration configuration;

    public Teda(final TedaConfiguration configuration) {
        this.configuration = configuration;
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
        TedaExecutor executor = TedaExecutor.builder()
                .loadDatabase(DatabaseFactory.createDatabase(configuration.getLoadDatabase()))
                .testDatabase(DatabaseFactory.createDatabase(configuration.getTestDatabase()))
                .truncateHandler(configuration.getTruncateHandler())
                .loadHandler(configuration.getLoadHandler())
                .executionHandler(configuration.getExecutionHandler())
                .testHandler(configuration.getTestHandler())
                .build();

        final Parser parser = ParserFactory.getParser(documentType);
        final Document document = parser.parse(inputStream);
        executor.execute(document);
    }
}
