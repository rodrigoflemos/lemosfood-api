package br.com.lemos.lemosfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.v1.LemosLinks;
import br.com.lemos.lemosfood.api.v1.controller.PedidoController;
import br.com.lemos.lemosfood.api.v1.model.PedidoResumoModel;
import br.com.lemos.lemosfood.core.security.LemosSecurity;
import br.com.lemos.lemosfood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler 
        extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private LemosLinks lemosLinks;
    
    @Autowired
    private LemosSecurity lemosSecurity;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }
    
    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);
        
        if (lemosSecurity.podePesquisarPedidos()) {
            pedidoModel.add(lemosLinks.linkToPedidos("pedidos"));
        }
        
        if (lemosSecurity.podeConsultarRestaurantes()) {
            pedidoModel.getRestaurante().add(
            		lemosLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        }

        if (lemosSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoModel.getCliente().add(lemosLinks.linkToUsuario(pedido.getCliente().getId()));
        }
        
        return pedidoModel;
    }
}