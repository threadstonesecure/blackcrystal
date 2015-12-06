package blackcrystal.runner.component;

import blackcrystal.data.domain.JobConfig;
import blackcrystal.runner.domain.Queue;
import org.springframework.stereotype.Component;

import java.io.*;


@Component
public class Runner implements Runnable {


    public Runner(){

    }
    private JobConfig jobConfig;

    public Runner(Queue queue) {
        this.jobConfig = queue.jobConfig;
    }

    public void run() {
        Process process= null;

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
        try{
            while ((line = reader.readLine ()) != null) {
                System.out.println (line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}