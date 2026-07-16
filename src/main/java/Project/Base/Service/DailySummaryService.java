package Project.Base.Service;

import Project.Base.Exception.ResourceNotFoundException;
import Project.Base.Entity.DailySummary;
import Project.Base.Entity.Issue;
import Project.Base.Entity.Room;
import Project.Base.Entity.User;
import Project.Base.Repository.DailySummaryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DailySummaryService {

    private final DailySummaryRepo dailySummaryRepo;
    private final RoomService roomService;
    private final UserService userService;
    private final IssueService issueService;

    @Autowired
    public DailySummaryService(DailySummaryRepo dailySummaryRepo, RoomService roomService,
                               UserService userService, IssueService issueService) {
        this.dailySummaryRepo = dailySummaryRepo;
        this.roomService = roomService;
        this.userService = userService;
        this.issueService = issueService;
    }

    public DailySummary createSummary(Long roomId, Long creatorId, String title,
                                      String content, List<Long> relatedIssueIds) {

        Room room = roomService.getById(roomId);
        User creator = userService.getById(creatorId);

        DailySummary summary = new DailySummary(room, title, content, creator);

        if (relatedIssueIds != null && !relatedIssueIds.isEmpty()) {
            List<Issue> relatedIssues = relatedIssueIds.stream()
                    .map(issueService::getById)
                    .toList();
            summary.setRelatedIssues(relatedIssues);
        }

        return dailySummaryRepo.save(summary);
    }

    public DailySummary updateSummary(Long summaryId, String title, String content) {
        DailySummary summary = getById(summaryId);
        summary.setTitle(title);
        summary.setContent(content);
        return dailySummaryRepo.save(summary);
    }

    public DailySummary getById(Long summaryId) {
        return dailySummaryRepo.findById(summaryId)
                .orElseThrow(() -> new ResourceNotFoundException("Summary not found: " + summaryId));
    }

    public List<DailySummary> getSummariesForRoom(Long roomId) {
        return dailySummaryRepo.findByRoomIdOrderByCreatedAtDesc(roomId);
    }

    public DailySummary getLatestSummary(Long roomId) {
        return dailySummaryRepo.findFirstByRoomIdOrderByCreatedAtDesc(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("No summary found for room: " + roomId));
    }

    public void deleteSummary(Long summaryId) {
        DailySummary summary = getById(summaryId);
        dailySummaryRepo.delete(summary);
    }
}