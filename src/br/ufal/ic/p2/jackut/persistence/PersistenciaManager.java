package br.ufal.ic.p2.jackut.persistence;

import br.ufal.ic.p2.jackut.repository.UsuarioRepository;

import java.io.*;

/**
 * Gerencia a persistencia do repositorio de usuarios em disco.
 *
 * <p>Encapsula toda a logica de serializacao/desserializacao e manipulacao do
 * arquivo de dados, isolando o {@code Sistema} dos detalhes de I/O. Em caso de
 * arquivo inexistente ou erro de leitura, retorna um repositorio vazio.</p>
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
     * Salva o repositorio de usuarios em disco.
     *
     * @param repositorio repositorio a persistir
     */
    public void salvar(UsuarioRepository repositorio) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            oos.writeObject(repositorio);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    /**
     * Carrega o repositorio de usuarios do disco.
     *
     * @return repositorio carregado, ou um repositorio vazio se nao houver
     *         arquivo ou se ocorrer erro de leitura
     */
    public UsuarioRepository carregar() {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            return new UsuarioRepository();
        }
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(arquivo))) {
            return (UsuarioRepository) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
            return new UsuarioRepository();
        }
    }

    /** Apaga o arquivo de dados, se existir. */
    public void apagarArquivo() {
        File arquivo = new File(caminhoArquivo);
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }
}
