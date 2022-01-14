package com.oligas.services;
import com.oligas.models.Funcionario;
import com.oligas.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {
    @Autowired
    FuncionarioRepository funcionarioRepository;

    /**
     * Salvar o funcionário
     * @param funcionario
     * @return
     */
    public Funcionario salvar(Funcionario funcionario) {
        return this.funcionarioRepository.save(funcionario);
    }

    /**
     * Lista toos os funcionarios filtrados ou não
     * @return
     */
    public List<Funcionario> listFuncionarios(String nomeFuncionario) {
        if (nomeFuncionario !=null){
            return this.funcionarioRepository.findFuncionarioPorNome(nomeFuncionario.toUpperCase());
        }else{
            return this.funcionarioRepository.findAll();
        }
   }

    /**
     * Busca funcionário pido ID
     * @param id
     * @return
     */
    public Optional<Funcionario> findFuncionarioUnique(Integer id){
        return funcionarioRepository.findById(id);
    }

    /**
     * Exclur um funcionário pelo ID
     * @param id
     * @return
     */
    public String excluirFuncionario(Integer id){
        try {
            funcionarioRepository.deleteById(id);
            return "Funcionario excluido com sucesso !!";
        }catch (Exception e){
            return  "Erro ao excluir funcionario ou funcionario está embarcado !!";
        }

    }
}
