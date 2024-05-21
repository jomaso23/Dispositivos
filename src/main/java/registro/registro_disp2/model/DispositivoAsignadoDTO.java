package registro.registro_disp2.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DispositivoAsignadoDTO {

    private Integer id;
    private LocalDateTime fechaAsignacion;
    private Integer user;
    private Long dispositivo;

}
