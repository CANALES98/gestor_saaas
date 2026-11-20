package com.gestor.gestioncomercial.servicio;

import com.gestor.gestioncomercial.dto.MesaRequestDTO;
import com.gestor.gestioncomercial.dto.MesaResponseDTO;
import java.util.List;

public interface MesaService {
    List<MesaResponseDTO> listarMesas();
    MesaResponseDTO obtenerPorId(Long idMesa);
    MesaResponseDTO crearMesa(MesaRequestDTO request);
    MesaResponseDTO cambiarEstadoMesa(Long idMesa, String estadoMesa);
}