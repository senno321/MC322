package mc322.materia;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mc322.usuario.Usuario;

public class GerenciadorDeMaterias {
    public Map<String, Materia> catalogoMaterias; 

    public GerenciadorDeMaterias() {
        catalogoMaterias = new HashMap<>();
        carregarMateriasDosArquivos();
    }

    private void carregarMateriasDosArquivos() {
        String caminhoArquivoCodigos = "MC322/txt/Código_Matérias.txt";
        String caminhoArquivoNomes = "MC322/txt/Nome_Matérias.txt";
        String caminhoArquivoProfessores = "MC322/txt/Nome_Professores.txt";
        String caminhoArquivoCreditos = "MC322/txt/Crédito_Matérias.txt";

        List<String> codigos = new ArrayList<>();
        List<String> nomes = new ArrayList<>();
        List<String> professores = new ArrayList<>();
        List<Integer> creditos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivoCodigos))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                codigos.add(linha);
            }
        } 
        catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de códigos: " + e.getMessage());
            System.err.println("Verifique se '" + caminhoArquivoCodigos + "' está no local correto.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivoNomes))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                nomes.add(linha);
            }
        } 
        catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de nomes: " + e.getMessage());
            System.err.println("Verifique se '" + caminhoArquivoNomes + "' está no local correto.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivoProfessores))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                professores.add(linha);
            }
        } 
        catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de professores: " + e.getMessage());
            System.err.println("Verifique se '" + caminhoArquivoProfessores + "' está no local correto.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivoCreditos))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                codigos.add(linha);
            }
        } 
        catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de creditos: " + e.getMessage());
            System.err.println("Verifique se '" + caminhoArquivoCreditos + "' está no local correto.");
        }

        if (codigos.size() == nomes.size() && codigos.size() == professores.size()) {
            for (int i = 0; i < codigos.size(); i++) {
                
                String codigo = codigos.get(i);
                String nome = nomes.get(i);
                String professor = professores.get(i);
                int credito = creditos.get(i);

                Materia novaMateria = new Materia(nome, professor, credito);

                catalogoMaterias.put(codigo, novaMateria);
            }

            System.out.println("Total de " + catalogoMaterias.size() + " matérias carregadas no catálogo.");

        } 
        else {
            System.err.println("Erro: O número de linhas nos arquivos de códigos, nomes, professores ou créditos");
        }

    }

    public Materia buscarMateriaPorCodigo(String codigo) {
        return catalogoMaterias.get(codigo);
    }

    // opcional - caso precise acessar o catálogo 
    public Map<String, Materia> getCatalogoMaterias() {
        return catalogoMaterias;
    }

    public void criarMateria(Usuario usuario, String nome, String professor, int creditos) {
        Materia materia = new Materia(nome, professor, creditos);
        usuario.adicionarMateria(materia);
    }
}
