package registro.registro_disp2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import registro.registro_disp2.model.DispositivoDTO;
import registro.registro_disp2.model.DispositivoSinEntregarDTO;
import registro.registro_disp2.model.UserDTO;
import registro.registro_disp2.service.DispositivoAsignadoService;
import registro.registro_disp2.service.DispositivoService;
import registro.registro_disp2.service.UserService;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Controller
public class AsignacionController {
	private  UserService userService;
	private  DispositivoService dispositivoService;
	private final DispositivoAsignadoService dispositivoAsignadoService;
	
	String mensajeAsignacion = "Selecc. un Disp. y Asigne";
	
	String mensajeAlumno = "Selecc. un Alum.";
	public AsignacionController(UserService userService, DispositivoService dispositivoService, DispositivoAsignadoService dispositivoAsignadoService) {
		this.userService = userService;
		this.dispositivoService = dispositivoService;
		this.dispositivoAsignadoService = dispositivoAsignadoService;
	}
	
	
	//	+++++++++++++++++++++++++++++++++++++++++++++++
	@GetMapping("/index")
	public String index( Model model) {
		model.addAttribute("mensajeAsignacion", mensajeAsignacion);
		model.addAttribute("mensajeAlumno", mensajeAlumno);
		return "index";
	}
//	+++++++++++++++++++++++++++++++++++++++++++++++

//	anteriormente la url era getAlumno
	@PostMapping("/getAlumno")
	public String getAlumno(@RequestParam(value = "id", required = false)String id, Model model){
	
		try {
			
			//metodo llamar alumno por ID de usuario
			UserDTO alumno = userService.get(id);
			
			String alumnoID = id;
			
			List<DispositivoDTO> dispositivos = dispositivoService.obtenerDispositivosNoAsignadosHoy();
			
			
			mensajeAlumno = "alumno ENCONTRADO";
			
			//ingresar los objetos mediante Model
			model.addAttribute("dispositivos", dispositivos);
			model.addAttribute("alumnoID", alumnoID);//este debo cambiar
		    model.addAttribute("alumno", alumno );
			model.addAttribute("mensajeAsignacion", mensajeAsignacion );
		    model.addAttribute("mensajeAlumno", mensajeAlumno);
			
		}catch (Exception e){
			
			mensajeAlumno = "alumno NO ENCONTRADO";
			model.addAttribute("mensajeAlumno", mensajeAlumno);
		}
		
		return "index";
	}
	//	+++++++++++++++++++++++++++++++++++++++++++++++
	@PostMapping("/getAlumnoSinEntregar")
	public String getAlumnoSinEntregar(@RequestParam(value = "id", required = false)String id, Model model){
		
		try {
			
			//metodo llamar alumno por ID de usuario
			UserDTO alumno = userService.get(id);
			
			String alumnoID = id;
			
			
			List<DispositivoSinEntregarDTO> dispositivos = dispositivoService.dispositivoSinEntregarDTOList(id);
			
			
			mensajeAlumno = "alumno ENCONTRADO";
			
			//ingresar los objetos mediante Model
			model.addAttribute("dispositivos", dispositivos);
			model.addAttribute("alumnoID", alumnoID);//ID alumno
			model.addAttribute("alumno", alumno );//OBJETO alumno
			
			model.addAttribute("mensajeAsignacion", mensajeAsignacion );
			model.addAttribute("mensajeAlumno", mensajeAlumno);
			
		}catch (Exception e){
			
			mensajeAlumno = "alumno NO ENCONTRADO";
			model.addAttribute("mensajeAlumno", mensajeAlumno);
		}
		
		return "consultaAlumno";
	}
	
	//	+++++++++++++++++++++++++++++++++++++++++++++++
	@PostMapping("/Entregar")
	public String consultaSinEntregarAlumno(@RequestParam(value="dispositivosSeleccionadosSinEntregar", required = false) List <String> dispositivosSeleccionadosSinEntregar , Model model){
		
		dispositivoAsignadoService.actualizarEstadoEntrega(dispositivosSeleccionadosSinEntregar);

		return "consultaAlumno";
	}
	
	
	//	+++++++++++++++++++++++++++++++++++++++++++++++

@PostMapping("/asignarDispositivos")
	public String asignarDispositivos(@RequestParam(value = "alumno") String alumno, @RequestParam(value="dispositivosSelecciondos", required = false) List<String>dispositivosSelecciondos, Model model){
	System.out.println(dispositivosSelecciondos);
	if (dispositivosSelecciondos == null){
		mensajeAsignacion = "SELCC. AL MENOS UN DISP.!";
	}else{
		mensajeAsignacion ="Asignacion enviada";
		dispositivoAsignadoService.create(alumno, dispositivosSelecciondos);
	}
	
	return "redirect:/index";
}
	
	
	//	+++++++++++++++++++++++++++++++++++++++++++++++


@PostMapping("creacionDispositivo")
	public String creacionDispositivo(@ModelAttribute DispositivoDTO dispositivoDTO){
	
//		OffsetDateTime now = OffsetDateTime.now();
//
//		// Configurar los atributos de fecha/hora en el objeto dispositivoDTO
//		dispositivoDTO.setDateCreated(now);
//		dispositivoDTO.setLastUpdated(now);
//
		dispositivoService.create(dispositivoDTO);
		return "index";
}
}
