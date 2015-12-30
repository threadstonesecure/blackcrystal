package blackcrystal.utility

import blackcrystal.model.JobConfig
import blackcrystal.model.JobExecution
import blackcrystal.model.Resource
import blackcrystal.model.ResourceType

import java.nio.file.Path
import java.nio.file.Paths
import java.time.OffsetDateTime

class TestUtils {

    String testWorkspace = getPath("/test_workspace")
    Path jobsDirectory = Paths.get(testWorkspace, "jobs")
    Path firstConfigFile = jobsDirectory.resolve("TestJob1").resolve("config.json")
    Path seconfConfigFile = jobsDirectory.resolve("TestJob2").resolve("config.json")

    String getPath(String file) {
        getClass().getResource(file).getPath()
    }

    def  getFirstJobConfig(){
        JobConfig jobConfig = new JobConfig()
        jobConfig.command = "/execute/something"
        jobConfig.executionDirectory = "/execution/directory"
        jobConfig.name = "TestJob1"
        jobConfig.resourceName = "/source/path"
        jobConfig.executionTime = "0 0/1 * * * ?"
        jobConfig.enabled = false
        jobConfig
    }


    def getSecondTestConfig(){
        JobConfig jobConfig = new JobConfig()
        jobConfig.command = "/execute/something"
        jobConfig.executionDirectory = "/execution/directory"
        jobConfig.name = "TestJob2"
        jobConfig.resourceName = "/source/path"
        jobConfig.executionTime = "0 0/1 * * * ?"
        jobConfig.enabled = false
        jobConfig
    }

    def getThirdTestConfig(){
        JobConfig jobConfig = new JobConfig()
        jobConfig.command = "/execute/something"
        jobConfig.executionDirectory = "/execution/directory"
        jobConfig.name = "TestJob3"
        jobConfig.resourceName = "/source/path"
        jobConfig.executionTime = "0 0/1 * * * ?"
        jobConfig.enabled = false
        jobConfig
    }


    def getTestJobForDeletion(){
        JobConfig jobConfig = new JobConfig()
        jobConfig.command = "/execute/something"
        jobConfig.executionDirectory = "/execution/directory"
        jobConfig.name = "TestJobForDeletion"
        jobConfig.resourceName = "/source/path"
        jobConfig.executionTime = "0 0/1 * * * ?"
        jobConfig.enabled = false
        jobConfig
    }

    def  getExecutionResult(){
        JobExecution jobExecution = new JobExecution()
        jobExecution.setJobName("TestJob1")
        jobExecution.setExecutionId(1)
        jobExecution.setStartTime(OffsetDateTime.parse("2015-12-29T22:59:00.020+01:00"))
        jobExecution.setEndTime(OffsetDateTime.parse("2015-12-29T22:59:11.328+01:00"))
        jobExecution.setDuration(10)
        jobExecution.setResult(3)
        jobExecution
    }
    def getResourceConfig(){
        Resource resource = new Resource()
        resource.repository = "http://"
        resource.type = ResourceType.GIT
        resource.name = "TestResource3"
        resource.version = "master"
        resource
    }

    def getResourceConfig2(){
        Resource resource = new Resource()
        resource.repository = "http://"
        resource.type = ResourceType.GIT
        resource.name = "TestResource1"
        resource.version = "master"
        resource
    }
}
