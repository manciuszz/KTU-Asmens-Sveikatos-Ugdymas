package biweekly.io.text;

import biweekly.ICalDataType;
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
import java.io.FileNotFoundException;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public class ICalWriter implements Closeable, Flushable {
    private ScribeIndex index;
    private final ICalRawWriter writer;

    public ICalWriter(OutputStream outputStream) {
        this(IOUtils.utf8Writer(outputStream));
    }

    public ICalWriter(OutputStream outputStream, FoldingScheme foldingScheme) {
        this(IOUtils.utf8Writer(outputStream), foldingScheme);
    }

    public ICalWriter(OutputStream outputStream, FoldingScheme foldingScheme, String newline) {
        this(IOUtils.utf8Writer(outputStream), foldingScheme, newline);
    }

    public ICalWriter(File file) throws FileNotFoundException {
        this(IOUtils.utf8Writer(file));
    }

    public ICalWriter(File file, boolean append) throws FileNotFoundException {
        this(IOUtils.utf8Writer(file, append));
    }

    public ICalWriter(File file, boolean append, FoldingScheme foldingScheme) throws FileNotFoundException {
        this(IOUtils.utf8Writer(file, append), foldingScheme);
    }

    public ICalWriter(File file, boolean append, FoldingScheme foldingScheme, String newline) throws FileNotFoundException {
        this(IOUtils.utf8Writer(file, append), foldingScheme, newline);
    }

    public ICalWriter(Writer writer) {
        this(writer, FoldingScheme.DEFAULT);
    }

    public ICalWriter(Writer writer, FoldingScheme foldingScheme) {
        this(writer, foldingScheme, "\r\n");
    }

    public ICalWriter(Writer writer, FoldingScheme foldingScheme, String newline) {
        this.index = new ScribeIndex();
        this.writer = new ICalRawWriter(writer, foldingScheme, newline);
    }

    public boolean isCaretEncodingEnabled() {
        return this.writer.isCaretEncodingEnabled();
    }

    public void setCaretEncodingEnabled(boolean enable) {
        this.writer.setCaretEncodingEnabled(enable);
    }

    public String getNewline() {
        return this.writer.getNewline();
    }

    public FoldingScheme getFoldingScheme() {
        return this.writer.getFoldingScheme();
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

    public void setScribeIndex(ScribeIndex scribe) {
        this.index = scribe;
    }

    public void write(ICalendar ical) throws IOException {
        this.index.hasScribesFor(ical);
        writeComponent(ical);
    }

    private void writeComponent(ICalComponent component) throws IOException {
        ICalComponentScribe componentScribe = this.index.getComponentScribe(component);
        this.writer.writeBeginComponent(componentScribe.getComponentName());
        for (ICalProperty property : componentScribe.getProperties(component)) {
            ICalPropertyScribe propertyScribe = this.index.getPropertyScribe(property);
            try {
                ICalParameters parameters = propertyScribe.prepareParameters(property);
                String value = propertyScribe.writeText(property);
                ICalDataType dataType = propertyScribe.dataType(property);
                if (!(dataType == null || dataType == propertyScribe.getDefaultDataType())) {
                    parameters.setValue(dataType);
                }
                this.writer.writeProperty(propertyScribe.getPropertyName(), parameters, value);
            } catch (SkipMeException e) {
            }
        }
        for (ICalComponent subComponent : componentScribe.getComponents(component)) {
            writeComponent(subComponent);
        }
        this.writer.writeEndComponent(componentScribe.getComponentName());
    }

    public void flush() throws IOException {
        this.writer.flush();
    }

    public void close() throws IOException {
        this.writer.close();
    }
}
