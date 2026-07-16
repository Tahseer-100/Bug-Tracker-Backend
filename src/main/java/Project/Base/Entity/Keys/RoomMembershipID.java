package Project.Base.Entity.Keys;

import java.io.Serializable;
import java.util.Objects;

public class RoomMembershipID implements Serializable {
    private long roomId;
    private long userId;

    public RoomMembershipID() { }

    public RoomMembershipID(long roomId, long userId) {
        this.roomId = roomId;
        this.userId = userId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (!(obj instanceof RoomMembershipID)) return false;
        RoomMembershipID that = (RoomMembershipID) obj;
        return Objects.equals(roomId, that.roomId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, userId);
    }
}
