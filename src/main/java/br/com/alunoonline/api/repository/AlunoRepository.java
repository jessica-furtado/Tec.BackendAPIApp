package br.com.alunoonline.api.repository;

import br.com.alunoonline.api.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Anotação para informar que essa interface é um repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}

//Extensão com a biblioteca JPA responsável pela ponte entre o JAVA e o Banco de Dados (SQL).
//Necessário informar o objeto que será usado no repository e o tipo da chave primária que será usada no Model e na tabela.