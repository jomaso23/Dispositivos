package registro.registro_disp2.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import registro.registro_disp2.domain.DispositivoAsignado;
import registro.registro_disp2.domain.Role;
import registro.registro_disp2.domain.Token;
import registro.registro_disp2.domain.User;
import registro.registro_disp2.model.UserDTO;
import registro.registro_disp2.repos.DispositivoAsignadoRepository;
import registro.registro_disp2.repos.RoleRepository;
import registro.registro_disp2.repos.TokenRepository;
import registro.registro_disp2.repos.UserRepository;
import registro.registro_disp2.util.NotFoundException;
import registro.registro_disp2.util.ReferencedWarning;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final DispositivoAsignadoRepository dispositivoAsignadoRepository;

    public UserService(final UserRepository userRepository, final TokenRepository tokenRepository,
            final RoleRepository roleRepository,
            final DispositivoAsignadoRepository dispositivoAsignadoRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.dispositivoAsignadoRepository = dispositivoAsignadoRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }
//metodo mofidicado de Integer id a String id
    public UserDTO get(String idAlumno) {
        int id = Integer.parseInt(idAlumno);
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Integer id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Integer id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setApellido(user.getApellido());
        userDTO.setDocumento(user.getDocumento());
        userDTO.setUserName(user.getUserName());
        userDTO.setPasswordHash(user.getPasswordHash());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setName(userDTO.getName());
        user.setApellido(userDTO.getApellido());
        user.setDocumento(userDTO.getDocumento());
        user.setUserName(userDTO.getUserName());
        user.setPasswordHash(userDTO.getPasswordHash());
        return user;
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Token userToken = tokenRepository.findFirstByUser(user);
        if (userToken != null) {
            referencedWarning.setKey("user.token.user.referenced");
            referencedWarning.addParam(userToken.getId());
            return referencedWarning;
        }
        final Role userRole = roleRepository.findFirstByUser(user);
        if (userRole != null) {
            referencedWarning.setKey("user.role.user.referenced");
            referencedWarning.addParam(userRole.getId());
            return referencedWarning;
        }
        final DispositivoAsignado userDispositivoAsignado = dispositivoAsignadoRepository.findFirstByUser(user);
        if (userDispositivoAsignado != null) {
            referencedWarning.setKey("user.dispositivoAsignado.user.referenced");
            referencedWarning.addParam(userDispositivoAsignado.getId());
            return referencedWarning;
        }
        return null;
    }

}
