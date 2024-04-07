package com.brielmayer.teda.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class Document {
    private final Map<String, Sheet> sheets;
}
