package com.brielmayer.teda.parser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import com.brielmayer.teda.exception.TedaException;
import com.brielmayer.teda.model.Document;

public interface Parser {

    String TEDA = "#Teda";
    String TABLE = "#Table";

    String COCKPIT = "Cockpit";

    /**
     * Parses a single-file spreadsheet from an input stream (XLSX, ODS). Parsers
     * that require a directory (like CSV) throw {@link UnsupportedOperationException}
     * here and must be invoked via {@link #parse(Path)}.
     */
    Document parse(InputStream inputStream);

    /**
     * Parses a spreadsheet from a filesystem path. The default implementation
     * opens the path as an input stream and delegates to {@link #parse(InputStream)}.
     * Directory-based parsers (CSV) override this method.
     */
    default Document parse(Path path) {
        try (InputStream in = Files.newInputStream(path)) {
            return parse(in);
        } catch (IOException e) {
            throw TedaException.builder()
                    .appendMessage("Unable to read file %s", path)
                    .cause(e)
                    .build();
        }
    }
}
