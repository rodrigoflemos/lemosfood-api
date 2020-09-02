package br.com.lemos.lemosfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.v1.LemosLinks;
import br.com.lemos.lemosfood.api.v1.controller.PedidoController;
import br.com.lemos.lemosfood.api.v1.model.PedidoModel;
import br.com.lemos.lemosfood.core.security.LemosSecurity;
import br.com.lemos.lemosfood.domain.model.Pedido;

@Component
public class PedidoModelAssembler 
        extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private LemosLinks lemosLinks;
    
    @Autowired
    private LemosSecurity lemosSecurity;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }
    
    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);
        
        // Não usei o método lemosSecurity.podePesquisarPedidos(clienteId, restauranteId) aqui,
        // porque na geração do link, não temos o id do cliente e do restaurante, 
        // então precisamos saber apenas se a requisição está autenticada e tem o escopo de leitura
        if (lemosSecurity.podePesquisarPedidos()) {
            pedidoModel.add(lemosLinks.linkToPedidos("pedidos"));
        }
        
        if (lemosSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
            if (pedido.podeSerConfirmado()) {
                pedidoModel.add(lemosLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
            }
            
            if (pedido.podeSerCancelado()) {
                pedidoModel.add(lemosLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
            }
            
            if (pedido.podeSerEntregue()) {
                pedidoModel.add(lemosLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
            }
        }
        
        if (lemosSecurity.podeConsultarRestaurantes()) {
            pedidoModel.getRestaurante().add(
            		lemosLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        }
        
        if (lemosSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoModel.getCliente().add(
            		lemosLinks.linkToUsuario(pedido.getCliente().getId()));
        }
        
        if (lemosSecurity.podeConsultarFormasPagamento()) {
            pedidoModel.getFormaPagamento().add(
            		lemosLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
        }
        
        if (lemosSecurity.podeConsultarCidades()) {
            pedidoModel.getEnderecoEntrega().getCidade().add(
            		lemosLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
        }
        
        // Quem pode consultar restaurantes, também pode consultar os produtos dos restaurantes
        if (lemosSecurity.podeConsultarRestaurantes()) {
            pedidoModel.getItens().forEach(item -> {
                item.add(lemosLinks.linkToProduto(
                        pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
            });
        }
        
        return pedidoModel;
    }
}