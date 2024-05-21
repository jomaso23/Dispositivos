package registro.registro_disp2.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import registro.registro_disp2.model.DispositivoAsignadoDTO;
import registro.registro_disp2.service.DispositivoAsignadoService;


@RestController
@RequestMapping(value = "/api/dispositivoAsignados", produces = MediaType.APPLICATION_JSON_VALUE)
public class DispositivoAsignadoResource {

    private final DispositivoAsignadoService dispositivoAsignadoService;

    public DispositivoAsignadoResource(
            final DispositivoAsignadoService dispositivoAsignadoService) {
        this.dispositivoAsignadoService = dispositivoAsignadoService;
    }

    @GetMapping
    public ResponseEntity<List<DispositivoAsignadoDTO>> getAllDispositivoAsignados() {
        return ResponseEntity.ok(dispositivoAsignadoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DispositivoAsignadoDTO> getDispositivoAsignado(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(dispositivoAsignadoService.get(id));
    }

      //+++++++CORREJIR RESTE METODO PARA CRAER UN DISPOSITIVO ASIGNADO!!!++++++++
//    @PostMapping
//    @ApiResponse(responseCode = "201")
//    public ResponseEntity<Integer> createDispositivoAsignado(
//            @RequestBody @Valid final DispositivoAsignadoDTO dispositivoAsignadoDTO) {
//        final Integer createdId = dispositivoAsignadoService.create(dispositivoAsignadoDTO);
//        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
//    }



//    @PutMapping("/{id}")
//    public ResponseEntity<Integer> updateDispositivoAsignado(
//            @PathVariable(name = "id") final Integer id,
//            @RequestBody @Valid final DispositivoAsignadoDTO dispositivoAsignadoDTO) {
//        dispositivoAsignadoService.update(id, dispositivoAsignadoDTO);
//        return ResponseEntity.ok(id);
//    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDispositivoAsignado(
            @PathVariable(name = "id") final Integer id) {
        dispositivoAsignadoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
