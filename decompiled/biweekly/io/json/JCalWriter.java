package biweekly.io.json;

import biweekly.ICalendar;
import biweekly.component.ICalComponent;
import biweekly.io.SkipMeException;
import biweekly.io.scribe.ScribeIndex;
import biweekly.io.scribe.component.ICalComponentScribe;
import biweekly.io.scribe.property.ICalPropertyScribe;
import biweekly.parameter.ICalParameters;
import biweekly.property.ICalProperty;
import biweekly.util.IOUtils;
import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public class JCalWriter implements Closeable, Flushable {
    private ScribeIndex index;
    private final JCalRawWriter writer;

    public JCalWriter(OutputStream outputStream) {
        this(IOUtils.utf8Writer(outputStream));
    }

    public JCalWriter(OutputStream outputStream, boolean wrapInArray) {
        this(IOUtils.utf8Writer(outputStream), wrapInArray);
    }

    public JCalWriter(File file) throws IOException {
        this(IOUtils.utf8Writer(file));
    }

    public JCalWriter(File file, boolean wrapInArray) throws IOException {
        this(IOUtils.utf8Writer(file), wrapInArray);
    }

    public JCalWriter(Writer writer) {
        this(writer, false);
    }

    public JCalWriter(Writer writer, boolean wrapInArray) {
        this.index = new ScribeIndex();
        this.writer = new JCalRawWriter(writer, wrapInArray);
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

    public boolean isIndent() {
        return this.writer.isIndent();
    }

    public void setIndent(boolean indent) {
        this.writer.setIndent(indent);
    }

    public void write(ICalendar ical) throws IOException {
        this.index.hasScribesFor(ical);
        writeComponent(ical);
    }

    private void writeComponent(ICalComponent component) throws IOException {
        ICalComponentScribe componentScribe = this.index.getComponentScribe(component);
        this.writer.writeStartComponent(componentScribe.getComponentName().toLowerCase());
        for (ICalProperty property : componentScribe.getProperties(component)) {
            ICalPropertyScribe propertyScribe = this.index.getPropertyScribe(property);
            try {
                ICalParameters parameters = propertyScribe.prepareParameters(property);
                JCalValue value = propertyScribe.writeJson(property);
                this.writer.writeProperty(propertyScribe.getPropertyName().toLowerCase(), parameters, propertyScribe.dataType(property), value);
            } catch (SkipMeException e) {
            }
        }
        for (ICalComponent subComponent : componentScribe.getComponents(component)) {
            writeComponent(subComponent);
        }
        this.writer.writeEndComponent();
    }

    public void flush() throws IOException {
        this.writer.flush();
    }

    public void close() throws IOException {
        this.writer.close();
    }

    public void closeJsonStream() throws IOException {
        this.writer.closeJsonStream();
    }
}
