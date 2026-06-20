package br.ufal.ic.p2.jackut.persistence;

import br.ufal.ic.p2.jackut.repository.ComunidadeRepository;
import br.ufal.ic.p2.jackut.repository.UsuarioRepository;

import java.io.*;

/**
 * Gerencia a persistencia do estado do Jackut em disco.
 *
 * <p>Encapsula toda a logica de serializacao/desserializacao e manipulacao do
 * arquivo de dados, isolando o {@code Sistema} dos detalhes de I/O. Persiste o
 * agregado {@link Dados} (usuarios e comunidades). Em caso de arquivo
 * inexistente ou erro de leitura, retorna um agregado vazio.</p>
 */
public class PersistenciaManager {

    /** Nome do arquivo onde os dados sao gravados. */
    private static final String ARQUIVO_DADOS = "jackut_dados.ser";

    private final String caminhoArquivo;

    /** Cria o gerenciador usando o arquivo de dados padrao. */
    public PersistenciaManager() {
        this(ARQUIVO_DADOS);
    }

    /**
     * Cria o gerenciador usando um caminho de arquivo especifico.
     *
     * @param caminhoArquivo caminho do arquivo de dados
     */
    public PersistenciaManager(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    /**
     * Salva o estado do sistema em disco.
     *
     * @param dados agregado a persistir
     */
    public void salvar(Dados dados) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            oos.writeObject(dados);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    /**
     * Carrega o estado do sistema do disco.
     *
     * @return agregado carregado, ou um agregado vazio se nao houver arquivo
     *         ou se ocorrer erro de leitura
     */
    public Dados carregar() {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            return vazio();
        }
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(arquivo))) {
            return (Dados) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
            return vazio();
        }
    }

    /** Apaga o arquivo de dados, se existir. */
    public void apagarArquivo() {
        File arquivo = new File(caminhoArquivo);
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }

    /** Cria um agregado de dados vazio. */
    private Dados vazio() {
        return new Dados(new UsuarioRepository(), new ComunidadeRepository());
    }
}
