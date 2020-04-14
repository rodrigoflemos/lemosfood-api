package br.com.lemos.lemosfood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.model.CidadeModel;
import br.com.lemos.lemosfood.domain.model.Cidade;

@Component
public class CidadeModelAssembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public CidadeModel toModel(Cidade cidade) {
        return modelMapper.map(cidade, CidadeModel.class);
    }
    
    public List<CidadeModel> toCollectionModel(List<Cidade> cidades) {
        return cidades.stream()
                .map(cidade -> toModel(cidade))
                .collect(Collectors.toList());
    }
}
