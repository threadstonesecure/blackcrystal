package blackcrystal.service;

import blackcrystal.app.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * Serves all file and directory paths that are used for jobs and executions
 */
@Component("directoryService")
public class DirectoryService {

    @Autowired
    private ApplicationProperties properties;


    /**
     * ./workspace/jobs
     * @return
     */
    public Path jobsDirectory(){
        return properties.jobsDirectory();
    }

    /**
     * ./workspace/jobs/job_name
     * @return
     */
    public Path jobDirectory(String name){
        return this.jobsDirectory().resolve(name);
    }


    /**
     * ./workspace/jobs/job_name/config.json
     * @param name
     * @return
     */
    public Path configFile(String name) {
        return jobDirectory(name).resolve("config.json");
    }

    /**
     * ./workspace/jobs/job_name/execution.json
     * @param name
     * @return
     */
    public Path executionFile(String name) {
        return jobDirectory(name).resolve("execution.json");
    }

    /**
     * ./workspace/jobs/job_name/execution_id
     * @param name
     * @param executionId
     * @return
     */
    public Path executionDirectory(String name, String executionId) {
        return jobDirectory(name).resolve(executionId);
    }


    /**
     * ./workspace/jobs/job_name/execution_id/result.json
     * @param name
     * @param executionId
     * @return
     */
    public Path executionResultFile(String name, String executionId) {
        return executionDirectory(name, executionId).resolve("result.json");
    }


    /**
     * ./workspace/jobs/job_name/execution_id/log
     * @param name
     * @param executionId
     * @return
     */
    public Path executionLog(String name, String executionId) {
        return executionDirectory(name, executionId).resolve("log");
    }

}
