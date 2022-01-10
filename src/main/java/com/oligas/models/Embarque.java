package com.oligas.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
/**
 * Classe de embarque onde são registraos embarques
 */
public class Embarque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer idEmbarque;
    @ManyToOne
    @JoinColumn(name = "funcionario_id_funcionario")
    private Funcionario funcionario;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataEmbarque;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataDesenbarque;
    private Integer diasMaximoEmbarcado;
    private Integer diasMinimoDeFolga;


}
