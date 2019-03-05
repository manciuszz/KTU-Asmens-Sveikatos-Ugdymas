package biweekly.io.scribe.component;

import biweekly.component.VJournal;

public class VJournalScribe extends ICalComponentScribe<VJournal> {
    public VJournalScribe() {
        super(VJournal.class, "VJOURNAL");
    }

    protected VJournal _newInstance() {
        return new VJournal();
    }
}
