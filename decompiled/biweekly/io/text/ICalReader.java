package biweekly.io.text;

import biweekly.ICalDataType;
import biweekly.ICalendar;
import biweekly.Warning;
import biweekly.component.ICalComponent;
import biweekly.io.CannotParseException;
import biweekly.io.ParseWarnings;
import biweekly.io.SkipMeException;
import biweekly.io.scribe.ScribeIndex;
import biweekly.io.scribe.component.ICalComponentScribe;
import biweekly.io.scribe.component.ICalendarScribe;
import biweekly.io.scribe.property.ICalPropertyScribe;
import biweekly.io.scribe.property.ICalPropertyScribe.Result;
import biweekly.io.scribe.property.RawPropertyScribe;
import biweekly.parameter.ICalParameters;
import biweekly.property.ICalProperty;
import biweekly.util.IOUtils;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class ICalReader implements Closeable {
    private static final String icalComponentName = icalMarshaller.getComponentName();
    private static final ICalendarScribe icalMarshaller = ScribeIndex.getICalendarScribe();
    private ScribeIndex index;
    private final ICalRawReader reader;
    private final ParseWarnings warnings;

    public ICalReader(String string) {
        this(new StringReader(string));
    }

    public ICalReader(InputStream in) {
        this(IOUtils.utf8Reader(in));
    }

    public ICalReader(File file) throws FileNotFoundException {
        this(IOUtils.utf8Reader(file));
    }

    public ICalReader(Reader reader) {
        this.warnings = new ParseWarnings();
        this.index = new ScribeIndex();
        this.reader = new ICalRawReader(reader);
    }

    public boolean isCaretDecodingEnabled() {
        return this.reader.isCaretDecodingEnabled();
    }

    public void setCaretDecodingEnabled(boolean enable) {
        this.reader.setCaretDecodingEnabled(enable);
    }

    public void registerScribe(ICalPropertyScribe<? extends ICalProperty> scribe) {
        this.index.register((ICalPropertyScribe) scribe);
    }

    public void registerScribe(ICalComponentScribe<? extends ICalComponent> scribe) {
        this.index.register((ICalComponentScribe) scribe);
    }

    public ScribeIndex getScribeIndex() {
        return this.index;
    }

    public void setScribeIndex(ScribeIndex index) {
        this.index = index;
    }

    public List<String> getWarnings() {
        return this.warnings.copy();
    }

    public ICalendar readNext() throws IOException {
        this.warnings.clear();
        boolean dataWasRead = false;
        List<ICalProperty> orphanedProperties = new ArrayList();
        List<ICalComponent> orphanedComponents = new ArrayList();
        List<ICalComponent> componentStack = new ArrayList();
        List<String> componentNamesStack = new ArrayList();
        while (true) {
            ICalComponent component;
            Iterator it;
            ICalProperty property;
            try {
                ICalRawLine line = this.reader.readLine();
                if (line == null) {
                    break;
                }
                String propertyName = line.getName();
                String componentName;
                if ("BEGIN".equalsIgnoreCase(propertyName)) {
                    componentName = line.getValue();
                    dataWasRead = true;
                    ICalComponent parentComponent = componentStack.isEmpty() ? null : (ICalComponent) componentStack.get(componentStack.size() - 1);
                    component = this.index.getComponentScribe(componentName).emptyInstance();
                    componentStack.add(component);
                    componentNamesStack.add(componentName);
                    if (parentComponent == null) {
                        orphanedComponents.add(component);
                    } else {
                        parentComponent.addComponent(component);
                    }
                } else if ("END".equalsIgnoreCase(propertyName)) {
                    componentName = line.getValue();
                    if (icalComponentName.equalsIgnoreCase(componentName)) {
                        break;
                    }
                    int popIndex = -1;
                    for (int i = componentStack.size() - 1; i >= 0; i--) {
                        if (((String) componentNamesStack.get(i)).equalsIgnoreCase(componentName)) {
                            popIndex = i;
                            break;
                        }
                    }
                    if (popIndex == -1) {
                        this.warnings.add(Integer.valueOf(this.reader.getLineNum()), "END", 2, new Object[0]);
                    } else {
                        componentStack.subList(popIndex, componentStack.size()).clear();
                        componentNamesStack.subList(popIndex, componentNamesStack.size()).clear();
                    }
                } else {
                    dataWasRead = true;
                    ICalParameters parameters = line.getParameters();
                    it = parameters.iterator();
                    while (it.hasNext()) {
                        Entry<String, List<String>> entry = (Entry) it.next();
                        for (String value : (List) entry.getValue()) {
                            if (value == null) {
                                String paramName = (String) entry.getKey();
                                this.warnings.add(Integer.valueOf(this.reader.getLineNum()), propertyName, 4, paramName);
                                break;
                            }
                        }
                    }
                    ICalPropertyScribe<? extends ICalProperty> marshaller = this.index.getPropertyScribe(propertyName);
                    ICalDataType dataType = parameters.getValue();
                    if (dataType == null) {
                        dataType = marshaller.getDefaultDataType();
                    } else {
                        parameters.setValue(null);
                    }
                    String value2 = line.getValue();
                    try {
                        Result<? extends ICalProperty> result = marshaller.parseText(value2, dataType, parameters);
                        for (Warning warning : result.getWarnings()) {
                            this.warnings.add(Integer.valueOf(this.reader.getLineNum()), propertyName, warning);
                        }
                        property = result.getProperty();
                    } catch (SkipMeException e) {
                        this.warnings.add(Integer.valueOf(this.reader.getLineNum()), propertyName, 0, e.getMessage());
                    } catch (CannotParseException e2) {
                        this.warnings.add(Integer.valueOf(this.reader.getLineNum()), propertyName, 1, value2, e2.getMessage());
                        property = new RawPropertyScribe(propertyName).parseText(value2, dataType, parameters).getProperty();
                    }
                    if (componentStack.isEmpty()) {
                        orphanedProperties.add(property);
                    } else {
                        ((ICalComponent) componentStack.get(componentStack.size() - 1)).addProperty(property);
                    }
                }
            } catch (ICalParseException e3) {
                this.warnings.add(Integer.valueOf(this.reader.getLineNum()), null, 3, e3.getLine());
            }
        }
        if (!dataWasRead) {
            return null;
        }
        ICalendar ical;
        if (orphanedComponents.isEmpty()) {
            ical = (ICalendar) icalMarshaller.emptyInstance();
        } else {
            ICalComponent first = (ICalComponent) orphanedComponents.get(0);
            if (first instanceof ICalendar) {
                ical = (ICalendar) first;
            } else {
                ical = (ICalendar) icalMarshaller.emptyInstance();
                for (ICalComponent component2 : orphanedComponents) {
                    ical.addComponent(component2);
                }
            }
        }
        for (ICalProperty property2 : orphanedProperties) {
            ical.addProperty(property2);
        }
        return ical;
    }

    public void close() throws IOException {
        this.reader.close();
    }
}
