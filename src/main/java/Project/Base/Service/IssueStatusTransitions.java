package Project.Base.Service;

import Project.Base.Entity.Enums.IssueStatus;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public class IssueStatusTransitions {


    private static final Map<IssueStatus, Set<IssueStatus>> ALLOWED_TRANSITIONS = new EnumMap<>(IssueStatus.class);

    static {
        ALLOWED_TRANSITIONS.put(IssueStatus.OPEN, EnumSet.of(IssueStatus.IN_PROGRESS));
        ALLOWED_TRANSITIONS.put(IssueStatus.IN_PROGRESS, EnumSet.of(IssueStatus.TESTING));
        ALLOWED_TRANSITIONS.put(IssueStatus.TESTING, EnumSet.of(IssueStatus.CLOSED, IssueStatus.REOPENED));
        ALLOWED_TRANSITIONS.put(IssueStatus.REOPENED, EnumSet.of(IssueStatus.IN_PROGRESS));
        ALLOWED_TRANSITIONS.put(IssueStatus.CLOSED, EnumSet.noneOf(IssueStatus.class));
    }

    public static boolean isValidTransition(IssueStatus from, IssueStatus to) {
        return ALLOWED_TRANSITIONS.getOrDefault(from, Set.of()).contains(to);
    }
}