package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.io.json.JCalValue;
import biweekly.io.xml.XCalElement;
import biweekly.parameter.ICalParameters;
import biweekly.property.FreeBusy;
import biweekly.util.Duration;
import biweekly.util.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FreeBusyScribe extends ICalPropertyScribe<FreeBusy> {
    public FreeBusyScribe() {
        super(FreeBusy.class, "FREEBUSY", ICalDataType.PERIOD);
    }

    protected String _writeText(FreeBusy property) {
        return ICalPropertyScribe.list(property.getValues(), new ListCallback<Period>() {
            public String asString(Period period) {
                StringBuilder sb = new StringBuilder();
                if (period.getStartDate() != null) {
                    sb.append(ICalPropertyScribe.date(period.getStartDate()).write());
                }
                sb.append('/');
                if (period.getEndDate() != null) {
                    sb.append(ICalPropertyScribe.date(period.getEndDate()).write());
                } else if (period.getDuration() != null) {
                    sb.append(period.getDuration());
                }
                return sb.toString();
            }
        });
    }

    protected FreeBusy _parseText(String value, ICalDataType dataType, ICalParameters parameters, List<Warning> warnings) {
        return parse(ICalPropertyScribe.list(value), parameters, warnings);
    }

    protected void _writeXml(FreeBusy property, XCalElement element) {
        for (Period period : property.getValues()) {
            XCalElement periodElement = element.append(ICalDataType.PERIOD);
            Date start = period.getStartDate();
            if (start != null) {
                periodElement.append("start", ICalPropertyScribe.date(start).extended(true).write());
            }
            Date end = period.getEndDate();
            if (end != null) {
                periodElement.append("end", ICalPropertyScribe.date(end).extended(true).write());
            }
            Duration duration = period.getDuration();
            if (duration != null) {
                periodElement.append("duration", duration.toString());
            }
        }
    }

    protected FreeBusy _parseXml(XCalElement element, ICalParameters parameters, List<Warning> warnings) {
        List<Warning> list;
        List<XCalElement> periodElements = element.children(ICalDataType.PERIOD);
        if (periodElements.isEmpty()) {
            throw ICalPropertyScribe.missingXmlElements(ICalDataType.PERIOD);
        }
        FreeBusy prop = new FreeBusy();
        for (XCalElement periodElement : periodElements) {
            String startStr = periodElement.first("start");
            if (startStr == null) {
                warnings.add(Warning.parse(9, new Object[0]));
            } else {
                try {
                    Date start = ICalPropertyScribe.date(startStr).tzid(parameters.getTimezoneId(), warnings).parse();
                    String endStr = periodElement.first("end");
                    if (endStr != null) {
                        try {
                            prop.addValue(start, ICalPropertyScribe.date(endStr).tzid(parameters.getTimezoneId(), warnings).parse());
                        } catch (IllegalArgumentException e) {
                            list = warnings;
                            list.add(Warning.parse(11, endStr));
                        }
                    } else {
                        String durationStr = periodElement.first("duration");
                        if (durationStr != null) {
                            try {
                                prop.addValue(start, Duration.parse(durationStr));
                            } catch (IllegalArgumentException e2) {
                                list = warnings;
                                list.add(Warning.parse(12, durationStr));
                            }
                        } else {
                            warnings.add(Warning.parse(13, new Object[0]));
                        }
                    }
                } catch (IllegalArgumentException e3) {
                    list = warnings;
                    list.add(Warning.parse(10, startStr));
                }
            }
        }
        return prop;
    }

    protected JCalValue _writeJson(FreeBusy property) {
        List<Period> values = property.getValues();
        if (values.isEmpty()) {
            return JCalValue.single("");
        }
        List valuesStr = new ArrayList();
        for (Period period : values) {
            StringBuilder sb = new StringBuilder();
            if (period.getStartDate() != null) {
                sb.append(ICalPropertyScribe.date(period.getStartDate()).extended(true).write());
            }
            sb.append('/');
            if (period.getEndDate() != null) {
                sb.append(ICalPropertyScribe.date(period.getEndDate()).extended(true).write());
            } else if (period.getDuration() != null) {
                sb.append(period.getDuration());
            }
            valuesStr.add(sb.toString());
        }
        return JCalValue.multi(valuesStr);
    }

    protected FreeBusy _parseJson(JCalValue value, ICalDataType dataType, ICalParameters parameters, List<Warning> warnings) {
        return parse(value.asMulti(), parameters, warnings);
    }

    private FreeBusy parse(List<String> periods, ICalParameters parameters, List<Warning> warnings) {
        List<Warning> list;
        FreeBusy freebusy = new FreeBusy();
        for (String period : periods) {
            String[] periodSplit = period.split("/");
            if (periodSplit.length < 2) {
                warnings.add(Warning.parse(13, new Object[0]));
            } else {
                try {
                    Date start = ICalPropertyScribe.date(periodSplit[0]).tzid(parameters.getTimezoneId(), warnings).parse();
                    String endStr = periodSplit[1];
                    try {
                        freebusy.addValue(start, ICalPropertyScribe.date(endStr).tzid(parameters.getTimezoneId(), warnings).parse());
                    } catch (IllegalArgumentException e) {
                        try {
                            freebusy.addValue(start, Duration.parse(endStr));
                        } catch (IllegalArgumentException e2) {
                            list = warnings;
                            list.add(Warning.parse(14, endStr));
                        }
                    }
                } catch (IllegalArgumentException e3) {
                    list = warnings;
                    list.add(Warning.parse(10, startStr));
                }
            }
        }
        return freebusy;
    }
}
