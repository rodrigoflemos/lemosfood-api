package br.com.lemos.lemosfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.v1.LemosLinks;
import br.com.lemos.lemosfood.api.v1.controller.CidadeController;
import br.com.lemos.lemosfood.api.v1.model.CidadeModel;
import br.com.lemos.lemosfood.core.security.LemosSecurity;
import br.com.lemos.lemosfood.domain.model.Cidade;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel>{

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private LemosLinks lemosLinks;
    
    @Autowired
    private LemosSecurity lemosSecurity;
    
    public CidadeModelAssembler () {
    	super(CidadeController.class, CidadeModel.class);
    }
    
    @Override
    public CidadeModel toModel(Cidade cidade) {
        CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
        
        modelMapper.map(cidade, cidadeModel);
        
        if (lemosSecurity.podeConsultarCidades()) {
            cidadeModel.add(lemosLinks.linkToCidades("cidades"));
        }
        
        if (lemosSecurity.podeConsultarEstados()) {
            cidadeModel.getEstado().add(lemosLinks.linkToEstado(cidadeModel.getEstado().getId()));
        }
        
        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
        CollectionModel<CidadeModel> collectionModel = super.toCollectionModel(entities);
        
        if (lemosSecurity.podeConsultarCidades()) {
            collectionModel.add(lemosLinks.linkToCidades());
        }
        
        return collectionModel;
    }


}