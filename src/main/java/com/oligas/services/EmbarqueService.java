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
     *
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
     *
     * @param funcionario
     * @param dataEmbarque
     * @return
     */
    public Embarque embarqueFuncionario (Funcionario funcionario, Date dataEmbarque, Date dataDesembarque) {
        UtilsDate utilsDate = new UtilsDate();

        //Traz a última data do desembarque do funcionário da base
        Embarque embarqueAnterior = embarqueRepository.findUltimoEmbarque(funcionario.getIdFuncionario());

        //Aqui verifica os dias de folga caso for null é o primeiro embarque verificando
        //O Último desembarque pela data de desembarque
        if(embarqueAnterior!=null){
            Long diasDeFolga =utilsDate.getDiasCorridos(embarqueAnterior.getDataDesenbarque(),dataEmbarque);
            if (diasDeFolga >=1 && diasDeFolga <=7){
                return null;
            }
        }

        //Traz a diferença dos dias embarcados  para ser salvo na base
        Long diasEmbarcado = utilsDate.getDiasCorridos(dataEmbarque, dataDesembarque);
        if (diasEmbarcado >=1 && diasEmbarcado <= 14) {
            try {
                Embarque embarque = new Embarque();
                embarque.setFuncionario(funcionario);
                embarque.setDataEmbarque(dataEmbarque);
                embarque.setDataDesenbarque(dataDesembarque);
                embarque.setDiasMaximoEmbarcado(Math.toIntExact(diasEmbarcado));
                embarque.setDiasMinimoDeFolga(7);
                embarqueRepository.save(embarque);
                return embarque;
            } catch (Exception e) {
                return null;
            }
        } else{
            return null;
        }
    }

    /**
     * Realiza a alteração do desembarque do funcionário pelo ID do
     * embarque
     * @param idEmbarque
     * @param dataDesembarque
     * @return
     */
    public Optional<Embarque> desembarqueFuncionario (Integer idEmbarque, Date dataDesembarque) {
        try {
            Optional<Embarque> embarque = embarqueRepository.findById(idEmbarque);
            UtilsDate utilsDate = new UtilsDate();
            Long diasEmbarado = utilsDate.getDiasCorridos(embarque.get().getDataEmbarque(), dataDesembarque);
            // Trata a diferenca de dias decorrido
            if (diasEmbarado >=1 && diasEmbarado <= 14) {
                embarque.get().setDiasMaximoEmbarcado(Math.toIntExact(diasEmbarado));
                embarque.get().setDataDesenbarque(dataDesembarque);
                embarqueRepository.save(embarque.get());
                return embarque;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.print(e);
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
