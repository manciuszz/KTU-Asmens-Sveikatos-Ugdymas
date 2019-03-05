package biweekly.util;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

public class StringUtils {
    public static final String NEWLINE = System.getProperty("line.separator");

    public interface JoinCallback<T> {
        void handle(StringBuilder stringBuilder, T t);
    }

    public interface JoinMapCallback<K, V> {
        void handle(StringBuilder stringBuilder, K k, V v);
    }

    public static <T> String join(Collection<T> collection, String delimiter) {
        StringBuilder sb = new StringBuilder();
        join((Collection) collection, delimiter, sb);
        return sb.toString();
    }

    public static <T> void join(Collection<T> collection, String delimiter, StringBuilder sb) {
        join(collection, delimiter, sb, new JoinCallback<T>() {
            public void handle(StringBuilder sb, T value) {
                sb.append(value);
            }
        });
    }

    public static <T> String join(Collection<T> collection, String delimiter, JoinCallback<T> join) {
        StringBuilder sb = new StringBuilder();
        join(collection, delimiter, sb, join);
        return sb.toString();
    }

    public static <T> void join(Collection<T> collection, String delimiter, StringBuilder sb, JoinCallback<T> join) {
        boolean first = true;
        for (T element : collection) {
            if (first) {
                first = false;
            } else {
                sb.append(delimiter);
            }
            join.handle(sb, element);
        }
    }

    public static <K, V> String join(Map<K, V> map, String delimiter, final JoinMapCallback<K, V> join) {
        return join(map.entrySet(), delimiter, new JoinCallback<Entry<K, V>>() {
            public void handle(StringBuilder sb, Entry<K, V> entry) {
                join.handle(sb, entry.getKey(), entry.getValue());
            }
        });
    }

    private StringUtils() {
    }
}
