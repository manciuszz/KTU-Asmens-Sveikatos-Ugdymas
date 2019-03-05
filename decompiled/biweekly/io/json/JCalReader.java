package biweekly.io.json;

import biweekly.ICalDataType;
import biweekly.ICalendar;
import biweekly.Warning;
import biweekly.component.ICalComponent;
import biweekly.io.CannotParseException;
import biweekly.io.ParseWarnings;
import biweekly.io.SkipMeException;
import biweekly.io.json.JCalRawReader.JCalDataStreamListener;
import biweekly.io.scribe.ScribeIndex;
import biweekly.io.scribe.component.ICalComponentScribe;
import biweekly.io.scribe.component.ICalendarScribe;
import biweekly.io.scribe.property.ICalPropertyScribe;
import biweekly.io.scribe.property.ICalPropertyScribe.Result;
import biweekly.io.scribe.property.RawPropertyScribe;
import biweekly.parameter.ICalParameters;
import biweekly.property.ICalProperty;
import biweekly.property.RawProperty;
import biweekly.util.IOUtils;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JCalReader implements Closeable {
    private static final ICalendarScribe icalScribe = ScribeIndex.getICalendarScribe();
    private ScribeIndex index;
    private final JCalRawReader reader;
    private final ParseWarnings warnings;

    private class JCalDataStreamListenerImpl implements JCalDataStreamListener {
        private final Map<List<String>, ICalComponent> components;

        private JCalDataStreamListenerImpl() {
            this.components = new HashMap();
        }

        public void readProperty(List<String> componentHierarchy, String propertyName, ICalParameters parameters, ICalDataType dataType, JCalValue value) {
            ICalComponent parent = (ICalComponent) this.components.get(componentHierarchy);
            try {
                Result<? extends ICalProperty> result = JCalReader.this.index.getPropertyScribe(propertyName).parseJson(value, dataType, parameters);
                for (Warning warning : result.getWarnings()) {
                    JCalReader.this.warnings.add(Integer.valueOf(JCalReader.this.reader.getLineNum()), propertyName, warning);
                }
                parent.addProperty(result.getProperty());
            } catch (SkipMeException e) {
                JCalReader.this.warnings.add(Integer.valueOf(JCalReader.this.reader.getLineNum()), propertyName, 0, e.getMessage());
            } catch (CannotParseException e2) {
                ICalProperty property = new RawPropertyScribe(propertyName).parseJson(value, dataType, parameters).getProperty();
                parent.addProperty(property);
                String valueStr = ((RawProperty) property).getValue();
                JCalReader.this.warnings.add(Integer.valueOf(JCalReader.this.reader.getLineNum()), propertyName, 1, valueStr, e2.getMessage());
            }
        }

        public void readComponent(List<String> parentHierarchy, String componentName) {
            ICalComponent component = JCalReader.this.index.getComponentScribe(componentName).emptyInstance();
            ICalComponent parent = (ICalComponent) this.components.get(parentHierarchy);
            if (parent != null) {
                parent.addComponent(component);
            }
            List<String> hierarchy = new ArrayList(parentHierarchy);
            hierarchy.add(componentName);
            this.components.put(hierarchy, component);
        }

        public ICalendar getICalendar() {
            if (this.components.isEmpty()) {
                return null;
            }
            ICalComponent component = (ICalComponent) this.components.get(Arrays.asList(new String[]{JCalReader.icalScribe.getComponentName().toLowerCase()}));
            if (component == null) {
                return null;
            }
            if (component instanceof ICalendar) {
                return (ICalendar) component;
            }
            ICalendar ical = (ICalendar) JCalReader.icalScribe.emptyInstance();
            ical.addComponent(component);
            return ical;
        }
    }

    public JCalReader(String json) {
        this(new StringReader(json));
    }

    public JCalReader(InputStream in) {
        this(IOUtils.utf8Reader(in));
    }

    public JCalReader(File file) throws FileNotFoundException {
        this(IOUtils.utf8Reader(file));
    }

    public JCalReader(Reader reader) {
        this.index = new ScribeIndex();
        this.warnings = new ParseWarnings();
        this.reader = new JCalRawReader(reader);
    }

    public List<String> getWarnings() {
        return this.warnings.copy();
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

    public ICalendar readNext() throws IOException {
        if (this.reader.eof()) {
            return null;
        }
        this.warnings.clear();
        JCalDataStreamListenerImpl listener = new JCalDataStreamListenerImpl();
        this.reader.readNext(listener);
        return listener.getICalendar();
    }

    public void close() throws IOException {
        this.reader.close();
    }
}
