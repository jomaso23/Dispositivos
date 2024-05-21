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
import registro.registro_disp2.model.TokenDTO;
import registro.registro_disp2.service.TokenService;


@RestController
@RequestMapping(value = "/api/tokens", produces = MediaType.APPLICATION_JSON_VALUE)
public class TokenResource {

    private final TokenService tokenService;

    public TokenResource(final TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping
    public ResponseEntity<List<TokenDTO>> getAllTokens() {
        return ResponseEntity.ok(tokenService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TokenDTO> getToken(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(tokenService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createToken(@RequestBody @Valid final TokenDTO tokenDTO) {
        final Long createdId = tokenService.create(tokenDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateToken(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final TokenDTO tokenDTO) {
        tokenService.update(id, tokenDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteToken(@PathVariable(name = "id") final Long id) {
        tokenService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
