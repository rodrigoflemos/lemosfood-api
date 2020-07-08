package br.com.lemos.lemosfood.api.model;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoModel extends RepresentationModel<EstadoModel>{

	@ApiModelProperty(example = "1")
    private Long id;
	
	@ApiModelProperty(example = "São Paulo")
    private String nome;
}
