package Project.Base.Repository;

import Project.Base.Entity.ActivityLog;
import Project.Base.Entity.Enums.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityLogRepo extends JpaRepository<ActivityLog, Long> {


    List<ActivityLog> findByRoomIdOrderByTimestampDesc(Long roomId);

    List<ActivityLog> findByEntityTypeAndEntityIdOrderByTimestampAsc(EntityType entityType, Long entityId);

    List<ActivityLog> findByUserId(Long userId);
}