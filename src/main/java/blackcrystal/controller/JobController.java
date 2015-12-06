package blackcrystal.controller;

import blackcrystal.app.ApplicationProperties;
import blackcrystal.data.domain.JobConfig;
import blackcrystal.data.service.JobService;
import blackcrystal.runner.domain.Queue;
import blackcrystal.runner.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class JobController {

	@Autowired
	private JobService jobService;

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private QueueService queueService;


    @RequestMapping("/job/{name}")
    @ResponseBody
    public JobConfig getJob(@PathVariable String name) throws Exception{
        try{
            return this.jobService.getJob(name);
        }catch (Exception e){
            throw e;
        }
    }

    @RequestMapping("/job/{name}/run")
    @ResponseBody
    public String runJob(@PathVariable String name){
        try{
            queueService.add(new Queue(this.jobService.getJob(name)));
        }catch (Exception e){

        }

        return "ok";
    }


    @RequestMapping("/queue")
    @ResponseBody
    public List<Queue> queue(){
        return queueService.getAll();
    }


    @RequestMapping("/prop")
    @ResponseBody
    @Transactional(readOnly = true)
    public ApplicationProperties getProb() {
        return this.properties;
    }



}
