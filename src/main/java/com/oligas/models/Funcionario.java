package com.oligas.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

/**
 * Classe Funcionario para registro de dados
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "FUNCIONARIO")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer idFuncionario;
    private String nomeFuncionario;
    private String funcaoFuncionario;
    private String empresaFuncionario;


    public Funcionario(String nomeFuncionario, String funcaoFuncionario, String empresaFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
        this.funcaoFuncionario = funcaoFuncionario;
        this.empresaFuncionario = empresaFuncionario;

    }
}
