package com.oligas.controllers;

import com.oligas.models.Embarque;
import com.oligas.models.Funcionario;
import com.oligas.services.EmbarqueService;
import com.oligas.services.FuncionarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api")
@Api(value = "Desafio")
/**
 * Classe cotroller onde tem os ends points da API
 */
public class EmbarqueDesembarqueController {
    @Autowired(required = false)
    private EmbarqueService embarqueService;
    @Autowired(required = false)
    private FuncionarioService funcionarioService;
    //Este endpoint lista o embaque de funcionários geral ou através dos parametros descritos abaixo.
    @ApiOperation(value = "Lista de embarque de funcionários geral através dos filtros.",notes = "A data e no formato yyyy-MM-dd Ex: 2022-01-01")
    @GetMapping(value = "/listaEmbarque/{idFuncionario}/{dataInicial}/{dataFinal}")
    public List<Embarque> listaembarque(@RequestParam(required = false) Integer idFuncinario,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataInicial,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataFinal){
        return embarqueService.listaembarque(idFuncinario,dataInicial,dataFinal);
    }

    //Este endpoint realiza o embarque ja realizando o desembarque com a data do desmbarque no maximo 14 dias
    @ApiOperation(value = "Realiza um embarque do funcionário.",notes = "A data e no formato yyyy-MM-dd Ex: 2022-01-01")
    @PostMapping(value = "/embarqueFuncionario/{idFuncionario}/{dataEmbarque}/{dataDesembarque}")
    public Embarque embarqueFuncionario(@PathVariable Integer idFuncionario, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataEmbarque,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataDesembarque){
        Optional<Funcionario> funcionario = null;
        funcionario= funcionarioService.findFuncionarioUnique(idFuncionario);
        return embarqueService.embarqueFuncionario(funcionario.get() , dataEmbarque,dataDesembarque);
    }

    //Exclui um embarque da lista de emabarque
    @ApiOperation(value = "Exclui um embarque da lista.", notes = "Exclui através do ID do embarque como parametro.")
    @DeleteMapping(value = "/excluirEmbarque/{idEmbarque}")
    public String excluirEmbarque(@PathVariable(required = true)  Integer idEmbarque){
        return  embarqueService.excluirEmbarque(idEmbarque);
    }


    //Realiza a alteração da data do  desembarque
    @ApiOperation(value = "Alteração da data do desembarque de funcionário.",notes = "Realiza a alteração da data do desembarque através do ID do emabarque")
    @PutMapping(value = "/desembarqueFuncionario/{idEmbarque}/{dataDesembarque}")
    public Optional<Embarque> desembarqueFuncionario(@RequestParam Integer idEmbarque, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataDesembarque){
        return embarqueService.desembarqueFuncionario(idEmbarque , dataDesembarque);
    }

}