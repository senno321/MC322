/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322.materia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mc322.exceptions.ResourceDataException;
import mc322.usuario.Usuario;

/**
 * Gerencia o catálogo de matérias carregando informações de arquivos
 * e permitindo que matérias sejam adicionadas a usuários.
 */
public class GerenciadorDeMaterias {

    // Singleton: uma única instância da classe é criada e reaproveitada.
    private static final GerenciadorDeMaterias INSTANCE = new GerenciadorDeMaterias();

    /**
     * Mapeia código da matéria (ex: "MC322") para o objeto Materia correspondente.
     */
    private final Map<String, Materia> catalogoMaterias;

    private GerenciadorDeMaterias() {
        catalogoMaterias = new HashMap<>();
        carregarMateriasDosArquivos(); // Inicializa os dados a partir dos arquivos
    }

    // Método público para acesso à instância única (Singleton).
    public static GerenciadorDeMaterias getInstance() {
        return INSTANCE;
    }

    /**
     * Carrega as informações das matérias a partir de 4 arquivos de texto:
     * - Código das matérias
     * - Nome das matérias
     * - Nome dos professores
     * - Créditos das matérias
     * Os arquivos devem estar em src/main/resources/mc322/txt/
     * 
     * @throws ResourceDataException em caso de falha na leitura ou inconsistência
     *                               dos dados
     */
    private void carregarMateriasDosArquivos() {
        // Caminhos relativos dos arquivos de dados dentro de resources
        String caminhoArquivoCodigos = "mc322/txt/Codigo_Materias.txt";
        String caminhoArquivoNomes = "mc322/txt/Nome_Materias.txt";
        String caminhoArquivoProfessores = "mc322/txt/Nome_Professores.txt";
        String caminhoArquivoCreditos = "mc322/txt/Credito_Materias.txt";

        List<String> codigos = lerArquivoDeTexto(caminhoArquivoCodigos);
        List<String> nomes = lerArquivoDeTexto(caminhoArquivoNomes);
        List<String> professores = lerArquivoDeTexto(caminhoArquivoProfessores);
        List<String> creditosStr = lerArquivoDeTexto(caminhoArquivoCreditos);

        List<Integer> creditos = new ArrayList<>();
        for (String linha : creditosStr) {
            try {
                creditos.add(Integer.parseInt(linha.trim()));
            } catch (NumberFormatException e) {
                throw new ResourceDataException("Erro ao converter crédito para inteiro: " + linha, e);
            }
        }

        // Verifica se todas as listas têm o mesmo tamanho
        if (codigos.size() == nomes.size() && codigos.size() == professores.size()
                && codigos.size() == creditos.size()) {
            for (int i = 0; i < codigos.size(); i++) {
                String codigo = codigos.get(i);
                String nome = nomes.get(i);
                String professor = professores.get(i);
                int credito = creditos.get(i);

                Materia novaMateria = new Materia(codigo, nome, professor, credito);
                catalogoMaterias.put(codigo, novaMateria);
            }
            System.out.println("Total de " + catalogoMaterias.size() + " matérias carregadas no catálogo.");
        } else {
            throw new ResourceDataException(
                    "Erro: número de linhas nos arquivos de códigos, nomes, professores ou créditos não coincide.");
        }
    }

    /**
     * Lê um arquivo de texto do resources e retorna uma lista com cada linha.
     * 
     * @param caminho Caminho relativo dentro de resources
     * @return Lista de linhas do arquivo
     * @throws ResourceDataException se o arquivo não for encontrado ou erro na
     *                               leitura
     */
    private List<String> lerArquivoDeTexto(String caminho) {
        List<String> linhas = new ArrayList<>();
        try (
                InputStream is = getClass().getClassLoader().getResourceAsStream(caminho);
                BufferedReader br = is != null ? new BufferedReader(new InputStreamReader(is)) : null) {
            if (br == null) {
                throw new ResourceDataException("Arquivo não encontrado: " + caminho);
            }
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (IOException e) {
            throw new ResourceDataException("Erro ao ler o arquivo: " + caminho, e);
        }
        return linhas;
    }

    /**
     * Retorna a matéria associada a um código, se ela existir.
     *
     * @param codigo código da matéria
     * @return objeto Materia correspondente, ou null se não encontrado
     */
    public Materia buscarMateriaPorCodigo(String codigo) {
        return catalogoMaterias.get(codigo);
    }

    /**
     * Retorna o mapa completo contendo todas as matérias cadastradas.
     *
     * @return catálogo de matérias
     */
    public Map<String, Materia> getCatalogoMaterias() {
        return catalogoMaterias;
    }

    public List<Materia> getTodasMaterias() {
        return new ArrayList<>(catalogoMaterias.values());
    }

    /**
     * Adiciona uma matéria a um usuário com base no código fornecido.
     * Verifica se o código existe no catálogo antes de adicionar.
     *
     * @param usuario       usuário que receberá a matéria
     * @param codigoMateria código da matéria desejada
     */
    public void criarMateria(Usuario usuario, String codigoMateria) {
        Materia materia = catalogoMaterias.get(codigoMateria);

        if (materia != null) {
            usuario.adicionarMateria(materia);
            System.out.println("Matéria adicionada ao usuário: " + materia.getNome());
        } else {
            System.err.println("Código não encontrado no catálogo: " + codigoMateria);
        }
    }
}