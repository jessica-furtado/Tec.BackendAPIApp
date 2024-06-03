package br.com.alunoonline.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor //Métodos construtores com argumentos
@NoArgsConstructor // Métodos construtores sem necessidade de argumentos
@Data //Gets and Sets
@Entity //Anotação para informar ao banco de dados que essa classe corresponde a uma entidade, tabela

public class Aluno implements Serializable {

    //Serializable - Serialização de dados

    @Id //Anotação informando que esse atributo é o ID da tabela Aluno
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Geração de valores com estratégia do tipo identity
    private Long id;
    private String name;
    private String email;

}
