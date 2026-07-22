package com.brielmayer.teda.parser.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.brielmayer.teda.exception.TedaException;
import com.brielmayer.teda.model.Document;
import com.brielmayer.teda.model.Sheet;
import com.brielmayer.teda.parser.Parser;

/**
 * Reads a directory of {@code .csv} files as a {@link Document}. Each file
 * becomes one {@link Sheet}, with the file name (without extension) as the
 * sheet name. Cells within each file are read as raw strings; type detection
 * happens later during comparison.
 */
public class CsvDocumentParser implements Parser {

    private static final String CSV_EXTENSION = ".csv";

    @Override
    public Document parse(final InputStream inputStream) {
        throw new UnsupportedOperationException(
                "CSV requires a directory path. Call Teda.execute(Path) with a directory containing .csv files.");
    }

    @Override
    public Document parse(final Path directory) {
        if (!Files.isDirectory(directory)) {
            throw TedaException.builder()
                    .appendMessage("Expected a directory of .csv files, got: %s", directory)
                    .build();
        }

        final Map<String, Sheet> sheets = new HashMap<>();
        try (Stream<Path> paths = Files.list(directory)) {
            final List<Path> csvFiles = paths.filter(CsvDocumentParser::isCsvFile)
                    .sorted(Comparator.comparing(p -> p.getFileName().toString()))
                    .collect(java.util.stream.Collectors.toList());
            for (final Path csvFile : csvFiles) {
                final String sheetName = stripExtension(csvFile.getFileName().toString());
                final List<List<String>> grid = readGrid(csvFile);
                sheets.put(sheetName, CsvSheetParser.parseSheet(sheetName, grid));
            }
        } catch (final IOException e) {
            throw TedaException.builder()
                    .appendMessage("Unable to read directory %s", directory)
                    .cause(e)
                    .build();
        }
        return new Document(sheets);
    }

    private static boolean isCsvFile(final Path path) {
        return Files.isRegularFile(path)
                && path.getFileName().toString().toLowerCase().endsWith(CSV_EXTENSION);
    }

    private static String stripExtension(final String fileName) {
        final int dot = fileName.lastIndexOf('.');
        return dot < 0 ? fileName : fileName.substring(0, dot);
    }

    private static List<List<String>> readGrid(final Path csvFile) throws IOException {
        try (Reader reader = Files.newBufferedReader(csvFile, StandardCharsets.UTF_8);
                CSVParser parser = CSVFormat.DEFAULT.parse(reader)) {
            final List<List<String>> grid = new ArrayList<>();
            for (final CSVRecord record : parser) {
                final List<String> row = new ArrayList<>(record.size());
                for (int i = 0; i < record.size(); i++) {
                    row.add(record.get(i));
                }
                grid.add(row);
            }
            return grid;
        }
    }
}
