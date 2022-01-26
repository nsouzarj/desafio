package com.oligas.services;
import com.oligas.config.UtilsDate;
import com.oligas.models.EmbarqueDesembarque;
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
    public List<EmbarqueDesembarque> listaembarque (Integer idFuncionario, Date dataInicial, Date dataFinal) {
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
    public EmbarqueDesembarque embarqueFuncionario (Funcionario funcionario, Date dataEmbarque) {
        UtilsDate utilsDate = new UtilsDate();
        Integer diasEmbarcado=null;
        Integer diasDeFolga = null;
        EmbarqueDesembarque embarqueDesembarque = new EmbarqueDesembarque();

        //Traz a última data do embarque fechado
        EmbarqueDesembarque embarqueDesembarqueAnteriorFechado = embarqueRepository.findUltimoEmbarqueFechado(funcionario.getIdFuncionario());

        //Traz a última data do embarque aberto
        EmbarqueDesembarque embarqueDesembarqueAnteriorAberto = embarqueRepository.findUltimoEmbarqueAberto(funcionario.getIdFuncionario());

        //Aqui verifica os dias de folga caso for null é o primeiro embarque verificando
        //O Último desembarque pela data de desembarque
        if(embarqueDesembarqueAnteriorFechado !=null){
              diasDeFolga = Math.toIntExact(utilsDate.getDiasCorridos(embarqueDesembarqueAnteriorFechado.getDataDesembarque(), dataEmbarque));
            if (diasDeFolga <=7){
                return null;
            }
        }
        //Verifica se tem 14 dias embarcado se for maior nao faz o embarque
        if (embarqueDesembarqueAnteriorAberto !=null){
            diasEmbarcado =Math.toIntExact(utilsDate.getDiasCorridos(embarqueDesembarqueAnteriorAberto.getDataEmbarque(), dataEmbarque));
            if (diasEmbarcado >=1 && diasEmbarcado <=14){
                return null;
            }
            if (diasEmbarcado > 14 && embarqueDesembarqueAnteriorAberto.getDataDesembarque()==null){
                return null;
            }
        }

        try {
            embarqueDesembarque = new EmbarqueDesembarque();
            embarqueDesembarque.setFuncionario(funcionario);
            embarqueDesembarque.setDataEmbarque(dataEmbarque);
            embarqueDesembarque.setDiasMinimoDeFolga(7);
            embarqueRepository.save(embarqueDesembarque);
                return embarqueDesembarque;
            } catch (Exception e) {
                return embarqueDesembarque;
        }

    }

    /**
     * Realiza a alteração do desembarque do funcionário pelo ID do
     * embarque
     * @param idFuncionario
     * @param dataDesembarque
     * @return
     */
    public Optional<EmbarqueDesembarque> desembarqueFuncionario (Integer idFuncionario, Date dataDesembarque) {
        try {
            Optional<EmbarqueDesembarque> embarqueAberto = Optional.ofNullable(embarqueRepository.findUltimoEmbarqueAberto(idFuncionario));
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
