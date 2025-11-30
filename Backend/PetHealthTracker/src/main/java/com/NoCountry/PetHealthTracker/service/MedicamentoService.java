package com.NoCountry.PetHealthTracker.service;

import com.NoCountry.PetHealthTracker.exception.medicamento.MedicamentoExistenteException;
import com.NoCountry.PetHealthTracker.exception.medicamento.MedicamentoNotFoundException;
import com.NoCountry.PetHealthTracker.model.dto.*;
import com.NoCountry.PetHealthTracker.model.entity.Medicamento;
import com.NoCountry.PetHealthTracker.model.entity.Usuario;
import com.NoCountry.PetHealthTracker.repository.MedicamentoRepository;
import com.NoCountry.PetHealthTracker.service.interfaces.UpdateProcess;
import com.NoCountry.PetHealthTracker.service.utility.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MedicamentoService  implements UpdateProcess<Medicamento, UpdateMedicamentoDTO> {

    public final MedicamentoRepository medicamentoRepository;
    public final UsuarioService usuarioService;

    public MedicamentoService(MedicamentoRepository medicamentoRepository,
                              UsuarioService usuarioService){
        this.medicamentoRepository = medicamentoRepository;
        this.usuarioService = usuarioService;
    }

    /**
     * Metodo que crea el DTO MedicamentoInfoDTO a partir de un Medicamento
     * @param medicamento
     * @return MedicamentoInfoDTO
     */
    protected MedicamentoInfoDTO createMedicamentoInfo(Medicamento medicamento){
        return MedicamentoInfoDTO.builder()
                .id(medicamento.getId())
                .nombre(medicamento.getNombre())
                .tipo(medicamento.getTipo())
                .fabricante(medicamento.getFabricante())
                .intervaloDosis(medicamento.getIntervaloDosis())
                .descripcion(medicamento.getDescripcion())
                .build();
    }

    /**
     * Metodo que crea un Objeto de tipo Medicamento a partir de Usuario y RegisterMedicamentoDTO
     * @param usuario
     * @param registerMedicamento
     * @return Medicamento
     */
    protected Medicamento createObjectMedicamento(Usuario usuario, RegisterMedicamentoDTO registerMedicamento){
        return Medicamento.builder()
                .nombre(registerMedicamento.getNombre())
                .tipo(registerMedicamento.getTipo())
                .fabricante(registerMedicamento.getFabricante())
                .intervaloDosis(registerMedicamento.getIntervaloDosis())
                .descripcion(registerMedicamento.getDescripcion())
                .usuario(usuario)
                .build();
    }

    /**
     * Metodo que genera una excepcion si el Medicamento ya existe
     * @param nombre
     * @param tipo
     * @param fabricante
     * @param intervaloDosis
     * @param usuario
     */
    protected void findDuplicateMedicamento(String nombre, String tipo, String fabricante, Integer intervaloDosis, Usuario usuario){
        Optional<Medicamento> medicamentoOpt = medicamentoRepository.findByNombreAndTipoAndFabricanteAndIntervaloDosisAndUsuario(
                nombre,
                tipo,
                fabricante,
                intervaloDosis
                ,usuario);
        if(medicamentoOpt.isPresent()){
            throw new MedicamentoExistenteException("El medicamento ya existe.");
        }
    }

    /**
     * Metodo que busca un Medicamento por medio de su id y Usaruario
     * @param id
     * @param usuario
     * @return Medicamento
     */
    protected Medicamento findById(Long id, Usuario usuario){
        Optional<Medicamento> medicamentoOpt = medicamentoRepository.findByIdAndUsuario(id,usuario);
        if(medicamentoOpt.isEmpty()){
            throw new MedicamentoNotFoundException("Medicamento no encontrado");
        }
        else{
            return medicamentoOpt.get();
        }
    }

    /**
     * Medtodo que guarda un medicamento
     * @param registerMedicamento DTO -> Contiene los datos para crear un Medicamento
     * @return MedicamentoInfoDTO
     */
    public MedicamentoInfoDTO saveMedicamento(RegisterMedicamentoDTO registerMedicamento){

        // Obtengo el usuario authenticado
        Usuario user = usuarioService.getUserAuthenticated();

        // Guardo el Medicamento
        Medicamento newMedicamento = createObjectMedicamento(user, registerMedicamento);

        // Valida si el medicamento ya existe
        findDuplicateMedicamento(
                newMedicamento.getNombre(),
                newMedicamento.getTipo(),
                newMedicamento.getFabricante(),
                newMedicamento.getIntervaloDosis(),
                user
        );

        // Guarda el medicamento
        Medicamento medicamento = medicamentoRepository.save(newMedicamento);
        return createMedicamentoInfo(medicamento);
    }

    /**
     * Metodo que verifica si ya existe Medicamento
     * @param medicamento
     * @param updateMedicamento
     */
    protected void isExistMedicamento(Medicamento medicamento, UpdateMedicamentoDTO updateMedicamento){

        if(medicamento.getNombre().equals(updateMedicamento.getNombre()) &&
                medicamento.getTipo().equals(updateMedicamento.getTipo()) &&
                medicamento.getFabricante().equals(updateMedicamento.getFabricante()) &&
                medicamento.getIntervaloDosis().equals(updateMedicamento.getIntervaloDosis())) {

        }
        else{
            // Valida si el medicamento ya existe
            findDuplicateMedicamento(
                    updateMedicamento.getNombre(),
                    updateMedicamento.getTipo(),
                    updateMedicamento.getFabricante(),
                    updateMedicamento.getIntervaloDosis(),
                    medicamento.getUsuario()
            );
        }
    }

    /**
     * Metodo que valida la informacion de UpdateMedicamentoDTO para actualizar Medicamento
     * @param medicamento
     * @param updateMedicamento
     * @return Medicamento
     */
    public Medicamento update(Medicamento medicamento, UpdateMedicamentoDTO updateMedicamento) {
        // verifica si se ha hecho alguna actualización
        isExistMedicamento(medicamento, updateMedicamento);

        boolean updated = false;
        LocalDateTime now = LocalDateTime.now();

        if (StringUtils.isNotEmpty(updateMedicamento.getNombre())) {
            medicamento.setNombre(updateMedicamento.getNombre());
            updated = true;
        }
        if (StringUtils.isNotEmpty(updateMedicamento.getTipo())) {
            medicamento.setTipo(updateMedicamento.getTipo());
            updated = true;
        }
        if (StringUtils.isNotEmpty(updateMedicamento.getFabricante())) {
            medicamento.setFabricante(updateMedicamento.getFabricante());
            updated = true;
        }
        if (StringUtils.isNotEmpty(updateMedicamento.getDescripcion())) {
            medicamento.setDescripcion(updateMedicamento.getDescripcion());
            updated = true;
        }
        if (updateMedicamento.getIntervaloDosis() != null && updateMedicamento.getIntervaloDosis() > 0) {
            medicamento.setIntervaloDosis(updateMedicamento.getIntervaloDosis());
            updated = true;
        }
        if (updated) {
            medicamento.setFechaActualizacion(now); // Establece la fecha de actualización si hay cambios
        }
        return medicamento;
    }

    /**
     * Metodo que guarda el Medicamento actualizado
     * @param id
     * @param updateMedicamento
     * @return MedicamentoInfoDTO
     */
    @Transactional
    public MedicamentoInfoDTO updateMedicamento(Long id, UpdateMedicamentoDTO updateMedicamento){

        // Obtengo el usuario authenticado
        Usuario user = usuarioService.getUserAuthenticated();

        Medicamento medicamento = findById(id,user);

        // Guardo el Medicamento
        Medicamento medicamentosave = medicamentoRepository.save(update(medicamento,updateMedicamento));
        return createMedicamentoInfo(medicamentosave);
    }

    /**
     * Metodo que elimina logica de un Medicamento
     * @param id identificador de Medicamento
     * @return MessageDTO
     */
    @Transactional
    public MessageDTO deleteMedicamento(Long id){

        String estado = "INACTIVO";
        LocalDateTime now = LocalDateTime.now();

        // Obtiene el usuario del contexto
        Usuario user = usuarioService.getUserAuthenticated();

        Medicamento medicamento = findById(id,user);
        medicamento.setEstado(estado);
        medicamento.setFechaEliminacion(now);
        medicamentoRepository.save(medicamento);

        return  MessageDTO.builder()
                .message("Medicamento:" +  medicamento.getNombre()  +"de Tipo: "+ medicamento.getTipo() + " eliminado exitosamente.")
                .build();
    }

    /**
     * Retorna un Pageable dependiendo del orderBy
     * @param page
     * @param size
     * @param orderBy
     * @return Pageable
     */
    protected Pageable getPageable(int page, int size, Boolean orderBy){
        if(orderBy){
            return PageRequest.of(page,size, Sort.by("nombre").ascending());
        }
        else{
            return PageRequest.of(page,size,Sort.by("nombre").descending());
        }
    }

    /**
     * Metodo que retorna todos los medicamentos activos del usuario
     * @param page Pagina a responder
     * @param size Numero de elementos
     * @param orderBy Orden ascendente o desendente ordenado por nombre
     * @return Page<MedicamentoInfoDTO>
     */
    public Page<MedicamentoInfoDTO> getAllMedicamentos(Integer page, Integer size, Boolean orderBy){

        String estado  = "ACTIVO";

        // Obtiene al usuario authenticado
        Usuario user = usuarioService.getUserAuthenticated();

        // Crear un Pageable para manejar numero de paginas, tamaño y orden
        Pageable pageable = getPageable(page,size,orderBy);

        // Obtiene todos los medimamentos registrados por el usuario
        Page<Medicamento> pageMedicamento = medicamentoRepository.findAllByUsuarioAndEstado(user, estado, pageable);

        return pageMedicamento.map(this::createMedicamentoInfo);
    }

    /**
     * Metodo que retorna todos los medicamentos por tipo activos del usuario
     * @param page Pagina a responder
     * @param size Numero de elementos
     * @param orderBy Orden ascendente o desendente ordenado por nombre
     * @param tipo atributo que define si es vacuna o desparasitante
     * @return Page<MedicamentoInfoDTO>
     */
    public Page<MedicamentoInfoDTO> getAllMedicamentosByTipo(Integer page, Integer size, Boolean orderBy, String tipo){

        String estado  = "ACTIVO";

        // Obtiene al usuario authenticado
        Usuario user = usuarioService.getUserAuthenticated();

        // Crear un Pageable para manejar numero de paginas, tamaño y orden
        Pageable pageable = getPageable(page,size,orderBy);

        // Obtiene todos los medimamentos registrados por el usuario
        Page<Medicamento> pageMedicamento = medicamentoRepository.findAllByUsuarioAndEstadoAndTipo(user, estado, tipo, pageable);

        return pageMedicamento.map(this::createMedicamentoInfo);
    }


}
