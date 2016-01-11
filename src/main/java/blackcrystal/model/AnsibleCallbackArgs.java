package blackcrystal.model;

public class AnsibleCallbackArgs {

    public String jobName;

    public String executionId;

    public AnsibleCallbackArgs(String jobName, String executionId) {
        this.jobName = jobName;
        this.executionId = executionId;
    }
}
