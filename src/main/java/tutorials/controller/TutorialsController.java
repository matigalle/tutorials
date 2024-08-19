package tutorials.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tutorials.model.Tutorial;
import tutorials.repository.TutorialsRepository;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<List<Tutorial>> getTutorials() {
        List<Tutorial> tutorials = new ArrayList<>();
        tutorialsRepository.findAll().forEach(tutorials::add);

        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

}
