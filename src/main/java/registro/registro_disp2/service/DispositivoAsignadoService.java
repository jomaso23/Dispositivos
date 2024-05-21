package registro.registro_disp2.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import registro.registro_disp2.domain.Dispositivo;
import registro.registro_disp2.domain.DispositivoAsignado;
import registro.registro_disp2.domain.User;
import registro.registro_disp2.model.DispositivoAsignadoDTO;
import registro.registro_disp2.model.DispositivoSinEntregarDTO;
import registro.registro_disp2.repos.DispositivoAsignadoRepository;
import registro.registro_disp2.repos.DispositivoRepository;
import registro.registro_disp2.repos.UserRepository;
import registro.registro_disp2.util.NotFoundException;


@Service
public class DispositivoAsignadoService {

    private final DispositivoAsignadoRepository dispositivoAsignadoRepository;
    private final UserRepository userRepository;
    private final DispositivoRepository dispositivoRepository;

    public DispositivoAsignadoService(
            final DispositivoAsignadoRepository dispositivoAsignadoRepository,
            final UserRepository userRepository,
            final DispositivoRepository dispositivoRepository) {
        this.dispositivoAsignadoRepository = dispositivoAsignadoRepository;
        this.userRepository = userRepository;
        this.dispositivoRepository = dispositivoRepository;
    }

    public List<DispositivoAsignadoDTO> findAll() {
        final List<DispositivoAsignado> dispositivoAsignadoes = dispositivoAsignadoRepository.findAll(Sort.by("id"));
        return dispositivoAsignadoes.stream()
                .map(dispositivoAsignado -> mapToDTO(dispositivoAsignado, new DispositivoAsignadoDTO()))
                .toList();
    }

    public DispositivoAsignadoDTO get(final Integer id) {
        return dispositivoAsignadoRepository.findById(id)
                .map(dispositivoAsignado -> mapToDTO(dispositivoAsignado, new DispositivoAsignadoDTO()))
                .orElseThrow(NotFoundException::new);
    }
    
    
//Aqui creo un dispositivo asignado, llama a MapToEntity
    public Integer create(String alumnoID, List<String>  disposIDs) {
        final DispositivoAsignado dispositivoAsignado = new DispositivoAsignado();
        
        for (String dispoID: disposIDs
             ) {
            dispositivoAsignadoRepository.save( mapToEntity(alumnoID, dispoID));
        }
        
        
        return null;
    }

    


    public void delete(final Integer id) {
        dispositivoAsignadoRepository.deleteById(id);
    }

    private DispositivoAsignadoDTO mapToDTO(final DispositivoAsignado dispositivoAsignado,
            final DispositivoAsignadoDTO dispositivoAsignadoDTO) {
        dispositivoAsignadoDTO.setId(dispositivoAsignado.getId());
        dispositivoAsignadoDTO.setFechaAsignacion(dispositivoAsignado.getFechaAsignacion().atStartOfDay());
        dispositivoAsignadoDTO.setUser(dispositivoAsignado.getUser() == null ? null : dispositivoAsignado.getUser().getId());
        dispositivoAsignadoDTO.setDispositivo(dispositivoAsignado.getDispositivo() == null ? null : dispositivoAsignado.getDispositivo().getId());
        return dispositivoAsignadoDTO;
    }

    private DispositivoAsignado mapToEntity(String alumnoID, String dispoID
            ) {
        DispositivoAsignado dispositivoAsignado = new DispositivoAsignado();
        dispositivoAsignado.setFechaAsignacion(LocalDate.now());//!!!!AQUI
        
        final User user = userRepository.findById(Integer.valueOf(alumnoID))  == null ? null : userRepository.findById(Integer.valueOf(alumnoID))
                .orElseThrow(() -> new NotFoundException("user not found"));
        dispositivoAsignado.setUser(user);
        
        final Dispositivo dispositivo = dispositivoRepository.findById(Long.valueOf(dispoID)) == null ? null : dispositivoRepository.findById(Long.valueOf(dispoID))
                .orElseThrow(() -> new NotFoundException("dispositivo not found"));
        dispositivoAsignado.setDispositivo(dispositivo);
        return dispositivoAsignado;
    }
    
    
        //metodo de que actualiza el estado de entregado de false ---> true
        @Transactional
        public void  actualizarEstadoEntrega(List<String> dispositivosSeleccionadosSinEntregar){
            
           
            for (String asignacionId: dispositivosSeleccionadosSinEntregar) {
                DispositivoAsignado dispositivoAsignado = new DispositivoAsignado();
                dispositivoAsignado=  dispositivoAsignadoRepository.getReferenceById(Integer.parseInt(asignacionId));
                dispositivoAsignado.setEntregado(true);
                dispositivoAsignadoRepository.save(dispositivoAsignado);
            }
            
        
        }

}
