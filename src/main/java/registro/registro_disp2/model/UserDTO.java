package registro.registro_disp2.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Integer id;

    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String apellido;

    @Size(max = 100)
    private String documento;

    @Size(max = 100)
    private String userName;

    @Size(max = 255)
    private String passwordHash;
    
    @Override
    public String toString() {
        return super.toString();
    }
}
