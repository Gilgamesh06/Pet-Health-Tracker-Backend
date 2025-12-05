package com.NoCountry.PetHealthTracker.model.dto;

import java.util.List;

public record EventoSaludPaginacion(List<EventoSaludResponse> eventosSalud, Paginacion paginacion) {
}
