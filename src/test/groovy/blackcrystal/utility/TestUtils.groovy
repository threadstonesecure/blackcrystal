package blackcrystal.utility

import blackcrystal.model.JobConfig

class TestUtils {

    String testWorkspace = getPath("/test_workspace")
    String jobsDirectory = "$testWorkspace/jobs"
    String firstConfigFile = "$jobsDirectory/TestJob1/config.json"
    String seconfConfigFile = "$jobsDirectory/TestJob2/config.json"

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
}
