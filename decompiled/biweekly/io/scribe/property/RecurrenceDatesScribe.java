package biweekly.io.scribe.property;

import biweekly.ICalDataType;
import biweekly.Warning;
import biweekly.io.json.JCalValue;
import biweekly.io.xml.XCalElement;
import biweekly.parameter.ICalParameters;
import biweekly.property.RecurrenceDates;
import biweekly.util.Duration;
import biweekly.util.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecurrenceDatesScribe extends ICalPropertyScribe<RecurrenceDates> {
    public RecurrenceDatesScribe() {
        super(RecurrenceDates.class, "RDATE", ICalDataType.DATE_TIME);
    }

    protected ICalDataType _dataType(RecurrenceDates property) {
        if (property.getDates() != null) {
            return property.hasTime() ? ICalDataType.DATE_TIME : ICalDataType.DATE;
        } else {
            if (property.getPeriods() != null) {
                return ICalDataType.PERIOD;
            }
            return getDefaultDataType();
        }
    }

    protected String _writeText(final RecurrenceDates property) {
        List<Date> dates = property.getDates();
        if (dates != null) {
            return ICalPropertyScribe.list(dates, new ListCallback<Date>() {
                public String asString(Date date) {
                    return ICalPropertyScribe.date(date).time(property.hasTime()).tzid(property.getTimezoneId()).write();
                }
            });
        }
        List<Period> periods = property.getPeriods();
        return periods != null ? ICalPropertyScribe.list(periods, new ListCallback<Period>() {
            public String asString(Period period) {
                StringBuilder sb = new StringBuilder();
                if (period.getStartDate() != null) {
                    sb.append(ICalPropertyScribe.date(period.getStartDate()).tzid(property.getTimezoneId()).write());
                }
                sb.append('/');
                if (period.getEndDate() != null) {
                    sb.append(ICalPropertyScribe.date(period.getEndDate()).tzid(property.getTimezoneId()).write());
                } else if (period.getDuration() != null) {
                    sb.append(period.getDuration());
                }
                return sb.toString();
            }
        }) : "";
    }

    protected RecurrenceDates _parseText(String value, ICalDataType dataType, ICalParameters parameters, List<Warning> warnings) {
        return parse(ICalPropertyScribe.list(value), dataType, parameters, warnings);
    }

    protected void _writeXml(RecurrenceDates property, XCalElement element) {
        List<Date> dates = property.getDates();
        if (dates != null) {
            ICalDataType dataType = property.hasTime() ? ICalDataType.DATE_TIME : ICalDataType.DATE;
            if (dates.isEmpty()) {
                element.append(dataType, "");
                return;
            }
            for (Date date : dates) {
                element.append(dataType, ICalPropertyScribe.date(date).time(property.hasTime()).tzid(property.getTimezoneId()).extended(true).write());
            }
            return;
        }
        List<Period> periods = property.getPeriods();
        if (periods == null) {
            element.append(this.defaultDataType, "");
        } else if (periods.isEmpty()) {
            element.append(ICalDataType.PERIOD, "");
        } else {
            for (Period period : periods) {
                XCalElement periodElement = element.append(ICalDataType.PERIOD);
                Date start = period.getStartDate();
                if (start != null) {
                    periodElement.append("start", ICalPropertyScribe.date(start).tzid(property.getTimezoneId()).extended(true).write());
                }
                Date end = period.getEndDate();
                if (end != null) {
                    periodElement.append("end", ICalPropertyScribe.date(end).tzid(property.getTimezoneId()).extended(true).write());
                }
                Duration duration = period.getDuration();
                if (duration != null) {
                    periodElement.append("duration", duration.toString());
                }
            }
        }
    }

    protected RecurrenceDates _parseXml(XCalElement element, ICalParameters parameters, List<Warning> warnings) {
        List<XCalElement> periodElements = element.children(ICalDataType.PERIOD);
        if (periodElements.isEmpty()) {
            List<String> dateStrs = element.all(ICalDataType.DATE_TIME);
            boolean hasTime = !dateStrs.isEmpty();
            dateStrs.addAll(element.all(ICalDataType.DATE));
            if (dateStrs.isEmpty()) {
                throw ICalPropertyScribe.missingXmlElements(ICalDataType.PERIOD, ICalDataType.DATE_TIME, ICalDataType.DATE);
            }
            List<Date> dates = new ArrayList(dateStrs.size());
            for (String dateStr : dateStrs) {
                try {
                    dates.add(ICalPropertyScribe.date(dateStr).tzid(parameters.getTimezoneId(), warnings).parse());
                } catch (IllegalArgumentException e) {
                    warnings.add(Warning.parse(15, dateStr));
                }
            }
            return new RecurrenceDates(dates, hasTime);
        }
        List<Period> arrayList = new ArrayList(periodElements.size());
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
                            arrayList.add(new Period(start, ICalPropertyScribe.date(endStr).tzid(parameters.getTimezoneId(), warnings).parse()));
                        } catch (IllegalArgumentException e2) {
                            warnings.add(Warning.parse(11, endStr));
                        }
                    } else {
                        String durationStr = periodElement.first("duration");
                        if (durationStr != null) {
                            try {
                                arrayList.add(new Period(start, Duration.parse(durationStr)));
                            } catch (IllegalArgumentException e3) {
                                warnings.add(Warning.parse(12, durationStr));
                            }
                        } else {
                            warnings.add(Warning.parse(13, new Object[0]));
                        }
                    }
                } catch (IllegalArgumentException e4) {
                    warnings.add(Warning.parse(10, startStr));
                }
            }
        }
        return new RecurrenceDates(arrayList);
    }

    protected JCalValue _writeJson(RecurrenceDates property) {
        List values = new ArrayList();
        List<Date> dates = property.getDates();
        List<Period> periods = property.getPeriods();
        if (dates != null) {
            for (Date date : dates) {
                values.add(ICalPropertyScribe.date(date).time(property.hasTime()).tzid(property.getTimezoneId()).extended(true).write());
            }
        } else if (periods != null) {
            for (Period period : property.getPeriods()) {
                StringBuilder sb = new StringBuilder();
                if (period.getStartDate() != null) {
                    sb.append(ICalPropertyScribe.date(period.getStartDate()).tzid(property.getTimezoneId()).extended(true).write());
                }
                sb.append('/');
                if (period.getEndDate() != null) {
                    sb.append(ICalPropertyScribe.date(period.getEndDate()).tzid(property.getTimezoneId()).extended(true).write());
                } else if (period.getDuration() != null) {
                    sb.append(period.getDuration());
                }
                values.add(sb.toString());
            }
        }
        if (values.isEmpty()) {
            values.add("");
        }
        return JCalValue.multi(values);
    }

    protected RecurrenceDates _parseJson(JCalValue value, ICalDataType dataType, ICalParameters parameters, List<Warning> warnings) {
        return parse(value.asMulti(), dataType, parameters, warnings);
    }

    private RecurrenceDates parse(List<String> valueStrs, ICalDataType dataType, ICalParameters parameters, List<Warning> warnings) {
        if (dataType == ICalDataType.PERIOD) {
            List<Period> periods = new ArrayList(valueStrs.size());
            for (String timePeriodStr : valueStrs) {
                String[] timePeriodStrSplit = timePeriodStr.split("/");
                if (timePeriodStrSplit.length < 2) {
                    warnings.add(Warning.parse(13, new Object[0]));
                } else {
                    try {
                        Date start = ICalPropertyScribe.date(timePeriodStrSplit[0]).tzid(parameters.getTimezoneId(), warnings).parse();
                        String endStr = timePeriodStrSplit[1];
                        try {
                            periods.add(new Period(start, ICalPropertyScribe.date(endStr).tzid(parameters.getTimezoneId(), warnings).parse()));
                        } catch (IllegalArgumentException e) {
                            try {
                                periods.add(new Period(start, Duration.parse(endStr)));
                            } catch (IllegalArgumentException e2) {
                                warnings.add(Warning.parse(14, endStr));
                            }
                        }
                    } catch (IllegalArgumentException e3) {
                        warnings.add(Warning.parse(10, startStr));
                    }
                }
            }
            return new RecurrenceDates(periods);
        }
        boolean hasTime = dataType == ICalDataType.DATE_TIME;
        List<Date> dates = new ArrayList(valueStrs.size());
        for (String s : valueStrs) {
            try {
                dates.add(ICalPropertyScribe.date(s).tzid(parameters.getTimezoneId(), warnings).parse());
            } catch (IllegalArgumentException e4) {
                warnings.add(Warning.parse(15, s));
            }
        }
        return new RecurrenceDates(dates, hasTime);
    }
}
