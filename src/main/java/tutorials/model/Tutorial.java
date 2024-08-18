package tutorials.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Tutorial {

    @Id
    private UUID id;
    private String title;
    private String description;
    private Boolean published;

    public Tutorial() {
    }

    public Tutorial(String title, String description, Boolean published) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.published = published;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

}
