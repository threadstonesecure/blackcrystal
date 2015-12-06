package blackcrystal.data.repository;

import blackcrystal.data.domain.Job;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<Job, Long> {

}
