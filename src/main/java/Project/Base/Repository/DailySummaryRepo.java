package Project.Base.Repository;

import Project.Base.Entity.DailySummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DailySummaryRepo extends JpaRepository<DailySummary, Long> {


    List<DailySummary> findByRoomIdOrderByCreatedAtDesc(Long roomId);


    Optional<DailySummary> findFirstByRoomIdOrderByCreatedAtDesc(Long roomId);
}