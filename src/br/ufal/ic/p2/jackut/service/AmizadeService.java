package br.ufal.ic.p2.jackut.service;

import br.ufal.ic.p2.jackut.exceptions.AutoAmizadeException;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.repository.UsuarioRepository;

/**
 * Servico que coordena as regras de amizade entre usuarios.
 *
 * <p>Concentra a logica de envio e aceitacao de convites, bem como as
 * consultas de amizade. Trabalha sobre o {@link UsuarioRepository} para
 * localizar os usuarios envolvidos, mantendo o {@code Sistema} livre dessa
 * responsabilidade.</p>
 */
public class AmizadeService {

    private final UsuarioRepository usuarios;

    /**
     * Cria o servico de amizade.
     *
     * @param usuarios repositorio de usuarios
     */
    public AmizadeService(UsuarioRepository usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * Adiciona uma amizade entre o usuario solicitante e o amigo informado.
     *
     * <p>Se o amigo ja tiver enviado um convite ao solicitante, a amizade e
     * confirmada para ambos. Caso contrario, um novo convite e registrado.</p>
     *
     * @param solicitante usuario que esta adicionando o amigo
     * @param loginAmigo  login do usuario a ser adicionado
     * @throws AutoAmizadeException se o usuario tentar adicionar a si mesmo
     */
    public void adicionarAmigo(Usuario solicitante, String loginAmigo) {
        if (solicitante.getLogin().equals(loginAmigo)) {
            throw new AutoAmizadeException();
        }
        Usuario amigo = usuarios.buscar(loginAmigo);

        if (amigo.temConvitePendentePara(solicitante.getLogin())) {
            amigo.aceitarConvite(solicitante.getLogin());
            solicitante.aceitarConvite(loginAmigo);
        } else {
            solicitante.enviarConvite(loginAmigo);
        }
    }

    /**
     * Indica se dois usuarios sao amigos.
     *
     * @param login login do usuario de referencia
     * @param amigo login a verificar
     * @return {@code true} se forem amigos; {@code false} se nao forem ou se
     *         o usuario de referencia nao existir
     */
    public boolean ehAmigo(String login, String amigo) {
        Usuario usuario = usuarios.buscarOuNulo(login);
        if (usuario == null) {
            return false;
        }
        return usuario.ehAmigo(amigo);
    }

    /**
     * Retorna a lista de amigos de um usuario no formato {@code {a,b,c}}.
     *
     * @param login login do usuario
     * @return string formatada com os amigos
     */
    public String getAmigos(String login) {
        Usuario usuario = usuarios.buscar(login);
        return "{" + String.join(",", usuario.getAmigos()) + "}";
    }
}
