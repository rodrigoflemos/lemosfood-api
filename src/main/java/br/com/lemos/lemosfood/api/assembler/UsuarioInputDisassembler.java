package br.com.lemos.lemosfood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lemos.lemosfood.api.model.input.UsuarioInput;
import br.com.lemos.lemosfood.domain.model.Usuario;

@Component
public class UsuarioInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public Usuario toDomainObject(UsuarioInput usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }
    
    public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
        modelMapper.map(usuarioInput, usuario);
    }            
}
