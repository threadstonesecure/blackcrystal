package blackcrystal.utility

import blackcrystal.model.JobConfig

import java.nio.file.Path
import java.nio.file.Paths

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
        jobConfig.sourcePath = "/source/path"
        jobConfig.executionTime = "0 0/1 * * * ?"
        jobConfig
    }


    def getSecondTestConfig(){
        JobConfig jobConfig = new JobConfig()
        jobConfig.command = "/execute/something"
        jobConfig.executionDirectory = "/execution/directory"
        jobConfig.name = "TestJob2"
        jobConfig.sourcePath = "/source/path"
        jobConfig.executionTime = "0 0/1 * * * ?"
        jobConfig
    }

    def getThirdTestConfig(){
        JobConfig jobConfig = new JobConfig()
        jobConfig.command = "/execute/something"
        jobConfig.executionDirectory = "/execution/directory"
        jobConfig.name = "TestJob3"
        jobConfig.sourcePath = "/source/path"
        jobConfig.executionTime = "0 0/1 * * * ?"
        jobConfig
    }
}
