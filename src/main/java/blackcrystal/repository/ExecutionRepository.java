package blackcrystal.repository;

import blackcrystal.model.JobExecution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ExecutionRepository extends CrudRepository<JobExecution, Long> {

    JobExecution findOne(Long id);

    JobExecution save(JobExecution j);

    @Query("select j from JobExecution j where j.jobName = ?1 and j.executionId = ?2")
    JobExecution findByJob(String jobName, Integer executionId);

    @Query("select max(j.executionId) from JobExecution j where j.jobName = ?1")
    Integer findLastExecutionID(String jobName);


    @Query("select j from JobExecution j where j.jobName = ?1")
    Page<JobExecution> findByJob(String jobName, Pageable pageable);

}

