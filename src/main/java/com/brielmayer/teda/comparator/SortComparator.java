package com.brielmayer.teda.comparator;

import com.brielmayer.teda.exception.TedaException;
import com.brielmayer.teda.model.Header;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class SortComparator implements Comparator<Map<String, Object>>, Serializable {

    private static final long serialVersionUID = 6561977888664706224L;

    private final List<Header> primaryKeys;

    @Override
    @SuppressWarnings("ComparatorMethodParameterNotUsed")
    public int compare(final Map<String, Object> o1, final Map<String, Object> o2) {
        for (final Header key : primaryKeys) {

            // check if both objects have the same data type
            if (!o1.getClass().getSimpleName().equals(o2.getClass().getSimpleName())) {
                throw TedaException.builder()
                        .appendMessage("Unable to sort data")
                        .appendMessage("Data types are not equal within primary key \"%s\"", key.getName())
                        .appendMessage("Can not sort by comparing \"%s\" with \"%s\"", o1.getClass().getSimpleName(), o2.getClass().getSimpleName())
                        .build();
            }

            final int result = o1.toString().compareTo(o2.toString());
            if (result == 0) {
                // sort result not unique
                // so continue sorting by next primary key
                continue;
            } else {
                return result;
            }
        }

        throw TedaException.builder()
                .appendMessage("Unable to sort data")
                .appendMessage("Primary keys are not unique when using")
                .appendMessage("ORDER BY %s", primaryKeys.toString())
                .build();
    }
}
