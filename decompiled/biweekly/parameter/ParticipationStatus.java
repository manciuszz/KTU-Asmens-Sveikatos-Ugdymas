package biweekly.parameter;

import java.util.Collection;

public class ParticipationStatus extends EnumParameterValue {
    public static final ParticipationStatus ACCEPTED = new ParticipationStatus("ACCEPTED");
    public static final ParticipationStatus COMPLETED = new ParticipationStatus("COMPLETED");
    public static final ParticipationStatus DECLINED = new ParticipationStatus("DECLINED");
    public static final ParticipationStatus DELEGATED = new ParticipationStatus("DELEGATED");
    public static final ParticipationStatus IN_PROGRESS = new ParticipationStatus("IN_PROGRESS");
    public static final ParticipationStatus NEEDS_ACTION = new ParticipationStatus("NEEDS-ACTION");
    public static final ParticipationStatus TENTATIVE = new ParticipationStatus("TENTATIVE");
    private static final ICalParameterCaseClasses<ParticipationStatus> enums = new ICalParameterCaseClasses(ParticipationStatus.class);

    private ParticipationStatus(String value) {
        super(value);
    }

    public static ParticipationStatus find(String value) {
        return (ParticipationStatus) enums.find(value);
    }

    public static ParticipationStatus get(String value) {
        return (ParticipationStatus) enums.get(value);
    }

    public static Collection<ParticipationStatus> all() {
        return enums.all();
    }
}
