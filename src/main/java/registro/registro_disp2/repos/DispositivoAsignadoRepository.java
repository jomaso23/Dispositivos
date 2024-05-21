package registro.registro_disp2.repos;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import registro.registro_disp2.domain.Dispositivo;
import registro.registro_disp2.domain.DispositivoAsignado;
import registro.registro_disp2.domain.User;

import java.util.List;


public interface DispositivoAsignadoRepository extends JpaRepository<DispositivoAsignado, Integer> {

    DispositivoAsignado findFirstByUser(User user);

    DispositivoAsignado findFirstByDispositivo(Dispositivo dispositivo);
    

}
