package blackcrystal.model;

import java.util.List;

public class JobExecutionInfo {

    public Integer lastExecutionId;

    public List<Integer> executions;

    public JobExecutionInfo() {
    }

    public JobExecutionInfo(Integer lastJobId, List<Integer> executions) {
        this.lastExecutionId = lastJobId;
        this.executions = executions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobExecutionInfo that = (JobExecutionInfo) o;

        if (lastExecutionId != null ? !lastExecutionId.equals(that.lastExecutionId) : that.lastExecutionId != null)
            return false;
        return !(executions != null ? !executions.equals(that.executions) : that.executions != null);

    }
}


