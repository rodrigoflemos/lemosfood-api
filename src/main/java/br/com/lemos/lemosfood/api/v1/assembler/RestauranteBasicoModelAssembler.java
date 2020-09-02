package br.com.lemos.lemosfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.v1.LemosLinks;
import br.com.lemos.lemosfood.api.v1.controller.RestauranteController;
import br.com.lemos.lemosfood.api.v1.model.RestauranteBasicoModel;
import br.com.lemos.lemosfood.core.security.LemosSecurity;
import br.com.lemos.lemosfood.domain.model.Restaurante;

@Component
public class RestauranteBasicoModelAssembler 
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private LemosLinks lemosLinks;
    
    @Autowired
    private LemosSecurity lemosSecurity;
    
    public RestauranteBasicoModelAssembler() {
        super(RestauranteController.class, RestauranteBasicoModel.class);
    }
    
    @Override
    public RestauranteBasicoModel toModel(Restaurante restaurante) {
        RestauranteBasicoModel restauranteModel = createModelWithId(
                restaurante.getId(), restaurante);
        
        modelMapper.map(restaurante, restauranteModel);
        
        if (lemosSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(lemosLinks.linkToRestaurantes("restaurantes"));
        }
        
        if (lemosSecurity.podeConsultarCozinhas()) {
            restauranteModel.getCozinha().add(
                    lemosLinks.linkToCozinha(restaurante.getCozinha().getId()));
        }
        
        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteBasicoModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteBasicoModel> collectionModel = super.toCollectionModel(entities);
        
        if (lemosSecurity.podeConsultarRestaurantes()) {
            collectionModel.add(lemosLinks.linkToRestaurantes());
        }
                
        return collectionModel;
    }
}