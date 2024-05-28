package registro.registro_disp2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import registro.registro_disp2.model.DispositivoDTO;
import registro.registro_disp2.service.DispositivoService;
import registro.registro_disp2.service.UserService;

import java.util.List;


@Controller
public class HomeController {
    
  
    private  DispositivoService dispositivoService;
    String mensajeAsignacion = "seleccione un dispositivo para Asignar";
    public HomeController( DispositivoService dispositivoService) {
        
        this.dispositivoService = dispositivoService;
    }
    
    @GetMapping("/")
    public String landing(Model model){
        return "landing";
    }
    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }
   
    
    @GetMapping("/consulta")
    public String consulta (Model model){
        
        return "consultaAlumno";
    }
    @GetMapping("/explicacion")
    public String explicacion(Model model){
        return "explicacion";
    }

}
