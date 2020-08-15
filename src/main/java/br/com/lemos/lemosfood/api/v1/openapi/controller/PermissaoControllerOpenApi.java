package br.com.lemos.lemosfood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import br.com.lemos.lemosfood.api.v1.model.PermissaoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Permissões")
public interface PermissaoControllerOpenApi {

    @ApiOperation("Lista as permissões")
    CollectionModel<PermissaoModel> listar();
    
}
