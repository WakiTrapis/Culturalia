package edu.triana.salesianos.culturalia.service;

import edu.triana.salesianos.culturalia.dto.CrearParticipanteDTO;
import edu.triana.salesianos.culturalia.dto.ParticipanteResponseDTO;
import edu.triana.salesianos.culturalia.error.*;
import edu.triana.salesianos.culturalia.error.notFound.ParticipanteNotFoundException;
import edu.triana.salesianos.culturalia.model.Participante;
import edu.triana.salesianos.culturalia.repository.ParticipanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipanteService {

    private final ParticipanteRepository participanteRepository;

    public ParticipanteResponseDTO save(CrearParticipanteDTO dto) {
        if (participanteRepository.existsByNombreCompleto(dto.getNombreCompleto())) {
            throw new TallerWasFoundException("Ya existe un Participante registrado con el nombre: " + dto.getNombreCompleto());
        }


        Participante nuevo = Participante.builder()
                .nombreCompleto(dto.getNombreCompleto())
                .build();

        return ParticipanteResponseDTO.of(participanteRepository.save(nuevo));
    }

    public List<ParticipanteResponseDTO> findAll() {
        List<Participante> lista = participanteRepository.findAll();
        if (lista.isEmpty()) {
            throw new ParticipanteNotFoundException("No existen participantes registrados");
        }
        return lista.stream()
                .map(ParticipanteResponseDTO::of)
                .toList();
    }

    public ParticipanteResponseDTO findById(Long id) {
        return participanteRepository.findById(id)
                .map(ParticipanteResponseDTO::of)
                .orElseThrow(() -> new ParticipanteNotFoundException(id));
    }
}