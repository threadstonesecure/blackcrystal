package blackcrystal.it

import blackcrystal.Application
import blackcrystal.model.JobExecution
import blackcrystal.repository.ExecutionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import java.time.OffsetDateTime

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = Application.class)
class ExecutionRepositorySpec extends Specification {

    @Autowired
    private ExecutionRepository executionRepository;

    void "execution information should be persisted"() {
        given:
        JobExecution expected = new JobExecution();
        expected.setJobName("RandomJobName");
        when:
        JobExecution actual = executionRepository.save(expected);
        then:
        expected == actual
    }

    void "should retrieve execution information of a job correctly"() {
        when:
        JobExecution result = executionRepository.findByJob("TestJob1", 1);
        then:
        result != null
        result.id == 101L
    }

    void "should retrieve execution information correctly"() {
        when:
        JobExecution result = executionRepository.findOne(101L);
        then:
        result != null
        result.id == 101L
    }

    void "should retrieve next execution ID "() {
        given:
        def lastExecutionId = 10
        when:
        Integer actual = executionRepository.findLastExecutionID("TestJob1");
        then:
        lastExecutionId == actual
    }


    void "should retrieve next execution ID1 "() {
        given:
        Pageable p = new PageRequest(0, 5, Sort.Direction.DESC, "executionId");
        when:
        Page<JobExecution> result = executionRepository.findByJob("TestJob1", p);
        then:
        result != null
        result.getTotalElements() == 10;
        result.getTotalPages() == 2
        result.getContent().size() == 5
        result.getContent().get(0).executionId == 10
    }


    void "should convert the OffsetDateTime correctly"() {
        when:
        JobExecution result = executionRepository.findByJob("TestJob1", 1);
        then:
        result != null
        result.startTime == OffsetDateTime.parse('2015-12-29T22:59:00.020+01:00')
        result.endTime == OffsetDateTime.parse('2015-12-29T22:59:11.328+01:00')
    }
}
