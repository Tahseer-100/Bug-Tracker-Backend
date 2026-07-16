package Project.Base.Service;

import Project.Base.Entity.Enums.ActivityAction;
import Project.Base.Entity.Enums.EntityType;
import Project.Base.Entity.ActivityLog;
import Project.Base.Entity.Room;
import Project.Base.Entity.User;
import Project.Base.Repository.ActivityLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityLogService {

    private final ActivityLogRepo activityLogRepo;

    @Autowired
    public ActivityLogService(ActivityLogRepo activityLogRepo) {
        this.activityLogRepo = activityLogRepo;
    }

    public ActivityLog log(Room room, User user, ActivityAction action,
                           EntityType entityType, Long entityId,
                           String oldValue, String newValue) {

        ActivityLog entry = new ActivityLog(room, user, action, entityType, entityId, oldValue, newValue);
        return activityLogRepo.save(entry);
    }

    public List<ActivityLog> getRoomActivity(Long roomId) {
        return activityLogRepo.findByRoomIdOrderByTimestampDesc(roomId);
    }

    public List<ActivityLog> getEntityHistory(EntityType entityType, Long entityId) {
        return activityLogRepo.findByEntityTypeAndEntityIdOrderByTimestampAsc(entityType, entityId);
    }
}