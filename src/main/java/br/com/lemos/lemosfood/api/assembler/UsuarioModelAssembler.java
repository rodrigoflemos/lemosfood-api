package br.com.lemos.lemosfood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.LemosLinks;
import br.com.lemos.lemosfood.api.controller.UsuarioController;
import br.com.lemos.lemosfood.api.model.UsuarioModel;
import br.com.lemos.lemosfood.domain.model.Usuario;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private LemosLinks lemosLinks;
    
    public UsuarioModelAssembler() {
        super(UsuarioController.class, UsuarioModel.class);
    }
    
    @Override
    public UsuarioModel toModel(Usuario usuario) {
        UsuarioModel usuarioModel = createModelWithId(usuario.getId(), usuario);
        modelMapper.map(usuario, usuarioModel);
        
        usuarioModel.add(lemosLinks.linkToUsuarios("usuarios"));
        
        usuarioModel.add(lemosLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
        
        return usuarioModel;
    }
    
    @Override
    public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
            .add(linkTo(UsuarioController.class).withSelfRel());
    }   
}               
