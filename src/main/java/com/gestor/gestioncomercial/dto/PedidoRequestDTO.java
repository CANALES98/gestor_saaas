package com.gestor.gestioncomercial.dto;

import java.util.List;

public class PedidoRequestDTO {
    private Long idMesa;
    private List<PedidoItemRequestDTO> items;

    public Long getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Long idMesa) {
        this.idMesa = idMesa;
    }

    public List<PedidoItemRequestDTO> getItems() {
        return items;
    }

    public void setItems(List<PedidoItemRequestDTO> items) {
        this.items = items;
    }
}