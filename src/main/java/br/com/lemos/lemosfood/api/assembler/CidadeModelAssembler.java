package br.com.lemos.lemosfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.LemosLinks;
import br.com.lemos.lemosfood.api.controller.CidadeController;
import br.com.lemos.lemosfood.api.model.CidadeModel;
import br.com.lemos.lemosfood.domain.model.Cidade;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel>{

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private LemosLinks lemosLinks;
    
    public CidadeModelAssembler () {
    	super(CidadeController.class, CidadeModel.class);
    }
    
    @Override
    public CidadeModel toModel(Cidade cidade) {
        
    	CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
    	
    	modelMapper.map(cidade, cidadeModel);
    	
    	cidadeModel.add(lemosLinks.linkToCidades("cidades"));
        
        cidadeModel.getEstado().add(lemosLinks.linkToEstado(cidadeModel.getEstado().getId()));
    	
    	return cidadeModel;
        
    }
    
    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(lemosLinks.linkToCidades());
    }
}