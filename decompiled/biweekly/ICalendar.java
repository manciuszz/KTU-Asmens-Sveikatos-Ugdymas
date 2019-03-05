package biweekly;

import biweekly.component.ICalComponent;
import biweekly.component.VEvent;
import biweekly.component.VFreeBusy;
import biweekly.component.VJournal;
import biweekly.component.VTimezone;
import biweekly.component.VTodo;
import biweekly.property.CalendarScale;
import biweekly.property.Method;
import biweekly.property.ProductId;
import biweekly.property.Version;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.TransformerException;

public class ICalendar extends ICalComponent {
    public ICalendar() {
        setVersion(Version.v2_0());
        setProductId(ProductId.biweekly());
    }

    public Version getVersion() {
        return (Version) getProperty(Version.class);
    }

    public void setVersion(Version version) {
        setProperty(Version.class, version);
    }

    public ProductId getProductId() {
        return (ProductId) getProperty(ProductId.class);
    }

    public void setProductId(ProductId prodId) {
        setProperty(ProductId.class, prodId);
    }

    public ProductId setProductId(String prodId) {
        ProductId prop = prodId == null ? null : new ProductId(prodId);
        setProductId(prop);
        return prop;
    }

    public CalendarScale getCalendarScale() {
        return (CalendarScale) getProperty(CalendarScale.class);
    }

    public void setCalendarScale(CalendarScale calendarScale) {
        setProperty(CalendarScale.class, calendarScale);
    }

    public Method getMethod() {
        return (Method) getProperty(Method.class);
    }

    public void setMethod(Method method) {
        setProperty(Method.class, method);
    }

    public Method setMethod(String method) {
        Method prop = method == null ? null : new Method(method);
        setMethod(prop);
        return prop;
    }

    public List<VEvent> getEvents() {
        return getComponents(VEvent.class);
    }

    public void addEvent(VEvent event) {
        addComponent(event);
    }

    public List<VTodo> getTodos() {
        return getComponents(VTodo.class);
    }

    public void addTodo(VTodo todo) {
        addComponent(todo);
    }

    public List<VJournal> getJournals() {
        return getComponents(VJournal.class);
    }

    public void addJournal(VJournal journal) {
        addComponent(journal);
    }

    public List<VFreeBusy> getFreeBusies() {
        return getComponents(VFreeBusy.class);
    }

    public void addFreeBusy(VFreeBusy freeBusy) {
        addComponent(freeBusy);
    }

    public List<VTimezone> getTimezones() {
        return getComponents(VTimezone.class);
    }

    public void addTimezone(VTimezone timezone) {
        addComponent(timezone);
    }

    public ValidationWarnings validate() {
        return new ValidationWarnings(validate(new ArrayList(0)));
    }

    protected void validate(List<ICalComponent> list, List<Warning> warnings) {
        checkRequiredCardinality(warnings, ProductId.class, Version.class);
        if (this.components.isEmpty()) {
            warnings.add(Warning.validate(4, new Object[0]));
        }
    }

    public String write() {
        return Biweekly.write(this).go();
    }

    public void write(File file) throws IOException {
        Biweekly.write(this).go(file);
    }

    public void write(OutputStream out) throws IOException {
        Biweekly.write(this).go(out);
    }

    public void write(Writer writer) throws IOException {
        Biweekly.write(this).go(writer);
    }

    public String writeXml() {
        return Biweekly.writeXml(this).indent(2).go();
    }

    public void writeXml(File file) throws TransformerException, IOException {
        Biweekly.writeXml(this).indent(2).go(file);
    }

    public void writeXml(OutputStream out) throws TransformerException {
        Biweekly.writeXml(this).indent(2).go(out);
    }

    public void writeXml(Writer writer) throws TransformerException {
        Biweekly.writeXml(this).indent(2).go(writer);
    }

    public String writeJson() {
        return Biweekly.writeJson(this).go();
    }

    public void writeJson(File file) throws IOException {
        Biweekly.writeJson(this).go(file);
    }

    public void writeJson(OutputStream out) throws IOException {
        Biweekly.writeJson(this).go(out);
    }

    public void writeJson(Writer writer) throws IOException {
        Biweekly.writeJson(this).go(writer);
    }
}
