package Project.Base.DTO;

import Project.Base.Entity.Enums.IssueStatus;

public class StatusChangeRequest {

    private Long actingUserId;
    private IssueStatus newStatus;

    public StatusChangeRequest() {}

    public Long getActingUserId() { return actingUserId; }
    public void setActingUserId(Long actingUserId) { this.actingUserId = actingUserId; }

    public IssueStatus getNewStatus() { return newStatus; }
    public void setNewStatus(IssueStatus newStatus) { this.newStatus = newStatus; }
}