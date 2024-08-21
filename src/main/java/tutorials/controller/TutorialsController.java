package tutorials.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tutorials.model.Tutorial;
import tutorials.repository.TutorialsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tutorials")
public class TutorialsController {

    private final TutorialsRepository tutorialsRepository;

    @Autowired
    public TutorialsController(TutorialsRepository tutorialsRepository) {
        this.tutorialsRepository = tutorialsRepository;
    }

    @PostMapping
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        Tutorial savedTutorial = tutorialsRepository.save(new Tutorial(
                tutorial.getTitle(),
                tutorial.getDescription(),
                tutorial.getPublished()));

        return new ResponseEntity<>(savedTutorial, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Tutorial>> getTutorials(@Param("title") String title) {
        List<Tutorial> tutorials = new ArrayList<>();

        if (title != null) {
            tutorials = tutorialsRepository.findByTitleContaining(title);
        } else {
            tutorialsRepository.findAll().forEach(tutorials::add);
        }

        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable UUID id) {
        Optional<Tutorial> tutorial = tutorialsRepository.findById(id);

        if (tutorial.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(tutorial.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tutorial> updateTutorialById(@PathVariable UUID id, @RequestBody Tutorial tutorial) {
        Optional<Tutorial> savedTutorial = tutorialsRepository.findById(id);

        if (savedTutorial.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        tutorial.setId(savedTutorial.get().getId());

        return new ResponseEntity<>(tutorialsRepository.save(tutorial), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTutorialById(@PathVariable UUID id) {
        if (tutorialsRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        tutorialsRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        tutorialsRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/published")
    public ResponseEntity<List<Tutorial>> getPublishedTutorials() {
        List<Tutorial> publishedTutorials = tutorialsRepository.findByPublished(true);

        return new ResponseEntity<>(publishedTutorials, HttpStatus.OK);
    }

}
