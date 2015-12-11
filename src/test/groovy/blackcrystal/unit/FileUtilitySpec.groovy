package blackcrystal.unit


import blackcrystal.data.domain.JobConfig
import blackcrystal.utility.FileUtility
import blackcrystal.utility.TestUtils
import spock.lang.Specification

class FileUtilitySpec extends Specification {

    TestUtils testUtils = new TestUtils();

    def "load file"() {
        JobConfig jobConfig = FileUtility.getJobConfig(testUtils.firstConfigFile);

        expect:
        jobConfig != null
    }


    def "parse job config correctly"() {
        JobConfig jobConfig = FileUtility.getJobConfig(testUtils.firstConfigFile);

        expect:
        jobConfig.name == "TestJob1"
        jobConfig.executionDirectory == "/execution/directory"
        jobConfig.sourcePath == "/source/path"
        jobConfig.executionTime == "0 0/1 * * * ?"
        jobConfig.command == "/execute/something"
    }


}
