package br.com.alunoonline.api.service;

import br.com.alunoonline.api.dtos.AtualizarNotasRequest;
import br.com.alunoonline.api.dtos.DisciplinasAlunoResponse;
import br.com.alunoonline.api.dtos.HistoricoAlunoResponse;
import br.com.alunoonline.api.enums.MatriculaAlunoStatusEnum;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatriculaAlunoService {

    public static final double GRADE_AVG_TO_APPROVE = 7.0;
    //Constante criada para definir a média de aprovação. Deve ser escrita em letra maiúscula e separada por underline
    @Autowired
    MatriculaAlunoRepository matriculaAlunoRepository;

    public void create(MatriculaAluno matriculaAluno) {
        matriculaAluno.setStatus(MatriculaAlunoStatusEnum.MATRICULADO);
        matriculaAlunoRepository.save(matriculaAluno);
    }

    public void updateGrades(Long matriculaAlunoId, AtualizarNotasRequest atualizarNotasRequest) {
        MatriculaAluno matriculaAluno = matriculaAlunoRepository.findById(matriculaAlunoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));
        updateStudentGrades(matriculaAluno, atualizarNotasRequest);
        updateStudentStatus(matriculaAluno);

        matriculaAlunoRepository.save(matriculaAluno);
    }
    //Para atualizar as notas  é necessário o ID da matrícula e as notas 1 e nota 2 (DTO)
    //Para variárias Optional é possível usar o orElseThrow - Se não for verdadeiro, lance uma exceção

    public void updateStudentGrades(MatriculaAluno matriculaAluno, AtualizarNotasRequest atualizarNotasRequest) {
        if (atualizarNotasRequest.getGrade1() != null) {
            matriculaAluno.setGrade1(atualizarNotasRequest.getGrade1());
        }

        if (atualizarNotasRequest.getGrade2() != null) {
            matriculaAluno.setGrade2(atualizarNotasRequest.getGrade2());
        }
    }
    //Método de atualização da nota1 ou/e nota2, SE foram diferentes de nulo.

    public void updateStudentStatus(MatriculaAluno matriculaAluno) {
        Double nota1 = matriculaAluno.getGrade1();
        Double nota2 = matriculaAluno.getGrade2();

        if (nota1 != null && nota2 != null) {
            double average = (nota1 + nota2) / 2;
            matriculaAluno.setStatus(average >= GRADE_AVG_TO_APPROVE ? MatriculaAlunoStatusEnum.APROVADO : MatriculaAlunoStatusEnum.REPROVADO);
        }

        //Operador Ternário, onde é feito um IF e Else em uma mesma linha.
        // Na lógica do método implementado acima é questionado: A média calculada desse aluno é maior igual do que a média de aprovação?
        //Se sim, Aprovado, Se não (Else), Reprovado.
    }

    //Método de atualização dos Status dos alunos que está no Enum
    //Métodos separados para cada ação deixam o código limpo. Single Responsability (S OLID).

    public void updateStatusToBreak(Long matriculaAlunoId) {
        MatriculaAluno matriculaAluno = matriculaAlunoRepository.findById(matriculaAlunoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada"));
        if (!MatriculaAlunoStatusEnum.MATRICULADO.equals(matriculaAluno.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Só é possível trancar uma matrícula com o status Matriculado");
        }
        changeStatus(matriculaAluno, MatriculaAlunoStatusEnum.TRANCADO);
    }

    //1º Método que traz a condição para a não atualização do status para trancado (Negaçao de "Matriculado" = Status da matrícula do aluno
    //2º Puxa o método criado abaxo que altera o status de matrícula aluno, setando o método em "TRANCADO"

    public void changeStatus(MatriculaAluno matriculaAluno, MatriculaAlunoStatusEnum matriculaAlunoStatusEnum) {
        matriculaAluno.setStatus(matriculaAlunoStatusEnum);
        matriculaAlunoRepository.save(matriculaAluno);
    }

    //Método criado para alterar o Status da matrícula aluno

    public HistoricoAlunoResponse getAcademicTranscript(Long alunoId) {
        List<MatriculaAluno> matriculasDoAluno = matriculaAlunoRepository.findByStudentId(alunoId);

        if (matriculasDoAluno.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Esse aluno não possui matrículas!");
        }
        HistoricoAlunoResponse historico = new HistoricoAlunoResponse();
        historico.setStudentName(matriculasDoAluno.get(0).getStudent().getName());
        historico.setStudentemail(matriculasDoAluno.get(0).getStudent().getEmail());

        List<DisciplinasAlunoResponse> disciplinasList = new ArrayList<>();

        for (MatriculaAluno matricula : matriculasDoAluno) {
            DisciplinasAlunoResponse disciplinasAlunoResponse = new DisciplinasAlunoResponse();
            disciplinasAlunoResponse.setSubjectName(matricula.getSubject().getName());
            disciplinasAlunoResponse.setProfessorName(matricula.getSubject().getProfessor().getName());
            disciplinasAlunoResponse.setGrade1(matricula.getGrade1());
            disciplinasAlunoResponse.setGrade2(matricula.getGrade2());

            if(matricula.getGrade1() != null && matricula.getGrade2() != null) {
                disciplinasAlunoResponse.setAverage(((matricula.getGrade1())+matricula.getGrade2())/2.0);
            }

            else {
                disciplinasAlunoResponse.setAverage(null);
            }

            disciplinasAlunoResponse.setStatus(matricula.getStatus());
            disciplinasList.add(disciplinasAlunoResponse);
        }

        historico.setStudentSubjectsRespondeList(disciplinasList);

        return historico;

    }

}
