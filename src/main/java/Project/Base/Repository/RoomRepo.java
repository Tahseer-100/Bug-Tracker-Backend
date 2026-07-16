package Project.Base.Repository;

import Project.Base.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepo extends JpaRepository<Room, Long> {

    Optional<Room> findByName(String name);

    boolean existsByName(String name);

    List<Room> findByActiveTrue();

    List<Room> findByCreatedById(Long userId);
}