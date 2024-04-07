package com.brielmayer.teda.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Builder
@Getter
@RequiredArgsConstructor
public class Sheet {
    private final String name;
    private final Map<String, Table> tables;
}
