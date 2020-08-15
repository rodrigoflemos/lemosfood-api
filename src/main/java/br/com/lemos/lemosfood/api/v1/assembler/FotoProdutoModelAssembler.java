package br.com.lemos.lemosfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.v1.LemosLinks;
import br.com.lemos.lemosfood.api.v1.controller.RestauranteProdutoFotoController;
import br.com.lemos.lemosfood.api.v1.model.FotoProdutoModel;
import br.com.lemos.lemosfood.domain.model.FotoProduto;

@Component
public class FotoProdutoModelAssembler 
        extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private LemosLinks lemosLinks;
    
    public FotoProdutoModelAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
    }
    
    @Override
    public FotoProdutoModel toModel(FotoProduto foto) {
        FotoProdutoModel fotoProdutoModel = modelMapper.map(foto, FotoProdutoModel.class);
        
        fotoProdutoModel.add(lemosLinks.linkToFotoProduto(
                foto.getRestauranteId(), foto.getProduto().getId()));
        
        fotoProdutoModel.add(lemosLinks.linkToProduto(
                foto.getRestauranteId(), foto.getProduto().getId(), "produto"));
        
        return fotoProdutoModel;
    }   
}


