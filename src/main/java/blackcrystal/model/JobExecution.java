package blackcrystal.model;

import blackcrystal.utility.OffsetDateTimeTypeConverter;
import com.google.gson.annotations.JsonAdapter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
public class JobExecution implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String jobName;

    @Column
    private Integer executionId;

    @Column(nullable = true)
    private OffsetDateTime startTime;

    @Column(nullable = true)
    private OffsetDateTime endTime;

    @Column(nullable = true)
    private Long duration;

    @Column(nullable = true)
    private Integer result;

    @Column(nullable = true)
    private String executedCommand;


    public JobExecution() {
    }

    public JobExecution(Long id, String jobName, Integer executionId) {
        this.id = id;
        this.jobName = jobName;
        this.executionId = executionId;
    }

    public Long getId() {
        return id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getExecutionId() {
        return executionId;
    }

    public void setExecutionId(Integer executionId) {
        this.executionId = executionId;
    }

    public OffsetDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(OffsetDateTime startTime) {
        this.startTime = startTime;
    }

    public OffsetDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(OffsetDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getExecutedCommand() {
        return executedCommand;
    }

    public void setExecutedCommand(String executedCommand) {
        this.executedCommand = executedCommand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobExecution that = (JobExecution) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (jobName != null ? !jobName.equals(that.jobName) : that.jobName != null) return false;
        if (executionId != null ? !executionId.equals(that.executionId) : that.executionId != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (result != null ? !result.equals(that.result) : that.result != null) return false;
        return !(executedCommand != null ? !executedCommand.equals(that.executedCommand) : that.executedCommand != null);

    }

    @Override
    public int hashCode() {
        int result1 = id != null ? id.hashCode() : 0;
        result1 = 31 * result1 + (jobName != null ? jobName.hashCode() : 0);
        result1 = 31 * result1 + (executionId != null ? executionId.hashCode() : 0);
        result1 = 31 * result1 + (startTime != null ? startTime.hashCode() : 0);
        result1 = 31 * result1 + (endTime != null ? endTime.hashCode() : 0);
        result1 = 31 * result1 + (duration != null ? duration.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (executedCommand != null ? executedCommand.hashCode() : 0);
        return result1;
    }
}


