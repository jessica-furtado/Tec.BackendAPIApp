package br.com.alunoonline.api.service;

import br.com.alunoonline.api.dtos.CriarAlunoRequest;
import br.com.alunoonline.api.enums.FinanceiroStatusEnum;
import br.com.alunoonline.api.enums.MatriculaAlunoStatusEnum;
import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.model.Curso;
import br.com.alunoonline.api.model.FinanceiroAluno;
import br.com.alunoonline.api.repository.AlunoRepository;
import br.com.alunoonline.api.repository.CursoRepository;
import br.com.alunoonline.api.repository.FinanceiroAlunoRepository;
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

    @Autowired
    FinanceiroAlunoRepository financeiroAlunoRepository;

    @Autowired
    CursoRepository cursoRepository;

    public void create(CriarAlunoRequest criarAlunoRequest) {
       Curso curso = cursoRepository.findById(criarAlunoRequest.getCourseId())
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));

        Aluno savedStudent =  alunoRepository.save(
               new Aluno(
                       null,
                       criarAlunoRequest.getName(),
                       criarAlunoRequest.getEmail(),
                       curso
               )
       );

        createFinanceiroInformation (savedStudent, criarAlunoRequest);
    }  //Criar o item financeiro desse aluno salvo

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
    alunoUpdated.setCourse(aluno.getCourse());

    alunoRepository.save(alunoUpdated);
  }
  public void deleteById(Long id) {
        alunoRepository.deleteById(id);
  }

  public void createFinanceiroInformation(Aluno aluno, CriarAlunoRequest criarAlunoRequest) {
      FinanceiroAluno financeiroAluno = new FinanceiroAluno(
         null,
         aluno,
         criarAlunoRequest.getDiscount(),
         criarAlunoRequest.getDueDate(),
              FinanceiroStatusEnum.EM_DIA
      );

      financeiroAlunoRepository.save(financeiroAluno);
  }

}
