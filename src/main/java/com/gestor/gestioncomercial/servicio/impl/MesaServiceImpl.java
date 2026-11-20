package com.gestor.gestioncomercial.servicio.impl;

import com.gestor.gestioncomercial.dto.MesaRequestDTO;
import com.gestor.gestioncomercial.dto.MesaResponseDTO;
import com.gestor.gestioncomercial.entidad.Mesa;
import com.gestor.gestioncomercial.repositorio.MesaRepository;
import com.gestor.gestioncomercial.servicio.MesaService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MesaServiceImpl implements MesaService {

    private final MesaRepository mesaRepository;

    public MesaServiceImpl(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    @Override
    public List<MesaResponseDTO> listarMesas() {
        return mesaRepository.findAll()
                .stream()
                .map(this::mapearAResponse)
                .toList();
    }

    @Override
    public MesaResponseDTO obtenerPorId(Long idMesa) {
        Mesa mesa = mesaRepository.findById(idMesa)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con id: " + idMesa));
        return mapearAResponse(mesa);
    }

    @Override
    public MesaResponseDTO crearMesa(MesaRequestDTO request) {
        Mesa mesa = new Mesa();
        mesa.setNumero(request.getNumero());
        mesa.setCapacidad(request.getCapacidad() != null ? request.getCapacidad() : 4);
        mesa.setEstadoMesa(request.getEstadoMesa() != null ? request.getEstadoMesa() : "LIBRE");
        mesa.setEstado(true);

        Mesa guardada = mesaRepository.save(mesa);
        return mapearAResponse(guardada);
    }

    @Override
    public MesaResponseDTO cambiarEstadoMesa(Long idMesa, String estadoMesa) {
        Mesa mesa = mesaRepository.findById(idMesa)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con id: " + idMesa));
        mesa.setEstadoMesa(estadoMesa);
        return mapearAResponse(mesaRepository.save(mesa));
    }

    private MesaResponseDTO mapearAResponse(Mesa mesa) {
        MesaResponseDTO dto = new MesaResponseDTO();
        dto.setIdMesa(mesa.getIdMesa());
        dto.setNumero(mesa.getNumero());
        dto.setCapacidad(mesa.getCapacidad());
        dto.setEstadoMesa(mesa.getEstadoMesa());
        dto.setEstado(mesa.getEstado());
        return dto;
    }
}