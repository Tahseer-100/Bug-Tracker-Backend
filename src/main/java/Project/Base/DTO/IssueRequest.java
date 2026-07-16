package Project.Base.DTO;

import Project.Base.Entity.Enums.IssuePriority;

public class IssueRequest {

    private Long creatorId;        //Temporary
    private String title;
    private String description;
    private String stepsToReproduce;
    private String expectedBehavior;
    private String actualBehavior;
    private String environment;
    private IssuePriority priority;

    public IssueRequest() {}

    public Long getCreatorId() { return creatorId; }
    public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStepsToReproduce() { return stepsToReproduce; }
    public void setStepsToReproduce(String s) { this.stepsToReproduce = s; }

    public String getExpectedBehavior() { return expectedBehavior; }
    public void setExpectedBehavior(String s) { this.expectedBehavior = s; }

    public String getActualBehavior() { return actualBehavior; }
    public void setActualBehavior(String s) { this.actualBehavior = s; }

    public String getEnvironment() { return environment; }
    public void setEnvironment(String environment) { this.environment = environment; }

    public IssuePriority getPriority() { return priority; }
    public void setPriority(IssuePriority priority) { this.priority = priority; }
}