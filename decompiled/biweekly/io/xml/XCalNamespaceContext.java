package biweekly.io.xml;

import java.util.Arrays;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;

public class XCalNamespaceContext implements NamespaceContext {
    public static final String XCAL_NS = "urn:ietf:params:xml:ns:icalendar-2.0";
    private final String prefix;

    public XCalNamespaceContext(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getNamespaceURI(String prefix) {
        if (this.prefix.equals(prefix)) {
            return XCAL_NS;
        }
        return null;
    }

    public String getPrefix(String ns) {
        if (XCAL_NS.equals(ns)) {
            return this.prefix;
        }
        return null;
    }

    public Iterator<String> getPrefixes(String ns) {
        if (!XCAL_NS.equals(ns)) {
            return null;
        }
        return Arrays.asList(new String[]{this.prefix}).iterator();
    }
}
