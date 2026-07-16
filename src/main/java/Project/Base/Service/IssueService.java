package Project.Base.Service;

import Project.Base.Exception.InvalidStatusTransitionException;
import Project.Base.Exception.ResourceNotFoundException;
import Project.Base.Entity.Enums.*;
import Project.Base.Entity.Issue;
import Project.Base.Entity.Room;
import Project.Base.Entity.User;
import Project.Base.Repository.IssueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IssueService {

    private final IssueRepo issueRepo;
    private final RoomService roomService;
    private final UserService userService;
    private final ActivityLogService activityLogService;

    @Autowired
    public IssueService(IssueRepo issueRepo, RoomService roomService,
                        UserService userService, ActivityLogService activityLogService) {
        this.issueRepo = issueRepo;
        this.roomService = roomService;
        this.userService = userService;
        this.activityLogService = activityLogService;
    }

    @Transactional
    public Issue createIssue(Long roomId, Long creatorId, String title, String description,
                             String stepsToReproduce, String expectedBehavior,
                             String actualBehavior, String environment,
                             IssuePriority priority) {

        Room room = roomService.getById(roomId);
        User creator = userService.getById(creatorId);

        Issue issue = new Issue(room, title, description, priority, creator);
        issue.setStepsToReproduce(stepsToReproduce);
        issue.setExpectedBehavior(expectedBehavior);
        issue.setActualBehavior(actualBehavior);
        issue.setEnvironment(environment);

        Issue saved = issueRepo.save(issue);

        activityLogService.log(room, creator, ActivityAction.CREATED_ISSUE,
                EntityType.ISSUE, saved.getId(), null, "OPEN");

        return saved;
    }

    @Transactional
    public Issue changeStatus(Long issueId, Long actingUserId, IssueStatus newStatus) {

        Issue issue = getById(issueId);
        IssueStatus oldStatus = issue.getStatus();

        // ← the actual enforcement happens here
        if (!IssueStatusTransitions.isValidTransition(oldStatus, newStatus)) {
            throw new InvalidStatusTransitionException(
                    "Cannot move issue from " + oldStatus + " to " + newStatus);
        }

        User actor = userService.getById(actingUserId);

        issue.setStatus(newStatus);
        issue.setUpdatedAT(LocalDateTime.now());

        if (newStatus == IssueStatus.CLOSED) {
            issue.setClosedAT(LocalDateTime.now());
        }

        Issue saved = issueRepo.save(issue);

        activityLogService.log(issue.getRoom(), actor, ActivityAction.UPDATED_STATUS,
                EntityType.ISSUE, issue.getId(), oldStatus.toString(), newStatus.toString());

        return saved;
    }

    @Transactional
    public Issue assignIssue(Long issueId, Long actingUserId, Long assigneeId) {

        Issue issue = getById(issueId);
        User assignee = userService.getById(assigneeId);
        User actor = userService.getById(actingUserId);

        String oldAssignee = issue.getAssignedTo() != null
                ? issue.getAssignedTo().getUsername()
                : "unassigned";

        issue.setAssignedTo(assignee);
        issue.setUpdatedAT(LocalDateTime.now());

        Issue saved = issueRepo.save(issue);

        activityLogService.log(issue.getRoom(), actor, ActivityAction.ASSIGNED,
                EntityType.ISSUE, issue.getId(), oldAssignee, assignee.getUsername());

        return saved;
    }

    public Issue getById(Long issueId) {
        return issueRepo.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found: " + issueId));
    }

    public List<Issue> getIssuesForRoom(Long roomId) {
        return issueRepo.findByRoomId(roomId);
    }

    public List<Issue> getIssuesForRoomByStatus(Long roomId, IssueStatus status) {
        return issueRepo.findByRoomIdAndStatus(roomId, status);
    }

    public List<Issue> getMyIssues(Long roomId, Long userId) {
        return issueRepo.findByRoomIdAndAssignedToId(roomId, userId);
    }
}