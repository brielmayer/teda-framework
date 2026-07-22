package com.brielmayer.teda.executor;

import com.brielmayer.teda.model.Action;

/**
 * One parsed cell from the cockpit table: an {@link Action} and the value it
 * applies to (a table name, sheet name, or handler argument, depending on the
 * action).
 */
final class Command {

    private final Action action;
    private final String value;

    Command(final Action action, final String value) {
        this.action = action;
        this.value = value;
    }

    Action getAction() {
        return action;
    }

    String getValue() {
        return value;
    }
}
