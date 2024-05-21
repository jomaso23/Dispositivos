package registro.registro_disp2.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import registro.registro_disp2.domain.Dispositivo;
import registro.registro_disp2.domain.DispositivoAsignado;
import registro.registro_disp2.model.DispositivoDTO;
import registro.registro_disp2.model.DispositivoSinEntregarDTO;
import registro.registro_disp2.repos.DispositivoAsignadoRepository;
import registro.registro_disp2.repos.DispositivoRepository;
import registro.registro_disp2.util.NotFoundException;
import registro.registro_disp2.util.ReferencedWarning;


@Service
public class DispositivoService {

    private final DispositivoRepository dispositivoRepository;
    private final DispositivoAsignadoRepository dispositivoAsignadoRepository;

    public DispositivoService(final DispositivoRepository dispositivoRepository,
            final DispositivoAsignadoRepository dispositivoAsignadoRepository) {
        this.dispositivoRepository = dispositivoRepository;
        this.dispositivoAsignadoRepository = dispositivoAsignadoRepository;
    }
    
//    NUEVO METODO me llama dispositivos que no se han prestado, doble condicion:
//    entregado : FALSE(no se llama dispositivo) ; fecha: actual(del dia)

    public List<DispositivoDTO> findAll() {
        final List<Dispositivo> dispositivoes = dispositivoRepository.findAll(Sort.by("id"));
        return dispositivoes.stream()
                .map(dispositivo -> mapToDTO(dispositivo, new DispositivoDTO()))
                .toList();
    }

    public DispositivoDTO get(final Long id) {
        return dispositivoRepository.findById(id)
                .map(dispositivo -> mapToDTO(dispositivo, new DispositivoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DispositivoDTO dispositivoDTO) {
        final Dispositivo dispositivo = new Dispositivo();
        mapToEntity(dispositivoDTO, dispositivo);
        return dispositivoRepository.save(dispositivo).getId();
    }

    public void update(final Long id, final DispositivoDTO dispositivoDTO) {
        final Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(dispositivoDTO, dispositivo);
        dispositivoRepository.save(dispositivo);
    }

    public void delete(final Long id) {
        dispositivoRepository.deleteById(id);
    }

    
    //usado en consulta-alumno!!!!!
    public List<DispositivoSinEntregarDTO> dispositivoSinEntregarDTOList(String alumnoId){
        
        List<Object[]> resultados = dispositivoRepository.dispositivoSinEntregar(Integer.parseInt(alumnoId));
        List<DispositivoSinEntregarDTO> dispositivoSinEntregarDTOList= new ArrayList<>();
        for (Object[] resultado: resultados) {
            DispositivoSinEntregarDTO dispositivoSinEntregarDTO= new DispositivoSinEntregarDTO();
            dispositivoSinEntregarDTO.setTipo((String) resultado[0]);
            dispositivoSinEntregarDTO.setMarca((String) resultado[1]);
            dispositivoSinEntregarDTO.setSerial((String) resultado[2]);
            dispositivoSinEntregarDTO.setEntregado((Boolean) resultado[3]);
            dispositivoSinEntregarDTO.setId(Math.toIntExact((Long) resultado[4]));
            dispositivoSinEntregarDTO.setIdEntrega((Integer) resultado[5]);
            dispositivoSinEntregarDTOList.add(dispositivoSinEntregarDTO);
            
        }
        //        dispositivoRepository.dispositivoSinEntregar(Integer.parseInt(alumnoId));
       return dispositivoSinEntregarDTOList;
    }
    public List<DispositivoDTO> obtenerDispositivosNoAsignadosHoy() {
        
        List<Object[]> resultados = dispositivoRepository.obtenerDispositivosNoAsignadosHoy();
        List<DispositivoDTO> dispositivoDTOList= new ArrayList<>();
//        System.out.println(resultados.get(0));
        for (Object[] resultado: resultados) {
            DispositivoDTO dispositivoDTO = new DispositivoDTO();
//            System.out.println(resultado[0]);
            dispositivoDTO.setId((Long )resultado[0]);
            dispositivoDTO.setTipo((String ) resultado[1]);
            dispositivoDTO.setMarca((String ) resultado[2]);
            dispositivoDTO.setTipo((String ) resultado[3]);
            dispositivoDTOList.add(dispositivoDTO);
        }
     
        return dispositivoDTOList;
    }
    private DispositivoDTO mapToDTO(final Dispositivo dispositivo,
            final DispositivoDTO dispositivoDTO) {
        dispositivoDTO.setId(dispositivo.getId());
        dispositivoDTO.setTipo(dispositivo.getTipo());
        dispositivoDTO.setMarca(dispositivo.getMarca());
        dispositivoDTO.setSerial(dispositivo.getSerial());
        return dispositivoDTO;
    }

    private Dispositivo mapToEntity(final DispositivoDTO dispositivoDTO,
            final Dispositivo dispositivo) {
        dispositivo.setTipo(dispositivoDTO.getTipo());
        dispositivo.setMarca(dispositivoDTO.getMarca());
        dispositivo.setSerial(dispositivoDTO.getSerial());
        return dispositivo;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final DispositivoAsignado dispositivoDispositivoAsignado = dispositivoAsignadoRepository.findFirstByDispositivo(dispositivo);
        if (dispositivoDispositivoAsignado != null) {
            referencedWarning.setKey("dispositivo.dispositivoAsignado.dispositivo.referenced");
            referencedWarning.addParam(dispositivoDispositivoAsignado.getId());
            return referencedWarning;
        }
        return null;
    }

}
