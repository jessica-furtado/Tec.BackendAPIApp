

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaRepository disciplinaRepository;

    public void create(Disciplina disciplina) {
        disciplinaRepository.save(disciplina);
    }

    public List<Disciplina> findALL() {
        return disciplinaRepository.findAll();
    }
    public Optional<Disciplina> findById(Long id) {
        return disciplinaRepository.findById(id);
    }
    public void update(Long id, Disciplina disciplina) {
        Optional<Disciplina > disciplinaFromDb = findById(id);
        if (disciplinaFromDb.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada no banco de dados!");
        }

        Disciplina disciplinaUpdated = disciplinaFromDb.get();

        disciplinaUpdated.setName(disciplina.getName());
        disciplinaUpdated.setProfessor(disciplina.getProfessor());

        disciplinaRepository.save(disciplinaUpdated);
    }
    public void deleteById(Long id) {
        disciplinaRepository.deleteById(id);
    }

}