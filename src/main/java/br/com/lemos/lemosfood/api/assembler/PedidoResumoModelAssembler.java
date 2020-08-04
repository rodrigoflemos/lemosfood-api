package br.com.lemos.lemosfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.LemosLinks;
import br.com.lemos.lemosfood.api.controller.PedidoController;
import br.com.lemos.lemosfood.api.model.PedidoResumoModel;
import br.com.lemos.lemosfood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler 
        extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private LemosLinks lemosLinks;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }
    
    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);
        
        pedidoModel.add(lemosLinks.linkToPedidos("pedidos"));
        
        pedidoModel.getRestaurante().add(
        		lemosLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoModel.getCliente().add(lemosLinks.linkToUsuario(pedido.getCliente().getId()));
        
        return pedidoModel;
    }
}