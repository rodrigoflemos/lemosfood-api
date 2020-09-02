package br.com.lemos.lemosfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.v1.LemosLinks;
import br.com.lemos.lemosfood.api.v1.controller.EstadoController;
import br.com.lemos.lemosfood.api.v1.model.EstadoModel;
import br.com.lemos.lemosfood.core.security.LemosSecurity;
import br.com.lemos.lemosfood.domain.model.Estado;

@Component
public class EstadoModelAssembler 
        extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LemosLinks lemosLinks;
    
    @Autowired
    private LemosSecurity lemosSecurity;
    
    public EstadoModelAssembler() {
        super(EstadoController.class, EstadoModel.class);
    }
    
    @Override
    public EstadoModel toModel(Estado estado) {
        EstadoModel estadoModel = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, estadoModel);
        
        if (lemosSecurity.podeConsultarEstados()) {
            estadoModel.add(lemosLinks.linkToEstados("estados"));
        }
        
        return estadoModel;
    }

    @Override
    public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
        CollectionModel<EstadoModel> collectionModel = super.toCollectionModel(entities);
        
        if (lemosSecurity.podeConsultarEstados()) {
            collectionModel.add(lemosLinks.linkToEstados());
        }
        
        return collectionModel;
    }
}        