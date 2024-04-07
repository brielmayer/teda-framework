package com.brielmayer.teda.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@RequiredArgsConstructor
public class Table {
    private final String name;
    private final List<Header> headers;
    private final List<Map<String, Object>> data;
}
