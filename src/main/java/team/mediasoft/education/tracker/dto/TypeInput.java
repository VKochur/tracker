package team.mediasoft.education.tracker.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TypeInput {

    @NotBlank(message = "name can't be empty")
    @Size(max = 50, message = "name's length must be in [1, 50]")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
