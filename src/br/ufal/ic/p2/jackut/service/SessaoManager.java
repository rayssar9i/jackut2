package br.ufal.ic.p2.jackut.service;

import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.models.Sessao;
import br.ufal.ic.p2.jackut.models.Usuario;

import java.util.HashMap;
import java.util.Map;

/**
 * Gerencia o ciclo de vida das sessoes de usuario.
 *
 * <p>Responsavel por abrir novas sessoes, gerar identificadores unicos e
 * recuperar sessoes existentes. As sessoes sao transientes (nao persistidas)
 * e sao reiniciadas quando o sistema e zerado.</p>
 */
public class SessaoManager {

    /** Sessoes ativas indexadas pelo id. */
    private final Map<String, Sessao> sessoes;

    /** Contador usado para compor identificadores unicos de sessao. */
    private int contador;

    /** Cria um gerenciador de sessoes vazio. */
    public SessaoManager() {
        this.sessoes = new HashMap<>();
        this.contador = 0;
    }

    /**
     * Abre uma nova sessao para o usuario e retorna seu identificador.
     *
     * @param usuario usuario autenticado
     * @return id da sessao criada
     */
    public String abrir(Usuario usuario) {
        String id = usuario.getLogin() + "_" + (++contador);
        sessoes.put(id, new Sessao(id, usuario));
        return id;
    }

    /**
     * Recupera a sessao do id informado.
     *
     * @param id identificador da sessao
     * @return sessao correspondente
     * @throws UsuarioNaoCadastradoException se o id for invalido ou inexistente
     */
    public Sessao getSessao(String id) {
        if (id == null || id.isEmpty()) {
            throw new UsuarioNaoCadastradoException();
        }
        Sessao sessao = sessoes.get(id);
        if (sessao == null) {
            throw new UsuarioNaoCadastradoException();
        }
        return sessao;
    }

    /** Encerra todas as sessoes e reinicia o contador. */
    public void limpar() {
        sessoes.clear();
        contador = 0;
    }
}
