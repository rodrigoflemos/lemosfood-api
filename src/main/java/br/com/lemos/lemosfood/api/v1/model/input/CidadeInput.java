package br.com.lemos.lemosfood.api.v1.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeInput {

	@ApiModelProperty(example = "São Paulo", required = true)
    @NotBlank
    private String nome;
    
    @Valid
    @NotNull
    private EstadoIdInput estado;
    
}