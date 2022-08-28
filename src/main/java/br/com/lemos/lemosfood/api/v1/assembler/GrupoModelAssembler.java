package br.com.lemos.lemosfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.v1.LemosLinks;
import br.com.lemos.lemosfood.api.v1.controller.GrupoController;
import br.com.lemos.lemosfood.api.v1.model.GrupoModel;
import br.com.lemos.lemosfood.core.security.LemosSecurity;
import br.com.lemos.lemosfood.domain.model.Grupo;

@Component
public class GrupoModelAssembler 
        extends RepresentationModelAssemblerSupport<Grupo, GrupoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private LemosLinks lemosLinks;
    
    @Autowired
    private LemosSecurity lemosSecurity;
    
    public GrupoModelAssembler() {
        super(GrupoController.class, GrupoModel.class);
    }
    
    @Override
    public GrupoModel toModel(Grupo grupo) {
        GrupoModel grupoModel = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoModel);
        
        if (lemosSecurity.podeConsultarUsuariosGruposPermissoes()) {
            grupoModel.add(lemosLinks.linkToGrupos("grupos"));
            
            grupoModel.add(lemosLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
        }
        
        return grupoModel;
    }

    @Override
    public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
        CollectionModel<GrupoModel> collectionModel = super.toCollectionModel(entities);
        
        if (lemosSecurity.podeConsultarUsuariosGruposPermissoes()) {
            collectionModel.add(lemosLinks.linkToGrupos());
        }
        
        return collectionModel;
    }
}