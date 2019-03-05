package biweekly.io.json;

import biweekly.util.ListMultimap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class JCalValue {
    private final List<JsonValue> values;

    public JCalValue(List<JsonValue> values) {
        this.values = Collections.unmodifiableList(values);
    }

    public JCalValue(JsonValue... values) {
        this.values = Arrays.asList(values);
    }

    public static JCalValue single(Object value) {
        return new JCalValue(new JsonValue(value));
    }

    public static JCalValue multi(Object... values) {
        return multi(Arrays.asList(values));
    }

    public static JCalValue multi(List<?> values) {
        List multiValues = new ArrayList(values.size());
        for (Object value : values) {
            multiValues.add(new JsonValue(value));
        }
        return new JCalValue(multiValues);
    }

    public static JCalValue structured(Object... values) {
        List valuesList = new ArrayList(values.length);
        for (Object value : values) {
            List<?> list;
            if (value instanceof List) {
                list = (List) value;
            } else {
                list = Arrays.asList(new Object[]{value});
            }
            valuesList.add(list);
        }
        return structured(valuesList);
    }

    public static JCalValue structured(List<List<?>> values) {
        List array = new ArrayList(values.size());
        for (List<?> list : values) {
            if (list.isEmpty()) {
                array.add(new JsonValue((Object) ""));
            } else if (list.size() == 1) {
                value = list.get(0);
                if (value == null) {
                    value = "";
                }
                array.add(new JsonValue(value));
            } else {
                List subArray = new ArrayList(list.size());
                for (Object value : list) {
                    if (value == null) {
                        value = "";
                    }
                    subArray.add(new JsonValue(value));
                }
                array.add(new JsonValue(subArray));
            }
        }
        return new JCalValue(new JsonValue(array));
    }

    public static JCalValue object(ListMultimap<String, Object> value) {
        Map object = new LinkedHashMap();
        Iterator it = value.iterator();
        while (it.hasNext()) {
            JsonValue v;
            Entry<String, List<Object>> entry = (Entry) it.next();
            String key = (String) entry.getKey();
            List<Object> list = (List) entry.getValue();
            if (list.size() == 1) {
                v = new JsonValue(list.get(0));
            } else {
                List array = new ArrayList(list.size());
                for (Object element : list) {
                    array.add(new JsonValue(element));
                }
                v = new JsonValue(array);
            }
            object.put(key, v);
        }
        return new JCalValue(new JsonValue(object));
    }

    public List<JsonValue> getValues() {
        return this.values;
    }

    public String asSingle() {
        if (this.values.isEmpty()) {
            return "";
        }
        JsonValue first = (JsonValue) this.values.get(0);
        if (first.isNull()) {
            return "";
        }
        Object obj = first.getValue();
        if (obj != null) {
            return obj.toString();
        }
        List<JsonValue> array = first.getArray();
        if (!(array == null || array.isEmpty())) {
            obj = ((JsonValue) array.get(0)).getValue();
            if (obj != null) {
                return obj.toString();
            }
        }
        return "";
    }

    public List<List<String>> asStructured() {
        if (this.values.isEmpty()) {
            return Collections.emptyList();
        }
        JsonValue first = (JsonValue) this.values.get(0);
        List<JsonValue> array = first.getArray();
        Object obj;
        if (array != null) {
            List<List<String>> valuesStr = new ArrayList(array.size());
            for (JsonValue value : array) {
                if (value.isNull()) {
                    valuesStr.add(Arrays.asList(new String[]{""}));
                } else {
                    if (value.getValue() != null) {
                        valuesStr.add(Arrays.asList(new String[]{value.getValue().toString()}));
                    } else {
                        List<JsonValue> subArray = value.getArray();
                        if (subArray != null) {
                            List<String> subValuesStr = new ArrayList(subArray.size());
                            for (JsonValue subArrayValue : subArray) {
                                if (subArrayValue.isNull()) {
                                    subValuesStr.add("");
                                } else {
                                    obj = subArrayValue.getValue();
                                    if (obj != null) {
                                        subValuesStr.add(obj.toString());
                                    }
                                }
                            }
                            valuesStr.add(subValuesStr);
                        }
                    }
                }
            }
            return valuesStr;
        }
        List<List<String>> values;
        if (first.getValue() != null) {
            values = new ArrayList(1);
            values.add(Arrays.asList(new String[]{obj.toString()}));
            return values;
        } else if (!first.isNull()) {
            return Collections.emptyList();
        } else {
            values = new ArrayList(1);
            values.add(Arrays.asList(new String[]{""}));
            return values;
        }
    }

    public List<String> asMulti() {
        if (this.values.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> multi = new ArrayList(this.values.size());
        for (JsonValue value : this.values) {
            if (value.isNull()) {
                multi.add("");
            } else {
                Object obj = value.getValue();
                if (obj != null) {
                    multi.add(obj.toString());
                }
            }
        }
        return multi;
    }

    public ListMultimap<String, String> asObject() {
        if (this.values.isEmpty()) {
            return new ListMultimap(0);
        }
        Map<String, JsonValue> map = ((JsonValue) this.values.get(0)).getObject();
        if (map == null) {
            return new ListMultimap(0);
        }
        ListMultimap<String, String> values = new ListMultimap();
        for (Entry<String, JsonValue> entry : map.entrySet()) {
            String key = (String) entry.getKey();
            JsonValue value = (JsonValue) entry.getValue();
            if (value.isNull()) {
                values.put(key, "");
            } else {
                Object obj = value.getValue();
                if (obj != null) {
                    values.put(key, obj.toString());
                } else {
                    List<JsonValue> array = value.getArray();
                    if (array != null) {
                        for (JsonValue element : array) {
                            if (element.isNull()) {
                                values.put(key, "");
                            } else {
                                obj = element.getValue();
                                if (obj != null) {
                                    values.put(key, obj.toString());
                                }
                            }
                        }
                    }
                }
            }
        }
        return values;
    }
}
