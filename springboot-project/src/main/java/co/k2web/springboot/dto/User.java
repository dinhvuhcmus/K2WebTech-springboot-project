package co.k2web.springboot.dto;

import javax.validation.constraints.NotBlank;

public class User {
    @NotBlank
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
