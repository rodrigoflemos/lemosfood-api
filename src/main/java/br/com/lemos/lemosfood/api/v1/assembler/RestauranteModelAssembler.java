package br.com.lemos.lemosfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.v1.LemosLinks;
import br.com.lemos.lemosfood.api.v1.controller.RestauranteController;
import br.com.lemos.lemosfood.api.v1.model.RestauranteModel;
import br.com.lemos.lemosfood.core.security.LemosSecurity;
import br.com.lemos.lemosfood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler 
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private LemosLinks lemosLinks;
    
    @Autowired
    private LemosSecurity lemosSecurity;
    
    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }
    
    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);
        
        if (lemosSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(lemosLinks.linkToRestaurantes("restaurantes"));
        }
        
        if (lemosSecurity.podeGerenciarCadastroRestaurantes()) {
            if (restaurante.ativacaoPermitida()) {
                restauranteModel.add(
                        lemosLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
            }

            if (restaurante.inativacaoPermitida()) {
                restauranteModel.add(
                        lemosLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
            }
        }
        
        if (lemosSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
            if (restaurante.aberturaPermitida()) {
                restauranteModel.add(
                        lemosLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
            }

            if (restaurante.fechamentoPermitido()) {
                restauranteModel.add(
                        lemosLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
            }
        }
        
        if (lemosSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(lemosLinks.linkToProdutos(restaurante.getId(), "produtos"));
        }
        
        if (lemosSecurity.podeConsultarCozinhas()) {
            restauranteModel.getCozinha().add(
                    lemosLinks.linkToCozinha(restaurante.getCozinha().getId()));
        }
        
        if (lemosSecurity.podeConsultarCidades()) {
            if (restauranteModel.getEndereco() != null 
                    && restauranteModel.getEndereco().getCidade() != null) {
                restauranteModel.getEndereco().getCidade().add(
                        lemosLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
            }
        }
        
        if (lemosSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(lemosLinks.linkToRestauranteFormasPagamento(restaurante.getId(), 
                    "formas-pagamento"));
        }
        
        if (lemosSecurity.podeGerenciarCadastroRestaurantes()) {
            restauranteModel.add(lemosLinks.linkToRestauranteResponsaveis(restaurante.getId(), 
                    "responsaveis"));
        }
        
        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteModel> collectionModel = super.toCollectionModel(entities);
        
        if (lemosSecurity.podeConsultarRestaurantes()) {
            collectionModel.add(lemosLinks.linkToRestaurantes());
        }
        
        return collectionModel;
    }
}