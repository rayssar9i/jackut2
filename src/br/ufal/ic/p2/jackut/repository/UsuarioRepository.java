package br.ufal.ic.p2.jackut.repository;

import br.ufal.ic.p2.jackut.exceptions.ContaJaExisteException;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.models.Usuario;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Repositorio responsavel por armazenar e recuperar usuarios.
 *
 * <p>Encapsula a colecao de usuarios e as regras de unicidade de login,
 * isolando o acesso aos dados do restante do sistema. E serializavel para
 * permitir a persistencia do conjunto completo de usuarios.</p>
 */
public class UsuarioRepository implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Usuarios indexados por login, preservando a ordem de insercao. */
    private Map<String, Usuario> usuarios;

    /** Cria um repositorio vazio. */
    public UsuarioRepository() {
        this.usuarios = new LinkedHashMap<>();
    }

    /**
     * Adiciona um novo usuario ao repositorio.
     *
     * @param usuario usuario a adicionar
     * @throws ContaJaExisteException se ja existir usuario com o mesmo login
     */
    public void adicionar(Usuario usuario) {
        if (usuarios.containsKey(usuario.getLogin())) {
            throw new ContaJaExisteException();
        }
        usuarios.put(usuario.getLogin(), usuario);
    }

    /**
     * Busca um usuario pelo login, exigindo que ele exista.
     *
     * @param login login do usuario
     * @return usuario encontrado
     * @throws UsuarioNaoCadastradoException se nao houver usuario com o login
     */
    public Usuario buscar(String login) {
        Usuario usuario = usuarios.get(login);
        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        }
        return usuario;
    }

    /**
     * Retorna o usuario do login informado, ou {@code null} se nao existir.
     *
     * @param login login do usuario
     * @return usuario ou {@code null}
     */
    public Usuario buscarOuNulo(String login) {
        return usuarios.get(login);
    }

    /**
     * Indica se existe usuario com o login informado.
     *
     * @param login login a consultar
     * @return {@code true} se existir
     */
    public boolean existe(String login) {
        return usuarios.containsKey(login);
    }

    /** Remove todos os usuarios do repositorio. */
    public void limpar() {
        usuarios = new LinkedHashMap<>();
    }
}
