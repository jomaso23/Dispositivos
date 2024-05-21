package registro.registro_disp2.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoleDTO {

    private Integer id;

    @Size(max = 255)
    private String role;

    private LocalDate grantedDate;

    @NotNull
    private Integer user;

}
