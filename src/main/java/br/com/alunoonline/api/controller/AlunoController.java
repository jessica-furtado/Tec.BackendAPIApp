package br.com.alunoonline.api.controller;

import br.com.alunoonline.api.dtos.CriarAlunoRequest;
import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.service.AlunoService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController  //Anotação de informação para arquitetura
@RequestMapping("/aluno")  //Mapeamento do endereço
public class AlunoController {

    @Autowired
    AlunoService alunoService;

    @PostMapping  //Anotação para informar da criação do objeto verbos HTTP
    @ResponseStatus(HttpStatus.CREATED) //Anotação da resposta da requisição
    public void create(@RequestBody CriarAlunoRequest criarAlunoRequest) {
        alunoService.create(criarAlunoRequest);
    }  //Conversão do Jason em um objeto já modelado em Java
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Aluno> findAll() {
        return alunoService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Aluno> findById(@PathVariable Long id) {
        return alunoService.findById(id);
    } //PathVariable anotação que converte o HTTP em Java
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Aluno aluno, @PathVariable Long id) {
        alunoService.update(id, aluno);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByID(@PathVariable Long id) {
        alunoService.deleteById(id);
    }

}
