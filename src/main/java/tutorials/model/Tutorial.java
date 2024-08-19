package tutorials.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
public class Tutorial {

    @Id
    private UUID id;
    @NotNull
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

}
