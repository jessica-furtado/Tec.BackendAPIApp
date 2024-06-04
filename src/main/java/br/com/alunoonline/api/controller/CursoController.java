package br.com.alunoonline.api.controller;

import br.com.alunoonline.api.model.Curso;
import br.com.alunoonline.api.model.Disciplina;
import br.com.alunoonline.api.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/curso")
public class CursoController {
    @Autowired
    CursoService cursoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Curso curso) {cursoService.create(curso);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Curso> findAll() {
        return cursoService.findALL();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Curso> findById(@PathVariable Long id) {
        return cursoService.findById(id);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Curso curso, @PathVariable Long id) {
        cursoService.update(id, curso);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByID(@PathVariable Long id) {
        cursoService.deleteById(id);
    }
}
