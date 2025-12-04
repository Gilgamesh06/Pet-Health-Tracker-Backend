package com.NoCountry.PetHealthTracker.model.dto;

public record Paginacion (int pagina_actual,
                          long elementos,
                          int paginas,
                          int tamanio_de_pagina) {

}
