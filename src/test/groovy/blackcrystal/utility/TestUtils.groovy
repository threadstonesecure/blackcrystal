package blackcrystal.utility

class TestUtils {

    String firstConfigFile = getPath("/sampleconfig/TestJob1/config.json")
    String seconfConfigFile = getPath("/sampleconfig/TestJob1/config.json")

    String getPath(String file) {
        getClass().getResource(file).getPath()
    }
}
