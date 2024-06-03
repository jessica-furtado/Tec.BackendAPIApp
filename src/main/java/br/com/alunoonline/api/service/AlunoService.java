package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.repository.AlunoRepository;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service //Anotação informando que aqui é a classe onde vamos inserir as regras de negócio (um service)
public class AlunoService {

    @Autowired  //injenção de depedências do repository
    AlunoRepository alunoRepository;

    public void create(Aluno aluno) {
        alunoRepository.save(aluno);
    }  //prirmeiro método de criação

    public List <Aluno> findAll() {
        return alunoRepository.findAll();
    } //método para buscar todos os objetos criados

    public Optional<Aluno> findById(Long id) {
        return alunoRepository.findById(id);
    } //método para buscar um objeto específico
    public void update(Long id, Aluno aluno) {
        Optional<Aluno> alunoFromDb = findById(id);
    if (alunoFromDb.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado no banco de dados!");
    } //Método para atualizar cadastro de objeto específico
        //FromDB - buscanco o aluno no banco de dados

    Aluno alunoUpdated = alunoFromDb.get(); //atualização do objeto da memória no banco de dados

    alunoUpdated.setName(aluno.getName());
    alunoUpdated.setEmail(aluno.getEmail());

    alunoRepository.save(alunoUpdated);
  }
  public void deleteById(Long id) {
        alunoRepository.deleteById(id);
  }

}
