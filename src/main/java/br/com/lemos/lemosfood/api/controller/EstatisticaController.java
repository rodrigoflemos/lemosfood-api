package br.com.lemos.lemosfood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lemos.lemosfood.domain.filter.VendaDiariaFilter;
import br.com.lemos.lemosfood.domain.model.dto.VendaDiaria;
import br.com.lemos.lemosfood.domain.service.VendaQueryService;
import br.com.lemos.lemosfood.domain.service.VendaReportService;

@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticaController {

	@Autowired
	VendaQueryService vendaQueryService;
	
	@Autowired
	VendaReportService vendaReportService;
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria>  consultarVendasDiarias(VendaDiariaFilter filtro, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
	}
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		
		byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffset);
		
		var headers = new HttpHeaders();
		//Serve para definir que um pdf nao pode ser aberto no browser
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}
}