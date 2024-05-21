package registro.registro_disp2.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;


@Getter
@Setter
public class DispositivoDTO {

    private Long id;

    @Size(max = 255)
    private String tipo;

    @Size(max = 255)
    private String marca;

    @Size(max = 255)
    private String serial;
    
   

}
