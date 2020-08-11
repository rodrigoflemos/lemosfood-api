package br.com.lemos.lemosfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.LemosLinks;
import br.com.lemos.lemosfood.api.controller.RestauranteController;
import br.com.lemos.lemosfood.api.model.RestauranteModel;
import br.com.lemos.lemosfood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler 
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private LemosLinks lemosLinks;
    
    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }
    
    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);
        
        restauranteModel.add(lemosLinks.linkToRestaurantes("restaurantes"));
        
        if (restaurante.ativacaoPermitida()) {
            restauranteModel.add(
            		lemosLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
        }

        if (restaurante.inativacaoPermitida()) {
            restauranteModel.add(
            		lemosLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
        }

        if (restaurante.aberturaPermitida()) {
            restauranteModel.add(
            		lemosLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
        }

        if (restaurante.fechamentoPermitido()) {
            restauranteModel.add(
            		lemosLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
        }
        
        restauranteModel.add(lemosLinks.linkToProdutos(restaurante.getId(), "produtos"));
        
        restauranteModel.getCozinha().add(
        		lemosLinks.linkToCozinha(restaurante.getCozinha().getId()));
        
        if (restauranteModel.getEndereco() != null 
                && restauranteModel.getEndereco().getCidade() != null) {
            restauranteModel.getEndereco().getCidade().add(
            		lemosLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
        }
        
        restauranteModel.add(lemosLinks.linkToRestauranteFormasPagamento(restaurante.getId(), 
                "formas-pagamento"));
        
        restauranteModel.add(lemosLinks.linkToRestauranteResponsaveis(restaurante.getId(), 
                "responsaveis"));
        
        return restauranteModel;
    }
    
    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(lemosLinks.linkToRestaurantes());
    }   
}