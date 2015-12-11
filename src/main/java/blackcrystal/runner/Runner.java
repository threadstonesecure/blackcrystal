package blackcrystal.runner;

import blackcrystal.model.JobConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.concurrent.Callable;


@Component
public class Runner implements Runnable, Callable {

    private static final Logger logger = LoggerFactory
            .getLogger(Runner.class);

    public Runner() {

    }

    private JobConfig jobConfig;

    public Runner(JobConfig jobConfig) {
        this.jobConfig = jobConfig;
    }


    @Override
    public void run() {
        logger.debug("Running job " + jobConfig.name);
    }


    public void runProcess() {
        Process process = null;
        try {
            ProcessBuilder pb = new ProcessBuilder(jobConfig.command.split(" "));
            pb.directory(new File(jobConfig.executionDirectory));
            pb.redirectErrorStream(true);
            process = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream stdout = process.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));

        String line;
        //TODO  : Redirect stdout to log file
        try {
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Object call() throws Exception {
        return null;
    }
}