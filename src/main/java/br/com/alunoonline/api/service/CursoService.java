package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Curso;
import br.com.alunoonline.api.model.Disciplina;
import br.com.alunoonline.api.repository.CursoRepository;
import br.com.alunoonline.api.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {
    @Autowired
    CursoRepository cursoRepository;

    public void create(Curso curso) {cursoRepository.save(curso);
    }

    public List<Curso> findALL() {
        return cursoRepository.findAll();
    }
    public Optional<Curso> findById(Long id) {
        return cursoRepository.findById(id);
    }
    public void update(Long id, Curso curso) {
        Optional<Curso > cursoFromDb = findById(id);
        if (cursoFromDb.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado no banco de dados!");
        }

        Curso cursoUpdated = cursoFromDb.get();

        cursoUpdated.setName(curso.getName());
        cursoUpdated.setType(curso.getType());
        cursoUpdated.setMonthlyCost(curso.getMonthlyCost());

        cursoRepository.save(cursoUpdated);
    }
    public void deleteById(Long id) {
        cursoRepository.deleteById(id);
    }

}
