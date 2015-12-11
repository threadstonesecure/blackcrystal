package blackcrystal.unit


import blackcrystal.model.JobConfig
import blackcrystal.utility.FileUtility
import blackcrystal.utility.TestUtils
import spock.lang.Specification

import java.nio.file.Path

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


    def "return only sub directories"() {
        List<Path> directories = FileUtility.getSubDirectories(testUtils.jobConfigDirectory);

        expect:
        directories != null
        directories.size() == 2
    }


}
