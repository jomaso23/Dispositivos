package registro.registro_disp2.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import registro.registro_disp2.domain.Token;
import registro.registro_disp2.domain.User;


public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findFirstByUser(User user);

}
