package br.com.lemos.lemosfood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.v1.model.input.PermissaoInput;
import br.com.lemos.lemosfood.domain.model.Permissao;

@Component
public class PermissaoInputDisassembler {

	@Autowired
    private ModelMapper modelMapper;
    
    public Permissao toDomainObject(PermissaoInput permissaoInput) {
        return modelMapper.map(permissaoInput, Permissao.class);
    }
    
    public void copyToDomainObject(PermissaoInput permissaoInput, Permissao permissao) {
        modelMapper.map(permissaoInput, permissao);
    }   
}
