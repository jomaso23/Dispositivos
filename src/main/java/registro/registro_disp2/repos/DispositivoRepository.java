package registro.registro_disp2.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import registro.registro_disp2.domain.Dispositivo;
import registro.registro_disp2.model.DispositivoSinEntregarDTO;

import java.util.List;


public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {
	
	@Query("""
        SELECT  d.tipo, d.marca, d.serial, da.entregado, d.id, da.id
        FROM Dispositivo d
        JOIN d.dispositivosAsignados da
        WHERE da.user.id = :id_alumno
        AND da.entregado = false
        """)
	List<Object[]> dispositivoSinEntregar(@Param("id_alumno") int id_alumno);
	
	@Query("""
    SELECT d.id, d.tipo, d.marca, d.serial
    FROM Dispositivo d
    WHERE id NOT IN (
        SELECT da.dispositivo.id
        FROM d.dispositivosAsignados da
        WHERE da.fechaAsignacion = CURRENT_DATE
        AND da.entregado = false
    )
""")
	List<Object[]> obtenerDispositivosNoAsignadosHoy();
	
}
//	SELECT id, tipo, marca, serial
//	FROM Dispositivos d
//	WHERE d.id NOT IN (
//		SELECT da.dispositivo_id
//                FROM dispositivo_asignados da
//		WHERE da.fecha_asignacion = CURRENT_DATE
//)