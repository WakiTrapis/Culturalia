package edu.triana.salesianos.culturalia.utils;

import edu.triana.salesianos.culturalia.model.Organizador;
import edu.triana.salesianos.culturalia.model.Participante;
import edu.triana.salesianos.culturalia.model.Taller;
import edu.triana.salesianos.culturalia.repository.OrganizadorRepository;
import edu.triana.salesianos.culturalia.repository.ParticipanteRepository;
import edu.triana.salesianos.culturalia.repository.TallerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeed {

    private final OrganizadorRepository organizadorRepository;
    private final TallerRepository tallerRepository;
    private final ParticipanteRepository participanteRepository;

    @PostConstruct
    public void initData() {

        Organizador org1 = Organizador.builder()
                .nombre("Asociación Cultural Triana")
                .email("contacto@culturatriana.org")
                .build();

        Organizador org2 = Organizador.builder()
                .nombre("Ateneo Artístico de Sevilla")
                .email("info@ateneoartistico.es")
                .build();

        organizadorRepository.saveAll(List.of(org1, org2));

        Participante part1 = Participante.builder()
                .nombreCompleto("María García López")
                .build();

        Participante part2 = Participante.builder()
                .nombreCompleto("Alejandro Rodríguez Silva")
                .build();

        Participante part3 = Participante.builder()
                .nombreCompleto("Carmen Benítez Morales")
                .build();

        participanteRepository.saveAll(List.of(part1, part2, part3));

        Taller tallerPintura = Taller.builder()
                .titulo("Taller de Pintura al Óleo")
                .fechaHoraInicio(String.valueOf(LocalDateTime.of(2026, 11, 10, 10, 0)))
                .fechaHoraFin(String.valueOf(LocalDateTime.of(2026, 11, 10, 13, 0)))
                .organizador(org1)
                .build();

        tallerPintura.getParticipantes().add(part1);
        tallerPintura.getParticipantes().add(part2);

        Taller tallerFotografia = Taller.builder()
                .titulo("Iniciación a la Fotografía Digital")
                .fechaHoraInicio(String.valueOf(LocalDateTime.of(2026, 11, 15, 17, 30)))
                .fechaHoraFin(String.valueOf(LocalDateTime.of(2026, 11, 15, 20, 0)))
                .organizador(org2)
                .build();

        tallerFotografia.getParticipantes().add(part3);

        Taller tallerTeatro = Taller.builder()
                .titulo("Taller de Improvisación Teatral")
                .fechaHoraInicio(String.valueOf(LocalDateTime.of(2026, 12, 1, 18, 0)))
                .fechaHoraFin(String.valueOf(LocalDateTime.of(2026, 12, 1, 21, 0)))
                .organizador(org1)
                .build();

        tallerRepository.saveAll(List.of(tallerPintura, tallerFotografia, tallerTeatro));
    }
}