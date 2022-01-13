package com.oligas.controllers;

import com.oligas.services.EncodeDecode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    /**
     * Este end point faz o encode de uma valor.
     * @param number
     * @return
     */
    @ApiOperation(value = "Encode de teste")
    @GetMapping(value = "/encode/{number}")
    public String encode(@PathVariable(required = true)  @Size (min = 1, max = 8)  String number) {
        try {
            return encodeDecode.getEnconde(Integer.parseInt(number));
        }catch (Exception e){
            return "Nao pode codificar numeros maiores que 8 digitos...";
        }
    }

    /**
     * Este end pont faz o decode do valor passado no encide.
     * @param code
     * @return
     */
    @ApiOperation(value = "Decode de teste")
    @GetMapping(value = "/decode/{code}")
    public String decode(@PathVariable(required = true) String code){
        return encodeDecode.getDecode(code);
    }

}