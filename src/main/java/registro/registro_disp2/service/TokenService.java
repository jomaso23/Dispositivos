package registro.registro_disp2.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import registro.registro_disp2.domain.Token;
import registro.registro_disp2.domain.User;
import registro.registro_disp2.model.TokenDTO;
import registro.registro_disp2.repos.TokenRepository;
import registro.registro_disp2.repos.UserRepository;
import registro.registro_disp2.util.NotFoundException;


@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public TokenService(final TokenRepository tokenRepository,
            final UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    public List<TokenDTO> findAll() {
        final List<Token> tokens = tokenRepository.findAll(Sort.by("id"));
        return tokens.stream()
                .map(token -> mapToDTO(token, new TokenDTO()))
                .toList();
    }

    public TokenDTO get(final Long id) {
        return tokenRepository.findById(id)
                .map(token -> mapToDTO(token, new TokenDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TokenDTO tokenDTO) {
        final Token token = new Token();
        mapToEntity(tokenDTO, token);
        return tokenRepository.save(token).getId();
    }

    public void update(final Long id, final TokenDTO tokenDTO) {
        final Token token = tokenRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(tokenDTO, token);
        tokenRepository.save(token);
    }

    public void delete(final Long id) {
        tokenRepository.deleteById(id);
    }

    private TokenDTO mapToDTO(final Token token, final TokenDTO tokenDTO) {
        tokenDTO.setId(token.getId());
        tokenDTO.setToken(token.getToken());
        tokenDTO.setIsLogged(token.getIsLogged());
        tokenDTO.setUser(token.getUser() == null ? null : token.getUser().getId());
        return tokenDTO;
    }

    private Token mapToEntity(final TokenDTO tokenDTO, final Token token) {
        token.setToken(tokenDTO.getToken());
        token.setIsLogged(tokenDTO.getIsLogged());
        final User user = tokenDTO.getUser() == null ? null : userRepository.findById(tokenDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        token.setUser(user);
        return token;
    }

}
