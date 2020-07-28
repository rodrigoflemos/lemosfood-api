package br.com.lemos.lemosfood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.controller.CidadeController;
import br.com.lemos.lemosfood.api.controller.EstadoController;
import br.com.lemos.lemosfood.api.model.CidadeModel;
import br.com.lemos.lemosfood.domain.model.Cidade;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel>{

    @Autowired
    private ModelMapper modelMapper;
    
    public CidadeModelAssembler () {
    	super(CidadeController.class, CidadeModel.class);
    }
    
    @Override
    public CidadeModel toModel(Cidade cidade) {
        
    	CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
    	
    	modelMapper.map(cidade, cidadeModel);
    	
//    	cidadeModel.add(linkTo(methodOn(CidadeController.class)
//				.buscar(cidadeModel.getId())).withSelfRel());

		cidadeModel.add(linkTo(methodOn(CidadeController.class)
				.listar()).withRel("cidades"));
		
		cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class)
				.buscar(cidadeModel.getEstado().getId())).withSelfRel());
    	
    	return cidadeModel;
        
    }
    
    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
    	// TODO Auto-generated method stub
    	return super.toCollectionModel(entities)
    			.add(linkTo(CidadeController.class).withSelfRel());
    }
}