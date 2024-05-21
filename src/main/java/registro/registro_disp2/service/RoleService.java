package registro.registro_disp2.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import registro.registro_disp2.domain.Role;
import registro.registro_disp2.domain.User;
import registro.registro_disp2.model.RoleDTO;
import registro.registro_disp2.repos.RoleRepository;
import registro.registro_disp2.repos.UserRepository;
import registro.registro_disp2.util.NotFoundException;


@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleService(final RoleRepository roleRepository, final UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public List<RoleDTO> findAll() {
        final List<Role> roles = roleRepository.findAll(Sort.by("id"));
        return roles.stream()
                .map(role -> mapToDTO(role, new RoleDTO()))
                .toList();
    }

    public RoleDTO get(final Integer id) {
        return roleRepository.findById(id)
                .map(role -> mapToDTO(role, new RoleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final RoleDTO roleDTO) {
        final Role role = new Role();
        mapToEntity(roleDTO, role);
        return roleRepository.save(role).getId();
    }

    public void update(final Integer id, final RoleDTO roleDTO) {
        final Role role = roleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(roleDTO, role);
        roleRepository.save(role);
    }

    public void delete(final Integer id) {
        roleRepository.deleteById(id);
    }

    private RoleDTO mapToDTO(final Role role, final RoleDTO roleDTO) {
        roleDTO.setId(role.getId());
        roleDTO.setRole(role.getRole());
        roleDTO.setGrantedDate(role.getGrantedDate());
        roleDTO.setUser(role.getUser() == null ? null : role.getUser().getId());
        return roleDTO;
    }

    private Role mapToEntity(final RoleDTO roleDTO, final Role role) {
        role.setRole(roleDTO.getRole());
        role.setGrantedDate(roleDTO.getGrantedDate());
        final User user = roleDTO.getUser() == null ? null : userRepository.findById(roleDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        role.setUser(user);
        return role;
    }

}
