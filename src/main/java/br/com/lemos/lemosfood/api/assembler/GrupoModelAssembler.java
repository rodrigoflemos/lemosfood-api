package br.com.lemos.lemosfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.model.GrupoModel;
import br.com.lemos.lemosfood.domain.model.Grupo;

@Component
public class GrupoModelAssembler {

	@Autowired
    private ModelMapper modelMapper;
    
    public GrupoModel toModel(Grupo grupo) {
        return modelMapper.map(grupo, GrupoModel.class);
    }
    
    public List<GrupoModel> toCollectionModel(List<Grupo> grupos) {
        return grupos.stream()
                .map(grupo -> toModel(grupo))
                .collect(Collectors.toList());
    }   
}