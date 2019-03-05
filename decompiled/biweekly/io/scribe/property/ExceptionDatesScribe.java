package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.io.CannotParseException;
import biweekly.io.json.JCalValue;
import biweekly.io.xml.XCalElement;
import biweekly.parameter.ICalParameters;
import biweekly.property.ExceptionDates;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExceptionDatesScribe extends ListPropertyScribe<ExceptionDates, Date> {
    public ExceptionDatesScribe() {
        super(ExceptionDates.class, "EXDATE", ICalDataType.DATE_TIME);
    }

    protected ICalDataType _dataType(ExceptionDates property) {
        return property.hasTime() ? ICalDataType.DATE_TIME : ICalDataType.DATE;
    }

    protected ExceptionDates newInstance(ICalDataType dataType, ICalParameters parameters) {
        return new ExceptionDates(dataType != ICalDataType.DATE);
    }

    protected String writeValue(ExceptionDates property, Date value) {
        return ICalPropertyScribe.date(value).time(property.hasTime()).tzid(property.getParameters().getTimezoneId()).write();
    }

    protected Date readValue(String value, ICalDataType dataType, ICalParameters parameters, List<Warning> warnings) {
        try {
            return ICalPropertyScribe.date(value).tzid(parameters.getTimezoneId(), warnings).parse();
        } catch (IllegalArgumentException e) {
            throw new CannotParseException(19, new Object[0]);
        }
    }

    protected void _writeXml(ExceptionDates property, XCalElement element) {
        ICalDataType dataType = dataType(property);
        for (Date value : property.getValues()) {
            element.append(dataType, ICalPropertyScribe.date(value).time(property.hasTime()).tzid(property.getParameters().getTimezoneId()).extended(true).write());
        }
    }

    protected ExceptionDates _parseXml(XCalElement element, ICalParameters parameters, List<Warning> warnings) {
        boolean z = true;
        List<String> values = element.all(ICalDataType.DATE_TIME);
        ICalDataType dataType = values.isEmpty() ? ICalDataType.DATE : ICalDataType.DATE_TIME;
        values.addAll(element.all(ICalDataType.DATE));
        if (values.isEmpty()) {
            throw ICalPropertyScribe.missingXmlElements(ICalDataType.DATE_TIME, ICalDataType.DATE);
        }
        if (dataType != ICalDataType.DATE_TIME) {
            z = false;
        }
        ExceptionDates prop = new ExceptionDates(z);
        for (String value : values) {
            prop.addValue(readValue(value, dataType, parameters, (List) warnings));
        }
        return prop;
    }

    protected JCalValue _writeJson(ExceptionDates property) {
        List<Date> values = property.getValues();
        if (values.isEmpty()) {
            return JCalValue.single("");
        }
        List valuesStr = new ArrayList();
        for (Date value : values) {
            valuesStr.add(ICalPropertyScribe.date(value).time(property.hasTime()).tzid(property.getParameters().getTimezoneId()).extended(true).write());
        }
        return JCalValue.multi(valuesStr);
    }

    protected ExceptionDates _parseJson(JCalValue value, ICalDataType dataType, ICalParameters parameters, List<Warning> warnings) {
        List<String> valueStrs = value.asMulti();
        ExceptionDates prop = new ExceptionDates(dataType == ICalDataType.DATE_TIME);
        for (String valueStr : valueStrs) {
            prop.addValue(readValue(valueStr, dataType, parameters, (List) warnings));
        }
        return prop;
    }
}
