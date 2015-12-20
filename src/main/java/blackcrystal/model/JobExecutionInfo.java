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
}


