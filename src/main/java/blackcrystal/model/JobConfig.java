package blackcrystal.model;

public class JobConfig {

    public String command;

    public String executionDirectory;

    public String name;

    public String resourceName;

    public String executionTime;

    public boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobConfig jobConfig = (JobConfig) o;

        if (enabled != jobConfig.enabled) return false;
        if (command != null ? !command.equals(jobConfig.command) : jobConfig.command != null) return false;
        if (executionDirectory != null ? !executionDirectory.equals(jobConfig.executionDirectory) : jobConfig.executionDirectory != null)
            return false;
        if (name != null ? !name.equals(jobConfig.name) : jobConfig.name != null) return false;
        if (resourceName != null ? !resourceName.equals(jobConfig.resourceName) : jobConfig.resourceName != null) return false;
        return !(executionTime != null ? !executionTime.equals(jobConfig.executionTime) : jobConfig.executionTime != null);

    }

}


