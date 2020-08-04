package br.com.lemos.lemosfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.LemosLinks;
import br.com.lemos.lemosfood.api.controller.PedidoController;
import br.com.lemos.lemosfood.api.model.PedidoModel;
import br.com.lemos.lemosfood.domain.model.Pedido;

@Component
public class PedidoModelAssembler 
        extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private LemosLinks lemosLinks;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }
    
    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);
        
        pedidoModel.add(lemosLinks.linkToPedidos());
        
        pedidoModel.add(lemosLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
        
        pedidoModel.add(lemosLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
        
        pedidoModel.add(lemosLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
        
        pedidoModel.getRestaurante().add(
        		lemosLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        
        pedidoModel.getCliente().add(
        		lemosLinks.linkToUsuario(pedido.getCliente().getId()));
        
        pedidoModel.getFormaPagamento().add(
        		lemosLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
        
        pedidoModel.getEnderecoEntrega().getCidade().add(
        		lemosLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
        
        pedidoModel.getItens().forEach(item -> {
            item.add(lemosLinks.linkToProduto(
                    pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
        });
        
        return pedidoModel;
    }
}