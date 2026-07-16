package Project.Base.DTO;

import Project.Base.Entity.Enums.RoomRole;
import Project.Base.Entity.RoomMembership;
import java.time.LocalDateTime;

public class RoomMemberResponse {

    private Long userId;
    private String username;
    private String fullname;
    private RoomRole roleInRoom;
    private LocalDateTime joinedAt;

    public RoomMemberResponse() {}

    public static RoomMemberResponse fromEntity(RoomMembership membership) {
        RoomMemberResponse dto = new RoomMemberResponse();
        dto.userId = membership.getUser().getId();
        dto.username = membership.getUser().getUsername();
        dto.fullname = membership.getUser().getFullname();
        dto.roleInRoom = membership.getRoleInRoom();
        dto.joinedAt = membership.getJoinedAt();
        return dto;
    }

    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getFullname() { return fullname; }
    public RoomRole getRoleInRoom() { return roleInRoom; }
    public LocalDateTime getJoinedAt() { return joinedAt; }
}