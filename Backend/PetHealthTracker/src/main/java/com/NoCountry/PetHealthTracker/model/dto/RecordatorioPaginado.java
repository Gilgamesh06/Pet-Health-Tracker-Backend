package com.NoCountry.PetHealthTracker.model.dto;

import java.util.List;

public record RecordatorioPaginado(List<RecordatorioResponse>recordatorios,Paginacion paginacion) {
}
