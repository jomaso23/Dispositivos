package registro.registro_disp2.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import registro.registro_disp2.domain.Role;
import registro.registro_disp2.domain.User;


public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findFirstByUser(User user);

}
