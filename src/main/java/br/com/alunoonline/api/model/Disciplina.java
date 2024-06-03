package br.com.alunoonline.api.model;

import br.com.alunoonline.api.service.ProfessorService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity

public class Disciplina implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long Id;
    private String name;

    @ManyToOne  //Anotação informando muitos para um (Muitas disciplinas para um professor)
    @JoinColumn(name = "professor_id") //Anotação para criação de chave estrangeira
    private Professor professor;  //Atributo Professor é do tipo professor
}
