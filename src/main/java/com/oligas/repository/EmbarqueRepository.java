package com.oligas.repository;

import com.oligas.models.Embarque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Repository
public interface EmbarqueRepository extends JpaRepository<Embarque, Integer> {
    //Traz a lista de embarque pelo id do funcionario.
    @Query(value = "select  id,funcionario_id_funcionario,data_embarque,data_desenbarque,dias_maximo_embarcado, " +
            "dias_minimo_de_folga " +
            "from embarque where funcionario_id_funcionario=?",nativeQuery = true)
    List<Embarque> findEmbarqueIdFuncionario(Integer id);

    //Traz a lista de embarque pelas datas de embarque num intervalo.
    @Query(value = "select id,funcionario_id_funcionario,data_embarque,data_desenbarque,dias_maximo_embarcado, " +
            "dias_minimo_de_folga " +
            "from embarque where funcionario_id_funcionario=? and data_embarque >= ? and data_embarque <= ? ",nativeQuery = true)
    List<Embarque> findEmbarqueByAll(Integer id,  Date dataInicial, Date dataFinal);

    //Traz o ultimo embarque aberto pelo id do funcionario.
    @Query(value ="select * from (select max(id) as id from embarque where funcionario_id_funcionario=? and data_desembarque is null) X "+
                  "inner join embarque emb on emb.id=X.id" ,nativeQuery = true)
    Embarque findUltimoEmbarqueAberto(Integer id);

    //Traz o ultimo desambarque do funcionário
    @Query(value ="select * from (select max(id) as id from embarque where funcionario_id_funcionario=? and data_desembarque is not null) X "+
            "inner join embarque emb on emb.id=X.id" ,nativeQuery = true)
    Embarque findUltimoEmbarqueFechado(Integer id);



}
