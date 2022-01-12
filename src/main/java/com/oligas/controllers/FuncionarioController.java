package com.oligas.controllers;
import com.oligas.services.FuncionarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.oligas.models.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api")
@Api(value = "Desafio")
/**
 * Classe cotroller onde tem os ends points da API em relaçao aos funcionários
 */
public class FuncionarioController {
    @Autowired(required = false)
    private FuncionarioService funcionarioService;
    /**
     * Este end point realiza a busca do funcionário atrvés do ID
     * @param id
     * @return
     */
    @ApiOperation(value = "Busca funcionário pelo ID.", notes = "Obs: Existem 2 funcionários cadastrados com valor do ID de valor 1 e 2")
    @GetMapping(value = "/funcionario/{id}")
    public Optional<Funcionario> funcionario(@PathVariable(required = true) Integer id){
        return funcionarioService.findFuncionarioUnique(id);
    }

    /**
     * Este endpoint salva os dados do funcionário cadastrado na base de dados
     * @param funcionario
     * @return
     */
    @ApiOperation(value = "Salva uma funcionário novo na lista de funcionários.", notes = "Obs:Existem 2 funcionários cadastrados com valor do ID de valor 1 e 2.")
    @PostMapping(value = "/salvar")
    public Funcionario salvar(Funcionario funcionario) {
        return this.funcionarioService.salvar(funcionario);
    }

    /**
     * Este end point realiza a exclusão do funcionário da base caso o funcionário esteje na base de embarque
     * Não será excluido
     * @param id
     * @return
     */
    @ApiOperation(value = "Exclui o funcionário pelo ID.", notes = "Obs: Existem 2 funcionários cadastrados com valor do ID de valor 1 e 2.")
    @DeleteMapping(value = "/funcionario/{id}")
    public String excluiFuncionario(@PathVariable(required = true) Integer id){
        return funcionarioService.excluirFuncionario(id);
    }

    /**
     * Este end point realiza a busca dos funcionario através dos critérios de busca conforme o parametros abaixo
     * Podendo ser vazio ou não
     * @param nomeFuncionario
     * @return
     */
    @ApiOperation(value = "Lista todos os funcionários cadastrado na base de dados.",notes = "Osb: Existem 2 funcionários cadastrados com valor do ID de valor 1 e 2.")
    @GetMapping(value = "/listaFuncionario/{nomeFuncionario}")
    public List<Funcionario> listaFuncionairios(@RequestParam(required = false) String nomeFuncionario) {
      return this.funcionarioService.listFuncionarios(nomeFuncionario);
    }

}