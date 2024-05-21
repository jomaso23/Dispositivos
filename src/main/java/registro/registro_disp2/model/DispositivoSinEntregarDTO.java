package registro.registro_disp2.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DispositivoSinEntregarDTO {
	private Integer id;
	private String tipo;
	private String marca;
	private String serial;
	private Boolean entregado;
	private Integer idEntrega;
	
}
