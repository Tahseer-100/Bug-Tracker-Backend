package Project.Base.DTO;

import Project.Base.Entity.Enums.RoomRole;

public class AddMemberRequest {

    private Long actingUserId;    //Temporary
    private Long newUserId;
    private RoomRole roleInRoom;

    public AddMemberRequest() {}

    public Long getActingUserId() { return actingUserId; }
    public void setActingUserId(Long actingUserId) { this.actingUserId = actingUserId; }

    public Long getNewUserId() { return newUserId; }
    public void setNewUserId(Long newUserId) { this.newUserId = newUserId; }

    public RoomRole getRoleInRoom() { return roleInRoom; }
    public void setRoleInRoom(RoomRole roleInRoom) { this.roleInRoom = roleInRoom; }
}