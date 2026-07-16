package Project.Base.DTO;

import Project.Base.Entity.Enums.IssuePriority;
import Project.Base.Entity.Enums.IssueStatus;
import Project.Base.Entity.Enums.IssueType;
import Project.Base.Entity.Issue;
import java.time.LocalDateTime;

public class IssueResponse {

    private Long id;
    private Long roomId;
    private String title;
    private String description;
    private String stepsToReproduce;
    private String expectedBehavior;
    private String actualBehavior;
    private String environment;
    private IssueStatus status;
    private IssuePriority priority;
    private IssueType type;
    private Long createdById;
    private String createdByUsername;
    private Long assignedToId;
    private String assignedToUsername;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime closedAt;

    public IssueResponse() {}

    public static IssueResponse fromEntity(Issue issue) {
        IssueResponse dto = new IssueResponse();
        dto.id = issue.getId();
        dto.roomId = issue.getRoom().getId();
        dto.title = issue.getTitle();
        dto.description = issue.getDescription();
        dto.stepsToReproduce = issue.getStepsToReproduce();
        dto.expectedBehavior = issue.getExpectedBehavior();
        dto.actualBehavior = issue.getActualBehavior();
        dto.environment = issue.getEnvironment();
        dto.status = issue.getStatus();
        dto.priority = issue.getPriority();
        dto.type = issue.getType();
        dto.createdById = issue.getCreatedBy().getId();
        dto.createdByUsername = issue.getCreatedBy().getUsername();
        dto.createdAt = issue.getCreatedAT();
        dto.updatedAt = issue.getUpdatedAT();
        dto.closedAt = issue.getClosedAT();

        if (issue.getAssignedTo() != null) {
            dto.assignedToId = issue.getAssignedTo().getId();
            dto.assignedToUsername = issue.getAssignedTo().getUsername();
        }

        return dto;
    }

    public Long getId() { return id; }
    public Long getRoomId() { return roomId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStepsToReproduce() { return stepsToReproduce; }
    public String getExpectedBehavior() { return expectedBehavior; }
    public String getActualBehavior() { return actualBehavior; }
    public String getEnvironment() { return environment; }
    public IssueStatus getStatus() { return status; }
    public IssuePriority getPriority() { return priority; }
    public IssueType getType() { return type; }
    public Long getCreatedById() { return createdById; }
    public String getCreatedByUsername() { return createdByUsername; }
    public Long getAssignedToId() { return assignedToId; }
    public String getAssignedToUsername() { return assignedToUsername; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getClosedAt() { return closedAt; }
}