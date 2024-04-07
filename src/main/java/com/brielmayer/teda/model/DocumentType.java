package com.brielmayer.teda.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum DocumentType {
    EXCEL("xlsx"),
    OPEN_DOCUMENT_SPREADSHEET("ods");

    private final String extension;

    public static DocumentType fromPath(Path path) {
        return fromFileExtension(FilenameUtils.getExtension(path.toString()));
    }

    public static DocumentType fromFileExtension(String extension) {
        return Arrays.stream(DocumentType.values())
                .filter(documentType -> documentType.getExtension().equals(extension.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown file extension: " + extension));
    }
}
