package edu.triana.salesianos.culturalia.service;

import edu.triana.salesianos.culturalia.dto.CrearTallerDTO;
import edu.triana.salesianos.culturalia.dto.ParticipanteResponseDTO;
import edu.triana.salesianos.culturalia.dto.TallerResponseDTO;
import edu.triana.salesianos.culturalia.error.*;
import edu.triana.salesianos.culturalia.error.notFound.OrganizadorNotFoundException;
import edu.triana.salesianos.culturalia.error.notFound.ParticipanteNotFoundException;
import edu.triana.salesianos.culturalia.error.notFound.TallerNotFoundException;
import edu.triana.salesianos.culturalia.model.Organizador;
import edu.triana.salesianos.culturalia.model.Participante;
import edu.triana.salesianos.culturalia.model.Taller;
import edu.triana.salesianos.culturalia.repository.OrganizadorRepository;
import edu.triana.salesianos.culturalia.repository.ParticipanteRepository;
import edu.triana.salesianos.culturalia.repository.TallerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TallerService {

    private final TallerRepository tallerRepository;
    private final OrganizadorRepository organizadorRepository;
    private final ParticipanteRepository participanteRepository;

    public TallerResponseDTO save(CrearTallerDTO dto) {
        if (tallerRepository.existsByTituloIgnoreCase(dto.getTitulo())) {
            throw new TallerWasFoundException("Ya existe un taller registrado con el título: " + dto.getTitulo());
        }

        Organizador organizador = organizadorRepository.findById(dto.getOrganizadorId())
                .orElseThrow(() -> new OrganizadorNotFoundException("No se encontró el organizador con ID: " + dto.getOrganizadorId()));

        Taller nuevoTaller = Taller.builder()
                .titulo(dto.getTitulo())
                .fechaHoraInicio(String.valueOf(dto.getFechaHoraInicio()))
                .fechaHoraFin(String.valueOf(dto.getFechaHoraFin()))
                .organizador(organizador)
                .build();

        return TallerResponseDTO.of(tallerRepository.save(nuevoTaller));
    }

    @Transactional(readOnly = true)
    public List<TallerResponseDTO> findAll() {
        return tallerRepository.findAllWithAssociations().stream()
                .map(TallerResponseDTO::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public TallerResponseDTO findById(Long id) {
        return tallerRepository.findByIdWithAssociations(id)
                .map(TallerResponseDTO::of)
                .orElseThrow(() -> new TallerNotFoundException(id));
    }

    public TallerResponseDTO addParticipanteToTaller(Long tallerId, Long participanteId) {
        Taller taller = tallerRepository.findByIdWithAssociations(tallerId)
                .orElseThrow(() -> new TallerNotFoundException(tallerId));

        Participante participante = participanteRepository.findById(participanteId)
                .orElseThrow(() -> new ParticipanteNotFoundException(participanteId));

        if (taller.getParticipantes() == null) {
            taller.setParticipantes(new HashSet<>());
        }

        if (taller.getParticipantes().contains(participante)) {
            throw new ParticipanteAlreadyRegisteredException(participanteId, tallerId);
        }

        taller.getParticipantes().add(participante);
        participante.getTalleres().add(taller);

        return TallerResponseDTO.of(tallerRepository.save(taller));
    }

    public void removeParticipanteFromTaller(Long tallerId, Long participanteId) {
        Taller taller = tallerRepository.findById(tallerId)
                .orElseThrow(() -> new TallerNotFoundException(tallerId));

        Participante participante = participanteRepository.findById(participanteId)
                .orElseThrow(() -> new ParticipanteNotFoundException(participanteId));

        if (taller.getParticipantes().contains(participante)) {
            taller.getParticipantes().remove(participante);
            tallerRepository.save(taller);
        }
    }

    public List<ParticipanteResponseDTO> getParticipantesByTallerId(Long tallerId){
        Taller taller = tallerRepository.findById(tallerId)
                .orElseThrow(() -> new TallerNotFoundException(tallerId));
        return taller.getParticipantes().stream()
                .map(ParticipanteResponseDTO::of)
                .toList();
    }

    @Transactional
    public TallerResponseDTO edit(Long id, CrearTallerDTO dto) {
        Taller taller = tallerRepository.findByIdWithAssociations(id)
                .orElseThrow(() -> new TallerNotFoundException(id));

        String tituloLimpio = dto.getTitulo().trim();

        if (!taller.getTitulo().equalsIgnoreCase(tituloLimpio) &&
                tallerRepository.existsByTituloIgnoreCase(tituloLimpio)) {
            throw new TallerWasFoundException(tituloLimpio);
        }

        taller.setTitulo(tituloLimpio);
        taller.setFechaHoraInicio(String.valueOf(dto.getFechaHoraInicio()));
        taller.setFechaHoraFin(String.valueOf(dto.getFechaHoraFin()));

        if (dto.getOrganizadorId() != null) {
            Organizador organizador = organizadorRepository.findById(dto.getOrganizadorId())
                    .orElseThrow(() -> new OrganizadorNotFoundException(dto.getOrganizadorId()));
            taller.setOrganizador(organizador);
        } else {
            taller.setOrganizador(null);
        }

        return TallerResponseDTO.of(tallerRepository.save(taller));
    }

    @Transactional
    public void delete(Long id) {
        if (!tallerRepository.existsById(id)) {
            throw new TallerNotFoundException(id);
        }
        tallerRepository.deleteById(id);
    }

}