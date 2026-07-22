package com.brielmayer.teda.executor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.brielmayer.teda.exception.TedaException;
import com.brielmayer.teda.model.Action;
import com.brielmayer.teda.model.Document;
import com.brielmayer.teda.model.Table;
import com.brielmayer.teda.parser.Parser;

/**
 * Extracts the cockpit table from a {@link Document} and turns its cells into
 * an ordered list of {@link Command}s. Empty cells are skipped. Unknown action
 * names throw a {@link TedaException} with the list of valid actions.
 */
final class CockpitReader {

    List<Command> read(final Document document) {
        final Table cockpit = document.getSheets()
                .get(Parser.COCKPIT)
                .getTables()
                .values()
                .iterator()
                .next();

        final List<Command> commands = new ArrayList<>();
        for (final Map<String, Object> row : cockpit.getData()) {
            for (final Map.Entry<String, Object> entry : row.entrySet()) {
                // cockpit must only contain strings
                final String header = entry.getKey();
                final String value = (String) entry.getValue();

                // if cell is empty, no action will be performed
                if (value == null || value.isEmpty()) {
                    continue;
                }

                commands.add(new Command(parseAction(header), value));
            }
        }
        return commands;
    }

    private static Action parseAction(final String header) {
        try {
            return Action.valueOf(header);
        } catch (final IllegalArgumentException e) {
            throw TedaException.builder()
                    .appendMessage("Unknown cockpit action \"%s\"", header)
                    .appendMessage("Valid actions are: %s", Arrays.toString(Action.values()))
                    .cause(e)
                    .build();
        }
    }
}
