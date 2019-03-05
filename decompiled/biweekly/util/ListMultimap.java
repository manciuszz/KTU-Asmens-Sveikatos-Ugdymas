package biweekly.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ListMultimap<K, V> implements Iterable<Entry<K, List<V>>> {
    private final Map<K, List<V>> map;

    public ListMultimap() {
        this.map = new LinkedHashMap();
    }

    public ListMultimap(int initialCapacity) {
        this.map = new LinkedHashMap(initialCapacity);
    }

    public ListMultimap(ListMultimap<K, V> orig) {
        this(orig.map);
    }

    public ListMultimap(Map<K, List<V>> orig) {
        this();
        for (Entry<K, List<V>> entry : orig.entrySet()) {
            this.map.put(entry.getKey(), new ArrayList((Collection) entry.getValue()));
        }
    }

    public void put(K key, V value) {
        get(key, true).add(value);
    }

    public void putAll(K key, Collection<V> values) {
        get(key, true).addAll(values);
    }

    public List<V> get(K key) {
        return get(key, false);
    }

    private List<V> get(K key, boolean add) {
        key = sanitizeKey(key);
        List<V> values = (List) this.map.get(key);
        if (values == null) {
            values = new ArrayList();
            if (add) {
                this.map.put(key, values);
            }
        }
        return values;
    }

    public V first(K key) {
        List<V> values = get(key);
        return (values == null || values.isEmpty()) ? null : values.get(0);
    }

    public boolean containsKey(K key) {
        return this.map.containsKey(key);
    }

    public boolean remove(K key, V value) {
        List<V> values = (List) this.map.get(sanitizeKey(key));
        if (values != null) {
            return values.remove(value);
        }
        return false;
    }

    public List<V> removeAll(K key) {
        List<V> removed = (List) this.map.remove(sanitizeKey(key));
        return removed == null ? Collections.emptyList() : removed;
    }

    public List<V> replace(K key, V value) {
        List<V> replaced = removeAll(key);
        if (value != null) {
            put(key, value);
        }
        return replaced;
    }

    public List<V> replace(K key, Collection<V> values) {
        List<V> replaced = removeAll(key);
        if (!(values == null || values.isEmpty())) {
            putAll(key, values);
        }
        return replaced;
    }

    public void clear() {
        this.map.clear();
    }

    public Set<K> keySet() {
        return this.map.keySet();
    }

    public List<V> values() {
        List<V> list = new ArrayList();
        for (List<V> value : this.map.values()) {
            list.addAll(value);
        }
        return list;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        int size = 0;
        for (List<V> value : this.map.values()) {
            size += value.size();
        }
        return size;
    }

    public Map<K, List<V>> getMap() {
        return this.map;
    }

    protected K sanitizeKey(K key) {
        return key;
    }

    public Iterator<Entry<K, List<V>>> iterator() {
        return this.map.entrySet().iterator();
    }

    public String toString() {
        return this.map.toString();
    }

    public int hashCode() {
        return this.map.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.map.equals(((ListMultimap) obj).map);
    }
}
