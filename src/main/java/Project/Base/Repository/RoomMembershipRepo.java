package Project.Base.Repository;

import Project.Base.Entity.RoomMembership;
import Project.Base.Entity.Keys.RoomMembershipID;
import Project.Base.Entity.Enums.RoomRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomMembershipRepo extends JpaRepository<RoomMembership, RoomMembershipID> {


    List<RoomMembership> findByRoomId(Long roomId);

    List<RoomMembership> findByUserId(Long userId);

    Optional<RoomMembership> findByRoomIdAndUserId(Long roomId, Long userId);

    boolean existsByRoomIdAndUserId(Long roomId, Long userId);

    List<RoomMembership> findByRoomIdAndRoleInRoom(Long roomId, RoomRole roleInRoom);
}