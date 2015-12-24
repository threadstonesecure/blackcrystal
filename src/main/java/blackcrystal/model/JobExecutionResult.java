package blackcrystal.model;

public class JobExecutionResult {

    public String startTime;

    public String endTime;

    public String duration;

    public String result;

    public JobExecutionResult() {
    }

    public JobExecutionResult(String startTime, String endTime, String duration, String result) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobExecutionResult that = (JobExecutionResult) o;

        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        return !(result != null ? !result.equals(that.result) : that.result != null);

    }

}


