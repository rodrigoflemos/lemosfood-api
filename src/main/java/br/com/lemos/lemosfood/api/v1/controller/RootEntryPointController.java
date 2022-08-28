package br.com.lemos.lemosfood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lemos.lemosfood.api.v1.LemosLinks;
import br.com.lemos.lemosfood.core.security.LemosSecurity;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

	@Autowired
	LemosLinks lemosLinks;
	
    @Autowired
    private LemosSecurity lemosSecurity;
	
    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();
        
        if (lemosSecurity.podeConsultarCozinhas()) {
            rootEntryPointModel.add(lemosLinks.linkToCozinhas("cozinhas"));
        }
        
        if (lemosSecurity.podePesquisarPedidos()) {
            rootEntryPointModel.add(lemosLinks.linkToPedidos("pedidos"));
        }
        
        if (lemosSecurity.podeConsultarRestaurantes()) {
            rootEntryPointModel.add(lemosLinks.linkToRestaurantes("restaurantes"));
        }
        
        if (lemosSecurity.podeConsultarUsuariosGruposPermissoes()) {
            rootEntryPointModel.add(lemosLinks.linkToGrupos("grupos"));
            rootEntryPointModel.add(lemosLinks.linkToUsuarios("usuarios"));
            rootEntryPointModel.add(lemosLinks.linkToPermissoes("permissoes"));
        }
        
        if (lemosSecurity.podeConsultarFormasPagamento()) {
            rootEntryPointModel.add(lemosLinks.linkToFormasPagamento("formas-pagamento"));
        }
        
        if (lemosSecurity.podeConsultarEstados()) {
            rootEntryPointModel.add(lemosLinks.linkToEstados("estados"));
        }
        
        if (lemosSecurity.podeConsultarCidades()) {
            rootEntryPointModel.add(lemosLinks.linkToCidades("cidades"));
        }
        
        if (lemosSecurity.podeConsultarEstatisticas()) {
            rootEntryPointModel.add(lemosLinks.linkToEstatisticas("estatisticas"));
        }
        
        return rootEntryPointModel;
    }
	
	
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
		
	}
}
