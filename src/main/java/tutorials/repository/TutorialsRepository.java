package tutorials.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tutorials.model.Tutorial;

import java.util.UUID;

@Repository
public interface TutorialsRepository extends CrudRepository<Tutorial, UUID> {
}
