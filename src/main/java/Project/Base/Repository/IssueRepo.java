package Project.Base.Repository;

import Project.Base.Entity.Issue;
import Project.Base.Entity.Enums.IssueStatus;
import Project.Base.Entity.Enums.IssuePriority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepo extends JpaRepository<Issue, Long> {


    List<Issue> findByRoomId(Long roomId);


    List<Issue> findByRoomIdAndStatus(Long roomId, IssueStatus status);


    List<Issue> findByRoomIdAndAssignedToId(Long roomId, Long userId);


    List<Issue> findByCreatedById(Long userId);


    List<Issue> findByRoomIdAndPriorityOrderByCreatedATDesc(Long roomId, IssuePriority priority);


    long countByRoomIdAndStatus(Long roomId, IssueStatus status);
}