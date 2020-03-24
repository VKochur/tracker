package team.mediasoft.education.tracker.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NodeInput {

    @NotBlank
    @Size(max = 255)
    private String postcode;

    @NotBlank
    @Size(max = 50)
    private String name;

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
