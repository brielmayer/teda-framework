package com.brielmayer.teda.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class LinkedCaseInsensitiveMap<V> extends LinkedHashMap<String, V> {

    private static final long serialVersionUID = -7492923676944145770L;

    private Map<String, String> caseInsensitiveKeys;
    private final Locale locale;

    public LinkedCaseInsensitiveMap() {
        this(null);
    }

    public LinkedCaseInsensitiveMap(final Locale locale) {
        this.caseInsensitiveKeys = new HashMap<>();
        this.locale = locale != null ? locale : Locale.getDefault();
    }

    public LinkedCaseInsensitiveMap(final int initialCapacity) {
        this(initialCapacity, null);
    }

    public LinkedCaseInsensitiveMap(final int initialCapacity, final Locale locale) {
        super(initialCapacity);
        this.caseInsensitiveKeys = new HashMap<>(initialCapacity);
        this.locale = locale != null ? locale : Locale.getDefault();
    }

    public V put(final String key, final V value) {
        final String oldKey = this.caseInsensitiveKeys.put(this.convertKey(key), key);
        if (oldKey != null && !oldKey.equals(key)) {
            super.remove(oldKey);
        }

        return super.put(key, value);
    }

    public void putAll(final Map<? extends String, ? extends V> map) {
        if (!map.isEmpty()) {
            final Iterator var2 = map.entrySet().iterator();

            while (var2.hasNext()) {
                final Map.Entry<? extends String, ? extends V> entry = (Map.Entry) var2.next();
                this.put(entry.getKey(), entry.getValue());
            }

        }
    }

    public boolean containsKey(final Object key) {
        return key instanceof String && this.caseInsensitiveKeys.containsKey(this.convertKey((String) key));
    }

    public V get(final Object key) {
        if (key instanceof String) {
            final String caseInsensitiveKey = this.caseInsensitiveKeys.get(this.convertKey((String) key));
            if (caseInsensitiveKey != null) {
                return super.get(caseInsensitiveKey);
            }
        }

        return null;
    }

    public V getOrDefault(final Object key, final V defaultValue) {
        if (key instanceof String) {
            final String caseInsensitiveKey = this.caseInsensitiveKeys.get(this.convertKey((String) key));
            if (caseInsensitiveKey != null) {
                return super.get(caseInsensitiveKey);
            }
        }

        return defaultValue;
    }

    public V remove(final Object key) {
        if (key instanceof String) {
            final String caseInsensitiveKey = this.caseInsensitiveKeys.remove(this.convertKey((String) key));
            if (caseInsensitiveKey != null) {
                return super.remove(caseInsensitiveKey);
            }
        }

        return null;
    }

    public void clear() {
        this.caseInsensitiveKeys.clear();
        super.clear();
    }

    public Object clone() {
        final LinkedCaseInsensitiveMap<V> copy = (LinkedCaseInsensitiveMap) super.clone();
        copy.caseInsensitiveKeys = new HashMap<>(this.caseInsensitiveKeys);
        return copy;
    }

    protected String convertKey(final String key) {
        return key.toLowerCase(this.locale);
    }
}
