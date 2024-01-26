package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"createdAt"})
public class User {
    private Integer id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;

    @Override
    public String toString() {
        return String.format(
                "{\n" +
                        "  \"email\": \"%s\",\n" +
                        "  \"first_name\": \"%s\",\n" +
                        "  \"last_name\": \"%s\",\n" +
                        "  \"avatar\": \"%s\"\n" +
                        "}",
                email, first_name, last_name, avatar
        );
    }
}