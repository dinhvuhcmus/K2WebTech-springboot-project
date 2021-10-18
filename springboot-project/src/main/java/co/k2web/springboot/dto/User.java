package co.k2web.springboot.dto;

import javax.validation.constraints.NotEmpty;

public class User {

    //content should not be empty
    @NotEmpty
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
