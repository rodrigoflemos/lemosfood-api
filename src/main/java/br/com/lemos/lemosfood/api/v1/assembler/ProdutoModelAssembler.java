package br.com.lemos.lemosfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.v1.LemosLinks;
import br.com.lemos.lemosfood.api.v1.controller.RestauranteProdutoController;
import br.com.lemos.lemosfood.api.v1.model.ProdutoModel;
import br.com.lemos.lemosfood.domain.model.Produto;

@Component
public class ProdutoModelAssembler 
        extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private LemosLinks lemosLinks;
    
    public ProdutoModelAssembler() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }
    
    @Override
    public ProdutoModel toModel(Produto produto) {
        ProdutoModel produtoModel = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId());
        
        modelMapper.map(produto, produtoModel);
        
        produtoModel.add(lemosLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

        produtoModel.add(lemosLinks.linkToFotoProduto(
                produto.getRestaurante().getId(), produto.getId(), "foto"));
        
        return produtoModel;
    }       
} 
