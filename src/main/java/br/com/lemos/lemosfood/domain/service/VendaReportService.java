package br.com.lemos.lemosfood.domain.service;

import br.com.lemos.lemosfood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {
	
	byte[] emitirVendasDiarias (VendaDiariaFilter filtro, String timeOffset);
}
