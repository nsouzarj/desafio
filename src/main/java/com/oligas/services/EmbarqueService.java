package com.oligas.services;
import com.oligas.config.UtilsDate;
import com.oligas.models.Embarque;
import com.oligas.models.Funcionario;
import com.oligas.repository.EmbarqueRepository;
import com.oligas.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service

public class EmbarqueService {
    @Autowired (required = false)
    EmbarqueRepository embarqueRepository;
    @Autowired (required = false)
    FuncionarioRepository funcionarioRepository;

    /**
     * Lista os embarques
     * @return
     */
    public List<Embarque> listaembarque (Integer idFuncionario, Date dataInicial, Date dataFinal) {
        if (idFuncionario != null && dataInicial ==null && dataFinal==null) {
            return embarqueRepository.findEmbarqueIdFuncionario(idFuncionario);
        }else  if (idFuncionario != null && dataInicial !=null && dataFinal!=null){
            return embarqueRepository.findEmbarqueByAll(idFuncionario,dataInicial,dataFinal);
        }else{
            return embarqueRepository.findAll();
        }
    }

    /**
     * Realiza o emabrque dos funcionarios
     * @param funcionario
     * @param dataEmbarque
     * @return
     */
    public Embarque embarqueFuncionario (Funcionario funcionario, Date dataEmbarque) {
        UtilsDate utilsDate = new UtilsDate();
        Integer diasEmbarcado=null;
        Integer diasDeFolga = null;
        Embarque embarque = new Embarque();

        //Traz a última data do embarque fechado
        Embarque embarqueAnteriorFechado = embarqueRepository.findUltimoEmbarqueFechado(funcionario.getIdFuncionario());

        //Traz a última data do embarque aberto
        Embarque embarqueAnteriorAberto = embarqueRepository.findUltimoEmbarqueAberto(funcionario.getIdFuncionario());

        //Aqui verifica os dias de folga caso for null é o primeiro embarque verificando
        //O Último desembarque pela data de desembarque
        if(embarqueAnteriorFechado!=null){
              diasDeFolga = Math.toIntExact(utilsDate.getDiasCorridos(embarqueAnteriorFechado.getDataDesembarque(), dataEmbarque));
            if (diasDeFolga <=7){
                return null;
            }
        }
        //Verifica se tem 14 dias embarcado se for maior nao faz o embarque
        if (embarqueAnteriorAberto!=null){
            diasEmbarcado =Math.toIntExact(utilsDate.getDiasCorridos(embarqueAnteriorAberto.getDataEmbarque(), dataEmbarque));
            if (diasEmbarcado >=1 && diasEmbarcado <=14){
                return null;
            }
            if (diasEmbarcado > 14 && embarqueAnteriorAberto.getDataDesembarque()==null){
                return null;
            }
        }

        try {
            embarque = new Embarque();
            embarque.setFuncionario(funcionario);
            embarque.setDataEmbarque(dataEmbarque);
            embarque.setDiasMinimoDeFolga(7);
            embarqueRepository.save(embarque);
                return embarque;
            } catch (Exception e) {
                return embarque;
        }

    }

    /**
     * Realiza a alteração do desembarque do funcionário pelo ID do
     * embarque
     * @param idFuncionario
     * @param dataDesembarque
     * @return
     */
    public Optional<Embarque> desembarqueFuncionario (Integer idFuncionario, Date dataDesembarque) {
        try {
            Optional<Embarque> embarqueAberto = Optional.ofNullable(embarqueRepository.findUltimoEmbarqueAberto(idFuncionario));
             UtilsDate utilsDate = new UtilsDate();
             Long diasEmbarcado = utilsDate.getDiasCorridos(embarqueAberto.get().getDataEmbarque(), dataDesembarque);
             // Trata a diferenca de dias decorrido
             if (diasEmbarcado >= 1 && diasEmbarcado <= 14) {
                 embarqueAberto.get().setDiasMaximoEmbarcado(Math.toIntExact(diasEmbarcado));
                 embarqueAberto.get().setDataDesembarque(dataDesembarque);
                 embarqueRepository.save(embarqueAberto.get());
                 return embarqueAberto;
             } else {
                 return null;
             }
        } catch (Exception e) {
            System.out.print("Nao existe desembarque");
            return null;
        }
    }

    /**
     * Excluir o embarque da base de dados
     * @param idEmbarque
     * @return
     */
    public String excluirEmbarque(Integer idEmbarque){
        try {
            embarqueRepository.deleteById(idEmbarque);
            return "Embarque deletado com sucesso  da base de dados !!";
        }catch (Exception e){
            return "Erro ao excluir o embarque da base de dados !!";
        }

    }
}
