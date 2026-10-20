package edu.triana.salesianos.culturalia.service;

import edu.triana.salesianos.culturalia.dto.CrearTallerDTO;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class TallerService {

    private final TallerRepository tallerRepository;
    private final OrganizadorRepository organizadorRepository;
    private final ParticipanteRepository participanteRepository;

    public TallerResponseDTO save(CrearTallerDTO dto) {
        if (tallerRepository.existsByTitulo(dto.getTitulo())) {
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

    public List<TallerResponseDTO> findAll() {
        List<Taller> talleres = tallerRepository.findAll();
        if (talleres.isEmpty()) {
            throw new TallerNotFoundException("No se han encontrado talleres registrados");
        }
        return talleres.stream()
                .map(TallerResponseDTO::of)
                .toList();
    }

    public TallerResponseDTO findById(Long id) {
        return tallerRepository.findById(id)
                .map(TallerResponseDTO::of)
                .orElseThrow(() -> new TallerNotFoundException(id));
    }

    public TallerResponseDTO addParticipanteToTaller(Long tallerId, Long participanteId) {
        Taller taller = tallerRepository.findById(tallerId)
                .orElseThrow(() -> new TallerNotFoundException(tallerId));

        Participante participante = participanteRepository.findById(participanteId)
                .orElseThrow(() -> new ParticipanteNotFoundException(participanteId));

        if (taller.getParticipantes().contains(participante)) {
            throw new ParticipanteAlreadyRegisteredException(tallerId, participanteId);
        }

        taller.getParticipantes().add(participante);
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
}