package blackcrystal.runner;

import blackcrystal.model.JobConfig;
import blackcrystal.model.JobExecution;
import blackcrystal.service.DirectoryService;
import blackcrystal.service.ExecutionService;
import blackcrystal.service.JobConfigService;
import blackcrystal.utility.FileUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.concurrent.Callable;


@Component
public class Runner implements Runnable, Callable {

    private static final Logger logger =
            LoggerFactory.getLogger(Runner.class);

    private JobConfig jobConfig;


    private ExecutionService executionService;

    private DirectoryService directoryService;

    public boolean running = true;

    public Runner() {
    }

    //TODO : Temporary Solution - do not pass the reference of JobConfigService here
    public Runner(JobConfig jobConfig, ExecutionService executionService, DirectoryService directoryService) {
        this.jobConfig = jobConfig;
        this.executionService = executionService;
        this.directoryService = directoryService;
    }


    @Override
    public void run() {
        OffsetDateTime startTime = OffsetDateTime.now();
        Process process = null;
        Integer executionId = executionService.getNextExecId(jobConfig);
        Path executionDirectory = directoryService.executionDirectory(jobConfig.name, executionId.toString());
        Path executionLog = directoryService.executionLog(jobConfig.name, executionId.toString());

        FileUtility.createDirectory(executionDirectory);

        logger.info("starting execution of "+ jobConfig.name);
        try {
            ProcessBuilder pb = new ProcessBuilder(jobConfig.command.split(" "));
            //Map<String, String> env = pb.environment();
            //env.put("jobid", jobid);
            File log = new File(executionLog.toString());
            pb.directory(new File(jobConfig.executionDirectory));
            pb.redirectErrorStream(true);
            pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));

            process = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (process.isAlive()) {
            if (running == false) {
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

    @Override
    public Object call() throws Exception {
        return null;
    }
}