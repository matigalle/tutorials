package tutorials.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tutorials.model.Tutorial;

import java.util.List;
import java.util.UUID;

@Repository
public interface TutorialsRepository extends CrudRepository<Tutorial, UUID> {

    List<Tutorial> findByPublished(Boolean published);

    List<Tutorial> findByTitleContaining(String title);

}
