package tutorials.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tutorials.model.Tutorial;
import tutorials.repository.TutorialsRepository;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TutorialsController.class)
class TutorialsControllerTest {

    @MockBean
    TutorialsRepository tutorialsRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void create_tutorial() throws Exception {
        Tutorial tutorial = getTestTutorial();

        mockMvc.perform(MockMvcRequestBuilders.post("/tutorials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tutorial)))
                .andExpect(status().isCreated());
    }

    @Test
    void return_list_of_tutorials() throws Exception {
        Tutorial tutorial1 = new Tutorial("Tutorial 1", null, true);
        Tutorial tutorial2 = new Tutorial("Tutorial 2", null, false);
        List<Tutorial> tutorials = new ArrayList<>(Arrays.asList(tutorial1, tutorial2));

        Mockito.when(tutorialsRepository.findAll()).thenReturn(tutorials);

        mockMvc.perform(MockMvcRequestBuilders.get("/tutorials"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(tutorials.size()));
    }

    @Test
    void return_list_of_tutorials_with_title_filter() throws Exception {
        Tutorial tutorial1 = new Tutorial("Tutorial 1", null, true);
        Tutorial tutorial2 = new Tutorial("Tutorial 2", null, false);
        String filter = "Tutorial";
        List<Tutorial> tutorials = Arrays.asList(tutorial1, tutorial2);

        Mockito.when(tutorialsRepository.findByTitleContaining(filter)).thenReturn(tutorials);

        mockMvc.perform(MockMvcRequestBuilders.get("/tutorials").param("title", filter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(tutorials.size()));
    }

    @Test
    void return_tutorial() throws Exception {
        Tutorial tutorial = getTestTutorial();
        UUID id = tutorial.getId();
        RequestBuilder getRequest = MockMvcRequestBuilders.get("/tutorials/{id}", id);

        Mockito.when(tutorialsRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(getRequest)
                .andExpect(status().isNotFound());

        Mockito.when(tutorialsRepository.findById(id)).thenReturn(Optional.of(tutorial));

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.title").value(tutorial.getTitle()))
                .andExpect(jsonPath("$.description").value(tutorial.getDescription()))
                .andExpect(jsonPath("$.published").value(tutorial.getPublished()));
    }

    @Test
    void update_tutorial() throws Exception {
        Tutorial savedTutorial = getTestTutorial();
        UUID id = savedTutorial.getId();
        Tutorial updatedTutorial = new Tutorial("Updated title", "Updated description", savedTutorial.getPublished());
        updatedTutorial.setId(id);
        RequestBuilder putRequest = MockMvcRequestBuilders
                .put("/tutorials/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTutorial));

        Mockito.when(tutorialsRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(putRequest)
                .andExpect(status().isNotFound());

        Mockito.when(tutorialsRepository.findById(id)).thenReturn(Optional.of(savedTutorial));
        Mockito.when(tutorialsRepository.save(any(Tutorial.class))).thenReturn(updatedTutorial);

        mockMvc.perform(putRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.title").value(updatedTutorial.getTitle()))
                .andExpect(jsonPath("$.description").value(updatedTutorial.getDescription()))
                .andExpect(jsonPath("$.published").value(updatedTutorial.getPublished()));
    }

    @Test
    void delete_tutorial() throws Exception {
        Tutorial tutorial = getTestTutorial();
        UUID id = tutorial.getId();
        RequestBuilder deleteRequest = MockMvcRequestBuilders.delete("/tutorials/{id}", id);

        Mockito.when(tutorialsRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(deleteRequest)
                .andExpect(status().isNotFound());

        Mockito.when(tutorialsRepository.findById(id)).thenReturn(Optional.of(tutorial));
        Mockito.doNothing().when(tutorialsRepository).deleteById(id);

        mockMvc.perform(deleteRequest)
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_all_tutorials() throws Exception {
        Mockito.doNothing().when(tutorialsRepository).deleteAll();

        mockMvc.perform(MockMvcRequestBuilders.delete("/tutorials"))
                .andExpect(status().isNoContent());
    }

    @Test
    void return_published_tutorials() throws Exception {
        Tutorial tutorial1 = new Tutorial("Tutorial 1", null, true);
        Tutorial tutorial2 = new Tutorial("Tutorial 2", null, true);
        List<Tutorial> publishedTutorials = Arrays.asList(tutorial1, tutorial2);

        Mockito.when(tutorialsRepository.findByPublished(true)).thenReturn(publishedTutorials);

        mockMvc.perform(MockMvcRequestBuilders.get("/tutorials/published"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(publishedTutorials.size()));
    }

    private Tutorial getTestTutorial() {
        return new Tutorial("Test title", "Test description", false);
    }

}
