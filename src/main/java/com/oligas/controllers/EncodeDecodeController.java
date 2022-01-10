package com.oligas.controllers;

import com.oligas.services.EncodeDecode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/desafio")
@Api(value = "Desafio")
/**
 * Classe cotroller onde tem os ends points da API do decode encode
 */
public class EncodeDecodeController {
    @Autowired(required = true)
    private EncodeDecode encodeDecode;

    @ApiOperation(value = "Encode de teste")
    @GetMapping(value = "/encode/{valor}")
    public String encode(@PathVariable(required = true)  @Size(min = 1, max = 8)  String valor) {
        try {
            return encodeDecode.getEnconde(Integer.parseInt(valor));
        }catch (Exception e){
            return "Nao pode codificar numeros maiores que 8 digitos...";
        }
    }

    @ApiOperation(value = "Decode de teste")
    @GetMapping(value = "/decode/{codigo}")
    public String decode(@PathVariable String codigo){
        return encodeDecode.getDecode(codigo);
    }

}