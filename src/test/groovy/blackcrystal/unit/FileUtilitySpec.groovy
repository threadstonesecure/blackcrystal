package blackcrystal.unit

import blackcrystal.model.JobConfig
import blackcrystal.utility.FileUtility
import blackcrystal.utility.TestUtils
import spock.lang.Specification

import java.nio.file.Path

class FileUtilitySpec extends Specification {

    TestUtils testUtils = new TestUtils();

    def "load file"() {
        when:
        JobConfig jobConfig = FileUtility.getJobConfig(testUtils.seconfConfigFile);
        then:
        jobConfig != null
    }


    def "parse job config correctly"() {
        when:
        JobConfig jobConfig = FileUtility.getJobConfig(testUtils.seconfConfigFile);
        then:
        jobConfig.name == "TestJob2"
        jobConfig.executionDirectory == "/execution/directory"
        jobConfig.sourcePath == "/source/path"
        jobConfig.executionTime == "0 0/1 * * * ?"
        jobConfig.command == "/execute/something"
    }


    def "return only sub directories"() {
        when:
        List<Path> directories = FileUtility.getSubDirectories(testUtils.jobsDirectory);
        then:
        directories != null
        directories.size() == 2
    }


}
