package Project.Base.Service;

import Project.Base.Exception.DuplicateResourceException;
import Project.Base.Exception.ResourceNotFoundException;
import Project.Base.Exception.UnauthorizedActionException;
import Project.Base.Entity.Enums.RoomRole;
import Project.Base.Entity.Room;
import Project.Base.Entity.RoomMembership;
import Project.Base.Entity.User;
import Project.Base.Repository.RoomMembershipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomMembershipService {

    private final RoomMembershipRepo roomMembershipRepo;
    private final RoomService roomService;
    private final UserService userService;

    @Autowired
    public RoomMembershipService(RoomMembershipRepo roomMembershipRepo,
                                 RoomService roomService,
                                 UserService userService) {
        this.roomMembershipRepo = roomMembershipRepo;
        this.roomService = roomService;
        this.userService = userService;
    }

    public void assertIsManager(Long roomId, Long actingUserId) {
        RoomMembership membership = roomMembershipRepo.findByRoomIdAndUserId(roomId, actingUserId)
                .orElseThrow(() -> new UnauthorizedActionException(
                        "User " + actingUserId + " is not a member of room " + roomId));

        if (membership.getRoleInRoom() != RoomRole.MANAGER) {
            throw new UnauthorizedActionException(
                    "User " + actingUserId + " must be a MANAGER to perform this action");
        }
    }

    public RoomMembership addMember(Long roomId, Long actingUserId, Long newUserId, RoomRole roleInRoom) {

        assertIsManager(roomId, actingUserId);   // ← permission check happens FIRST

        if (roomMembershipRepo.existsByRoomIdAndUserId(roomId, newUserId)) {
            throw new DuplicateResourceException(
                    "User " + newUserId + " is already a member of room " + roomId);
        }

        Room room = roomService.getById(roomId);
        User newUser = userService.getById(newUserId);

        RoomMembership membership = new RoomMembership(room, newUser, roleInRoom);
        return roomMembershipRepo.save(membership);
    }

    public void removeMember(Long roomId, Long actingUserId, Long targetUserId) {

        assertIsManager(roomId, actingUserId);

        RoomMembership membership = roomMembershipRepo.findByRoomIdAndUserId(roomId, targetUserId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User " + targetUserId + " is not a member of room " + roomId));

        roomMembershipRepo.delete(membership);
    }

    public RoomMembership changeRole(Long roomId, Long actingUserId, Long targetUserId, RoomRole newRole) {

        assertIsManager(roomId, actingUserId);

        RoomMembership membership = roomMembershipRepo.findByRoomIdAndUserId(roomId, targetUserId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User " + targetUserId + " is not a member of room " + roomId));

        membership.setRoleInRoom(newRole);
        return roomMembershipRepo.save(membership);
    }

    public List<RoomMembership> getMembers(Long roomId) {
        return roomMembershipRepo.findByRoomId(roomId);
    }
}