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
import registro.registro_disp2.model.DispositivoDTO;
import registro.registro_disp2.service.DispositivoService;
import registro.registro_disp2.util.ReferencedException;
import registro.registro_disp2.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/dispositivos", produces = MediaType.APPLICATION_JSON_VALUE)
public class DispositivoResource {

    private final DispositivoService dispositivoService;

    public DispositivoResource(final DispositivoService dispositivoService) {
        this.dispositivoService = dispositivoService;
    }

    @GetMapping
    public ResponseEntity<List<DispositivoDTO>> getAllDispositivos() {
        return ResponseEntity.ok(dispositivoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DispositivoDTO> getDispositivo(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(dispositivoService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDispositivo(
            @RequestBody @Valid final DispositivoDTO dispositivoDTO) {
        final Long createdId = dispositivoService.create(dispositivoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDispositivo(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final DispositivoDTO dispositivoDTO) {
        dispositivoService.update(id, dispositivoDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDispositivo(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = dispositivoService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        dispositivoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
