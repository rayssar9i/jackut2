package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.UsuarioJaNaComunidadeException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Comunidade do Jackut.
 *
 * <p>Identificada de forma unica pelo nome, possui uma descricao, um dono (o
 * usuario que a criou) e a lista ordenada de membros. O dono entra
 * automaticamente como primeiro membro no momento da criacao.</p>
 */
public class Comunidade implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Nome unico da comunidade (chave primaria). */
    private final String nome;

    /** Descricao livre da comunidade. */
    private final String descricao;

    /** Login do dono (criador) da comunidade. */
    private final String dono;

    /** Membros da comunidade, em ordem de entrada (o dono e o primeiro). */
    private final List<String> membros;

    /**
     * Cria uma comunidade com o dono ja registrado como primeiro membro.
     *
     * @param nome      nome unico da comunidade
     * @param descricao descricao da comunidade
     * @param dono      login do criador
     */
    public Comunidade(String nome, String descricao, String dono) {
        this.nome = nome;
        this.descricao = descricao;
        this.dono = dono;
        this.membros = new ArrayList<>();
        this.membros.add(dono);
    }

    /**
     * Retorna o nome da comunidade.
     *
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a descricao da comunidade.
     *
     * @return descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o login do dono da comunidade.
     *
     * @return login do dono
     */
    public String getDono() {
        return dono;
    }

    /**
     * Retorna a lista de membros, em ordem de entrada. A lista e imutavel.
     *
     * @return lista nao modificavel de logins dos membros
     */
    public List<String> getMembros() {
        return Collections.unmodifiableList(membros);
    }

    /**
     * Indica se o login informado e membro desta comunidade.
     *
     * @param login login a consultar
     * @return {@code true} se for membro
     */
    public boolean temMembro(String login) {
        return membros.contains(login);
    }

    /**
     * Adiciona um membro a comunidade.
     *
     * @param login login do novo membro
     * @throws UsuarioJaNaComunidadeException se o usuario ja for membro
     */
    public void adicionarMembro(String login) {
        if (membros.contains(login)) {
            throw new UsuarioJaNaComunidadeException();
        }
        membros.add(login);
    }

    /**
     * Remove um membro da comunidade, se presente.
     *
     * @param login login do membro a remover
     */
    public void removerMembro(String login) {
        membros.remove(login);
    }
}
