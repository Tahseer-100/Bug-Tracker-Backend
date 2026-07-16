package Project.Base.DTO;

import Project.Base.Entity.Enums.RoomRole;

public class ChangeRoleRequest {

    private Long actingUserId;
    private RoomRole newRole;

    public ChangeRoleRequest() {}

    public Long getActingUserId() { return actingUserId; }
    public void setActingUserId(Long actingUserId) { this.actingUserId = actingUserId; }

    public RoomRole getNewRole() { return newRole; }
    public void setNewRole(RoomRole newRole) { this.newRole = newRole; }
}