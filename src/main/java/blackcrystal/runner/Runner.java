package blackcrystal.runner;

import blackcrystal.app.ApplicationProperties;
import blackcrystal.model.JobConfig;
import blackcrystal.model.JobExecution;
import blackcrystal.service.DirectoryService;
import blackcrystal.service.ExecutionService;
import blackcrystal.utility.FileUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.OffsetDateTime;

public class Runner implements Runnable {

    private static final Logger logger =
            LoggerFactory.getLogger(Runner.class);

    private JobConfig jobConfig;

    @Autowired
    private ExecutionService executionService;

    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private ApplicationProperties properties;

    public boolean running = true;

    public Runner() {
    }

    public Runner(JobConfig jobConfig) {
        this.jobConfig = jobConfig;
    }


    @Override
    public void run() {

        OffsetDateTime startTime = OffsetDateTime.now();
        Process process = null;
        Integer executionId = executionService.getNextExecId(jobConfig);
        Path executionDirectory = directoryService.executionDirectory(jobConfig.name, executionId.toString());
        Path executionLog = directoryService.executionLog(jobConfig.name, executionId.toString());

        FileUtility.createDirectory(executionDirectory);

        logger.debug("starting execution of : " + jobConfig.name);
        try {
            ProcessBuilder pb = new ProcessBuilder(jobConfig.command.split(" "));
            pb.environment().put("ELASTICSEARCH_SERVER", properties.getElasticSearchHost());
            pb.environment().put("ELASTICSEARCH_PORT",  properties.getElasticSearchPort());
            pb.environment().put("ELASTICSEARCH_INDEX", properties.getElasticSearchIndex());

            File log = new File(executionLog.toString());
            pb.directory(new File(jobConfig.executionDirectory));
            pb.redirectErrorStream(true);
            pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));

            process = pb.start();
        } catch (IOException e) {
            logger.error("Error while execution job : " + jobConfig.name, e);
        }

        while (process.isAlive()) {
            if (running == false) {
                logger.info("Attempting to destroy the process for the job : " + jobConfig.name);
                process.destroy();
            }
        }

        OffsetDateTime endTime = OffsetDateTime.now();
        Duration duration = Duration.between(startTime, endTime);

        JobExecution jobExecution = new JobExecution();
        jobExecution.setResult(process.exitValue());
        jobExecution.setExecutionId(executionId);
        jobExecution.setJobName(jobConfig.name);
        jobExecution.setDuration(duration.getSeconds());
        jobExecution.setStartTime(startTime);
        jobExecution.setEndTime(endTime);

        executionService.writeExecutionResult(jobConfig, jobExecution);
    }

}