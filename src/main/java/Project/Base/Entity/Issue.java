package Project.Base.Entity;

import Project.Base.Entity.Enums.IssuePriority;
import Project.Base.Entity.Enums.IssueStatus;
import Project.Base.Entity.Enums.IssueType;
import jakarta.persistence.*;

import javax.print.attribute.standard.MediaSize;
import java.time.LocalDateTime;

@Entity
@Table(name="issues")
public class Issue {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String stepsToReproduce;

    @Column(columnDefinition = "TEXT")
    private String expectedBehavior;

    @Column(columnDefinition = "TEXT")
    private String actualBehavior;

    @Column
    private String environment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssuePriority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueType type;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to", nullable = true)
    private User assignedTo;

    @Column(nullable = false)
    private LocalDateTime createdAT;

    @Column(nullable = false)
    private LocalDateTime updatedAT;

    @Column(nullable = true)
    private LocalDateTime closedAT;

    public Issue() {}

    public Issue(Room room, String title, String description, IssuePriority priority, User createdBy) {
        this.room = room;
        this.title = title;
        this.description = description;
        this.status = IssueStatus.OPEN;
        this.priority = priority;
        this.type = IssueType.BUG;
        this.createdBy = createdBy;
        this.createdAT = LocalDateTime.now();
        this.updatedAT = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStepsToReproduce() {
        return stepsToReproduce;
    }

    public void setStepsToReproduce(String stepsToReproduce) {
        this.stepsToReproduce = stepsToReproduce;
    }

    public String getExpectedBehavior() {
        return expectedBehavior;
    }

    public void setExpectedBehavior(String expectedBehavior) {
        this.expectedBehavior = expectedBehavior;
    }

    public String getActualBehavior() {
        return actualBehavior;
    }

    public void setActualBehavior(String actualBehavior) {
        this.actualBehavior = actualBehavior;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    public IssueType getType() {
        return type;
    }

    public void setType(IssueType type) {
        this.type = type;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public LocalDateTime getCreatedAT() {
        return createdAT;
    }

    public void setCreatedAT(LocalDateTime createdAT) {
        this.createdAT = createdAT;
    }

    public LocalDateTime getUpdatedAT() {
        return updatedAT;
    }

    public void setUpdatedAT(LocalDateTime updatedAT) {
        this.updatedAT = updatedAT;
    }

    public LocalDateTime getClosedAT() {
        return closedAT;
    }

    public void setClosedAT(LocalDateTime closedAT) {
        this.closedAT = closedAT;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", type=" + type +
                ", createdAT=" + createdAT +
                '}';
    }
}
