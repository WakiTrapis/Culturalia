package edu.triana.salesianos.culturalia.service;

import edu.triana.salesianos.culturalia.dto.CrearOrganizadorDTO;
import edu.triana.salesianos.culturalia.dto.OrganizadorResponseDTO;
import edu.triana.salesianos.culturalia.error.OrganizadorNotFoundException;
import edu.triana.salesianos.culturalia.model.Organizador;
import edu.triana.salesianos.culturalia.repository.OrganizadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizadorService {

    private final OrganizadorRepository organizadorRepository;

    public OrganizadorResponseDTO save(CrearOrganizadorDTO dto) {
        Organizador nuevo = Organizador.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .build();

        Organizador guardado = organizadorRepository.save(nuevo);
        return OrganizadorResponseDTO.of(guardado);
    }

    public List<OrganizadorResponseDTO> findAll() {
        List<Organizador> result = organizadorRepository.findAll();

        if (result.isEmpty()) {
            throw new OrganizadorNotFoundException("No se han encontrado organizadores");
        }

        return result.stream()
                .map(OrganizadorResponseDTO::of)
                .toList();
    }
}
