package biweekly.io.scribe.component;

import biweekly.component.VFreeBusy;
import biweekly.property.FreeBusy;
import biweekly.property.ICalProperty;
import biweekly.util.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class VFreeBusyScribe extends ICalComponentScribe<VFreeBusy> {
    public VFreeBusyScribe() {
        super(VFreeBusy.class, "VFREEBUSY");
    }

    public List<ICalProperty> getProperties(VFreeBusy component) {
        List<ICalProperty> properties = super.getProperties(component);
        List<FreeBusy> fb = new ArrayList(component.getFreeBusy());
        if (!fb.isEmpty()) {
            Collections.sort(fb, new Comparator<FreeBusy>() {
                public int compare(FreeBusy one, FreeBusy two) {
                    Date oneStart = getEarliestStartDate(one);
                    Date twoStart = getEarliestStartDate(two);
                    if (oneStart == null && twoStart == null) {
                        return 0;
                    }
                    if (oneStart == null) {
                        return 1;
                    }
                    if (twoStart == null) {
                        return -1;
                    }
                    return oneStart.compareTo(twoStart);
                }

                private Date getEarliestStartDate(FreeBusy fb) {
                    Date date = null;
                    for (Period tp : fb.getValues()) {
                        if (tp.getStartDate() != null && (date == null || date.compareTo(tp.getStartDate()) > 0)) {
                            date = tp.getStartDate();
                        }
                    }
                    return date;
                }
            });
            int index = 0;
            Iterator i$ = properties.iterator();
            while (i$.hasNext() && !(((ICalProperty) i$.next()) instanceof FreeBusy)) {
                index++;
            }
            for (FreeBusy f : fb) {
                properties.remove(f);
                int index2 = index + 1;
                properties.add(index, f);
                index = index2;
            }
        }
        return properties;
    }

    protected VFreeBusy _newInstance() {
        return new VFreeBusy();
    }
}
