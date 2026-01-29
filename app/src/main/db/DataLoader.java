package db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import materia.Materia;
import materia.GerenciadorDeMaterias;
import db.MateriaRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private MateriaRepository materiaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (materiaRepository.count() == 0) {
            System.out.println("Banco de dados vazio. Carregando matérias dos arquivos de texto...");
            carregarMaterias();
        } else {
            System.out.println("Banco de dados já populado. Nenhuma ação necessária.");
        }
    }

    private void carregarMaterias() {
        List<Materia> materias = GerenciadorDeMaterias.getInstance().getTodasMaterias();
        
        for(Materia materia : materias){
            materiaRepository.save(materia);
        }

        System.out.println(materiaRepository.count() + " matérias foram carregadas no banco de dados.");
    }
}