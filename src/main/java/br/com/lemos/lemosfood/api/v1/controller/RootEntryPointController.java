package br.com.lemos.lemosfood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lemos.lemosfood.api.v1.LemosLinks;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

	@Autowired
	LemosLinks lemosLinks;
	
	@GetMapping
	public RootEntryPointModel root() {
		var rootEntryPointModel = new RootEntryPointModel();

		rootEntryPointModel.add(lemosLinks.linkToCozinhas("cozinhas"));
		rootEntryPointModel.add(lemosLinks.linkToPedidos("pedidos"));
		rootEntryPointModel.add(lemosLinks.linkToRestaurantes("restaurantes"));
		rootEntryPointModel.add(lemosLinks.linkToGrupos("grupos"));
		rootEntryPointModel.add(lemosLinks.linkToUsuarios("usuarios"));
		rootEntryPointModel.add(lemosLinks.linkToPermissoes("permissoes"));
		rootEntryPointModel.add(lemosLinks.linkToFormasPagamento("formas-pagamento"));
		rootEntryPointModel.add(lemosLinks.linkToEstados("estados"));
		rootEntryPointModel.add(lemosLinks.linkToCidades("cidades"));
		rootEntryPointModel.add(lemosLinks.linkToEstatisticas("estatisticas"));
		
		return rootEntryPointModel;
	}
	
	
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
		
	}
}
