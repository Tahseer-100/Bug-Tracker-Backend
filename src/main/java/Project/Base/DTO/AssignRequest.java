package Project.Base.DTO;

public class AssignRequest {

    private Long actingUserId;
    private Long assigneeId;

    public AssignRequest() {}

    public Long getActingUserId() { return actingUserId; }
    public void setActingUserId(Long actingUserId) { this.actingUserId = actingUserId; }

    public Long getAssigneeId() { return assigneeId; }
    public void setAssigneeId(Long assigneeId) { this.assigneeId = assigneeId; }
}