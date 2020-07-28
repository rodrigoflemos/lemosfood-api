package br.com.lemos.lemosfood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.controller.CozinhaController;
import br.com.lemos.lemosfood.api.model.CozinhaModel;
import br.com.lemos.lemosfood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel>{

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaModelAssembler() {
    	super(CozinhaController.class, CozinhaModel.class);
	}
    
    @Override
    public CozinhaModel toModel(Cozinha cozinha) {
    	
    	CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);
        
        cozinhaModel.add(linkTo(CozinhaController.class).withRel("cozinhas"));
        
        return cozinhaModel;
    }
}             