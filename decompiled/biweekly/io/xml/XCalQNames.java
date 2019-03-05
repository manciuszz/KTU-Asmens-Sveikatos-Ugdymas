package biweekly.io.xml;

import javax.xml.namespace.QName;

public interface XCalQNames {
    public static final QName COMPONENTS = new QName(XCalNamespaceContext.XCAL_NS, "components");
    public static final QName ICALENDAR = new QName(XCalNamespaceContext.XCAL_NS, "icalendar");
    public static final QName PARAMETERS = new QName(XCalNamespaceContext.XCAL_NS, "parameters");
    public static final QName PROPERTIES = new QName(XCalNamespaceContext.XCAL_NS, "properties");
    public static final QName VCALENDAR = new QName(XCalNamespaceContext.XCAL_NS, "vcalendar");
}
