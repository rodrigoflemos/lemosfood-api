package br.com.lemos.lemosfood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import br.com.lemos.lemosfood.api.model.RestauranteBasicoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("RestaurantesBasicoModel")
@Data
public class RestaurantesBasicoModelOpenApi {

    private RestaurantesEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    @ApiModel("RestaurantesEmbeddedModel")
    @Data
    public class RestaurantesEmbeddedModelOpenApi {
        
        private List<RestauranteBasicoModel> restaurantes;   
    } 
}  