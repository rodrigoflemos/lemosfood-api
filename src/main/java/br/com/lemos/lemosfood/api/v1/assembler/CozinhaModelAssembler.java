package br.com.lemos.lemosfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.v1.LemosLinks;
import br.com.lemos.lemosfood.api.v1.controller.CozinhaController;
import br.com.lemos.lemosfood.api.v1.model.CozinhaModel;
import br.com.lemos.lemosfood.core.security.LemosSecurity;
import br.com.lemos.lemosfood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel>{

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private LemosLinks lemosLinks;
    
    @Autowired
    private LemosSecurity lemosSecurity;

    public CozinhaModelAssembler() {
    	super(CozinhaController.class, CozinhaModel.class);
	}
    
    @Override
    public CozinhaModel toModel(Cozinha cozinha) {
        CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);
        
        if (lemosSecurity.podeConsultarCozinhas()) {
            cozinhaModel.add(lemosLinks.linkToCozinhas("cozinhas"));
        }
        
        return cozinhaModel;
    }
}             