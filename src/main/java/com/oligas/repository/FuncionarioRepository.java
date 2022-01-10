package com.oligas.repository;
import com.oligas.models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
  @Query (value = "select * from funcionario where  nome_funcionario like %?1%", nativeQuery = true)
  List<Funcionario> findFuncionarioPorNome(String nomeFuncionario);
}
