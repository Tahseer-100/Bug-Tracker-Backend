package Project.Base.Entity;


import Project.Base.Entity.Enums.RoomRole;
import Project.Base.Entity.Keys.RoomMembershipID;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "room_memberships")
public class RoomMembership {

    @EmbeddedId
    private RoomMembershipID id;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name="room_id")
    private Room room;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name="user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomRole roleInRoom;

    @Column(nullable = false)
    private LocalDateTime joinedAt;

    public RoomMembership() {}

    public RoomMembership(Room room, User user, RoomRole roleInRoom) {
        this.room = room;
        this.user = user;
        this.roleInRoom = roleInRoom;
        this.id = new RoomMembershipID(room.getId(), user.getId()); //Id of a RoomMembership is combination of roomId and userId
        this.joinedAt = LocalDateTime.now();
    }

    public RoomMembershipID getId() {
        return id;
    }

    public void setId(RoomMembershipID id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RoomRole getRoleInRoom() {
        return roleInRoom;
    }

    public void setRoleInRoom(RoomRole roleInRoom) {
        this.roleInRoom = roleInRoom;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    @Override
    public String toString() {
        return "RoomMembership{" +
                "id=" + id +
                ", room=" + room +
                ", user=" + user +
                ", roleInRoom=" + roleInRoom +
                ", joinedAt=" + joinedAt +
                '}';
    }
}
