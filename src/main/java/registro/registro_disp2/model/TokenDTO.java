package registro.registro_disp2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TokenDTO {

    private Long id;

    @Size(max = 255)
    private String token;

    @JsonProperty("isLogged")
    private Boolean isLogged;

    private Integer user;

}
