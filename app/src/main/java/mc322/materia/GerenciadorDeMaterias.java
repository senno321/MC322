/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322.materia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mc322.usuario.Usuario;

/**
 * Gerencia o catálogo de matérias carregando informações de arquivos
 * e permitindo que matérias sejam adicionadas a usuários.
 */
public class GerenciadorDeMaterias {
    private static final GerenciadorDeMaterias INSTANCE = new GerenciadorDeMaterias();
    /**
     * Mapeia código da matéria para o objeto Materia correspondente.
     */
    public Map<String, Materia> catalogoMaterias;

    /**
     * Construtor que inicializa o catálogo e carrega as matérias dos arquivos.
     */
    public GerenciadorDeMaterias() {
        catalogoMaterias = new HashMap<>();
        carregarMateriasDosArquivos();
    }

    public static GerenciadorDeMaterias getInstance() {
        return INSTANCE;
    }

    /**
     * Carrega as informações das matérias a partir de arquivos externos
     * e popula o catálogo. Lê códigos, nomes, professores e créditos.
     * Imprime erros se arquivos estiverem faltando ou incorretos.
     */
    private void carregarMateriasDosArquivos() {
        String caminhoArquivoCodigos = "MC322/txt/Código_Matérias.txt";
        String caminhoArquivoNomes = "MC322/txt/Nome_Matérias.txt";
        String caminhoArquivoProfessores = "MC322/txt/Nome_Professores.txt";
        String caminhoArquivoCreditos = "MC322/txt/Crédito_Matérias.txt";

        List<String> codigos = new ArrayList<>();
        List<String> nomes = new ArrayList<>();
        List<String> professores = new ArrayList<>();
        List<Integer> creditos = new ArrayList<>();

        // Carrega códigos das matérias
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivoCodigos))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                codigos.add(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de códigos: " + e.getMessage());
            System.err.println("Verifique se '" + caminhoArquivoCodigos + "' está no local correto.");
        }

        // Carrega nomes das matérias
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivoNomes))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                nomes.add(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de nomes: " + e.getMessage());
            System.err.println("Verifique se '" + caminhoArquivoNomes + "' está no local correto.");
        }

        // Carrega nomes dos professores
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivoProfessores))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                professores.add(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de professores: " + e.getMessage());
            System.err.println("Verifique se '" + caminhoArquivoProfessores + "' está no local correto.");
        }

        // Carrega créditos das matérias
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivoCreditos))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                creditos.add(Integer.parseInt(linha.trim()));
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de créditos: " + e.getMessage());
            System.err.println("Verifique se '" + caminhoArquivoCreditos + "' está no local correto.");
        }

        // Valida se as listas carregadas têm o mesmo tamanho
        if (codigos.size() == nomes.size() && codigos.size() == professores.size() && codigos.size() == creditos.size()) {
            for (int i = 0; i < codigos.size(); i++) {

                String codigo = codigos.get(i);
                String nome = nomes.get(i);
                String professor = professores.get(i);
                int credito = creditos.get(i);

                Materia novaMateria = new Materia(nome, professor, credito);

                catalogoMaterias.put(codigo, novaMateria);
            }

            System.out.println("Total de " + catalogoMaterias.size() + " matérias carregadas no catálogo.");

        } else {
            System.err.println("Erro: O número de linhas nos arquivos de códigos, nomes, professores ou créditos não coincide.");
        }

    }

    /**
     * Busca uma matéria pelo seu código no catálogo.
     *
     * @param codigo código da matéria
     * @return objeto Materia correspondente, ou null se não encontrado
     */
    public Materia buscarMateriaPorCodigo(String codigo) {
        return catalogoMaterias.get(codigo);
    }

    /**
     * Retorna o catálogo completo de matérias.
     *
     * @return mapa código -> matéria
     */
    public Map<String, Materia> getCatalogoMaterias() {
        return catalogoMaterias;
    }

    /**
     * Adiciona uma matéria ao usuário, caso o código exista no catálogo.
     *
     * @param usuario       usuário que receberá a matéria
     * @param codigoMateria código da matéria a ser adicionada
     */
    public void criarMateria(Usuario usuario, String codigoMateria) {
        Materia materia = catalogoMaterias.get(codigoMateria);

        if (materia != null) {
            usuario.adicionarMateria(materia);
            System.out.println("Matéria adicionada ao usuário: " + materia.getNome());
        } else
            System.err.println("Código não encontrado no catálogo: " + codigoMateria);
    }
}
