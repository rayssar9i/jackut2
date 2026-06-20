package br.ufal.ic.p2.jackut.repository;

import br.ufal.ic.p2.jackut.exceptions.ComunidadeJaExisteException;
import br.ufal.ic.p2.jackut.exceptions.ComunidadeNaoExisteException;
import br.ufal.ic.p2.jackut.models.Comunidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Repositorio responsavel por armazenar e recuperar comunidades.
 *
 * <p>Encapsula a colecao de comunidades e a regra de unicidade de nome,
 * que e a chave primaria de uma comunidade. E serializavel para permitir a
 * persistencia do conjunto completo de comunidades.</p>
 */
public class ComunidadeRepository implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Comunidades indexadas por nome, preservando a ordem de criacao. */
    private Map<String, Comunidade> comunidades;

    /** Cria um repositorio vazio. */
    public ComunidadeRepository() {
        this.comunidades = new LinkedHashMap<>();
    }

    /**
     * Adiciona uma nova comunidade.
     *
     * @param comunidade comunidade a adicionar
     * @throws ComunidadeJaExisteException se ja existir comunidade com o mesmo nome
     */
    public void adicionar(Comunidade comunidade) {
        if (comunidades.containsKey(comunidade.getNome())) {
            throw new ComunidadeJaExisteException();
        }
        comunidades.put(comunidade.getNome(), comunidade);
    }

    /**
     * Busca uma comunidade pelo nome, exigindo que ela exista.
     *
     * @param nome nome da comunidade
     * @return comunidade encontrada
     * @throws ComunidadeNaoExisteException se nao houver comunidade com o nome
     */
    public Comunidade buscar(String nome) {
        Comunidade comunidade = comunidades.get(nome);
        if (comunidade == null) {
            throw new ComunidadeNaoExisteException();
        }
        return comunidade;
    }

    /**
     * Indica se existe comunidade com o nome informado.
     *
     * @param nome nome a consultar
     * @return {@code true} se existir
     */
    public boolean existe(String nome) {
        return comunidades.containsKey(nome);
    }

    /**
     * Remove a comunidade do nome informado, se presente.
     *
     * @param nome nome da comunidade a remover
     */
    public void remover(String nome) {
        comunidades.remove(nome);
    }

    /**
     * Retorna uma copia da colecao de comunidades, segura para iteracao mesmo
     * durante remocoes.
     *
     * @return lista de todas as comunidades
     */
    public Collection<Comunidade> todas() {
        return new ArrayList<>(comunidades.values());
    }

    /** Remove todas as comunidades do repositorio. */
    public void limpar() {
        comunidades = new LinkedHashMap<>();
    }
}
