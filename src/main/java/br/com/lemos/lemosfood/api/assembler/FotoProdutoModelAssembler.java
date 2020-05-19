package br.com.lemos.lemosfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.model.FotoProdutoModel;
import br.com.lemos.lemosfood.domain.model.FotoProduto;

@Component
public class FotoProdutoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public FotoProdutoModel toModel(FotoProduto foto) {
        return modelMapper.map(foto, FotoProdutoModel.class);
    }
    
 
   
}
