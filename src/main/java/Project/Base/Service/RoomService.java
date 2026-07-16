package Project.Base.Service;

import Project.Base.Exception.DuplicateResourceException;
import Project.Base.Exception.ResourceNotFoundException;
import Project.Base.Entity.Enums.RoomRole;
import Project.Base.Entity.Room;
import Project.Base.Entity.RoomMembership;
import Project.Base.Entity.User;
import Project.Base.Repository.RoomMembershipRepo;
import Project.Base.Repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepo roomRepo;
    private final RoomMembershipRepo roomMembershipRepo;
    private final UserService userService;

    @Autowired
    public RoomService(RoomRepo roomRepo, RoomMembershipRepo roomMembershipRepo, UserService userService) {
        this.roomRepo = roomRepo;
        this.roomMembershipRepo = roomMembershipRepo;
        this.userService = userService;
    }

    @Transactional
    public Room createRoom(String name, String description, Long creatorId) {

        if (roomRepo.existsByName(name)) {
            throw new DuplicateResourceException("Room name already exists: " + name);
        }

        User creator = userService.getById(creatorId);

        Room room = new Room();
        room.setName(name);
        room.setDescription(description);
        room.setCreatedBy(creator);
        room.setCreatedAt(LocalDateTime.now());
        room.setActive(true);

        Room savedRoom = roomRepo.save(room);

        RoomMembership membership = new RoomMembership(savedRoom, creator, RoomRole.MANAGER);
        roomMembershipRepo.save(membership);

        return savedRoom;
    }

    public Room getById(Long roomId) {
        return roomRepo.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found: " + roomId));
    }

    public List<Room> getRoomsForUser(Long userId) {
        List<RoomMembership> memberships = roomMembershipRepo.findByUserId(userId);
        return memberships.stream()
                .map(RoomMembership::getRoom)
                .toList();
    }

    public Room updateRoom(Long roomId, String name, String description) {
        Room room = getById(roomId);
        room.setName(name);
        room.setDescription(description);
        return roomRepo.save(room);
    }

    public void deactivateRoom(Long roomId) {
        Room room = getById(roomId);
        room.setActive(false);
        roomRepo.save(room);
    }
}