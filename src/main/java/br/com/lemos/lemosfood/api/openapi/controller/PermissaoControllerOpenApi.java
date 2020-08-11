package br.com.lemos.lemosfood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import br.com.lemos.lemosfood.api.model.PermissaoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Permissões")
public interface PermissaoControllerOpenApi {

    @ApiOperation("Lista as permissões")
    CollectionModel<PermissaoModel> listar();
    
}
