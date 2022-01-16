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
    /**
     * Este end point lista o embarque de funcionários geral ou através dos parametros descritos abaixo.
     * @param idFuncionario
     * @param dataInicial
     * @param dataFinal
     * @return
     */
    @ApiOperation(value = "Lista de embarque de funcionários geral através dos filtros.",notes = "A data e no formato yyyy-MM-dd Ex: 2022-01-01 Obs: Existem 2 funcionários cadastrados com valor do ID de valor 1 e 2. Os campos em vazios retornará todos os embarques e desembarques já realizados.")
    @GetMapping(value = "/listaEmbarque/{idFuncionario}/{dataInicial}/{dataFinal}")
    public List<Embarque> listaembarque(@RequestParam(required = false) Integer idFuncionario,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataInicial,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataFinal){
        return embarqueService.listaembarque(idFuncionario,dataInicial,dataFinal);
    }

    /**
     * Este endpoint realiza o embarque ja realizando o desembarque com a data do desmbarque no maximo 14 dias
     * @param idFuncionario
     * @param dataEmbarque
     * @return
     */
    @ApiOperation(value = "Realiza um embarque do funcionário.",notes = "A data e no formato yyyy-MM-dd Ex: 2022-01-01. Obs: Existem 2 funcionários cadastrados com valor do ID de valor 1 e 2.")
    @PostMapping(value = "/embarqueFuncionario/{idFuncionario}/{dataEmbarque}")
    public Embarque embarqueFuncionario(@PathVariable Integer idFuncionario, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataEmbarque){
        Optional<Funcionario> funcionario = null;
        funcionario= funcionarioService.findFuncionarioUnique(idFuncionario);
        return embarqueService.embarqueFuncionario(funcionario.get() , dataEmbarque);
    }



    /**
     * Este end point exclui um embarque da lista de emabarque
     * @param idEmbarque
     * @return
     */
    @ApiOperation(value = "Exclui um embarque da lista.", notes = "Exclui através do ID do embarque emitirá uma mensagem.")
    @DeleteMapping(value = "/excluirEmbarque/{idEmbarque}")
    public String excluirEmbarque(@PathVariable(required = true)  Integer idEmbarque){
        return  embarqueService.excluirEmbarque(idEmbarque);
    }


    /**
     * Este end point realiza a alteração da data do desembarque
     * @param idFuncionario
     * @param dataDesembarque
     * @return
     */
    @ApiOperation(value = "Alteração da data do desembarque de funcionário.",notes = "Realiza o desembarque através do ID do funcionário. Obs: Existem 2 funcionários cadastrados com valor do ID de valor 1 e 2.")
    @PutMapping(value = "/desembarqueFuncionario/{idFuncionario}/{dataDesembarque}")
    public Optional<Embarque> desembarqueFuncionario(@RequestParam Integer idFuncionario, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataDesembarque){
        return embarqueService.desembarqueFuncionario(idFuncionario , dataDesembarque);
    }

}