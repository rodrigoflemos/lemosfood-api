package br.com.lemos.lemosfood.domain.service;

import java.util.List;

import br.com.lemos.lemosfood.domain.filter.VendaDiariaFilter;
import br.com.lemos.lemosfood.domain.model.dto.VendaDiaria;

public interface VendaQueryService {

	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
