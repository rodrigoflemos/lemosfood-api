package br.com.lemos.lemosfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.v1.LemosLinks;
import br.com.lemos.lemosfood.api.v1.model.PermissaoModel;
import br.com.lemos.lemosfood.domain.model.Permissao;

@Component
public class PermissaoModelAssembler 
        implements RepresentationModelAssembler<Permissao, PermissaoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private LemosLinks lemosLinks;

    @Override
    public PermissaoModel toModel(Permissao permissao) {
        PermissaoModel permissaoModel = modelMapper.map(permissao, PermissaoModel.class);
        return permissaoModel;
    }
    
    @Override
    public CollectionModel<PermissaoModel> toCollectionModel(Iterable<? extends Permissao> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(lemosLinks.linkToPermissoes());
    }   
}