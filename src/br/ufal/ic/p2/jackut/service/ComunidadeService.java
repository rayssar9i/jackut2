package br.ufal.ic.p2.jackut.service;

import br.ufal.ic.p2.jackut.models.Comunidade;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.repository.ComunidadeRepository;
import br.ufal.ic.p2.jackut.repository.UsuarioRepository;

import java.util.List;

/**
 * Servico que coordena as regras de comunidades.
 *
 * <p>Trata a criacao de comunidades, a entrada de membros e as consultas de
 * descricao, dono, membros e comunidades de um usuario, mantendo sincronizadas
 * a lista de membros da comunidade e a lista de comunidades do usuario.</p>
 */
public class ComunidadeService {

    private final UsuarioRepository usuarios;
    private final ComunidadeRepository comunidades;

    /**
     * Cria o servico de comunidades.
     *
     * @param usuarios    repositorio de usuarios
     * @param comunidades repositorio de comunidades
     */
    public ComunidadeService(UsuarioRepository usuarios, ComunidadeRepository comunidades) {
        this.usuarios = usuarios;
        this.comunidades = comunidades;
    }

    /**
     * Cria uma comunidade tendo o solicitante como dono e primeiro membro.
     *
     * @param dono      usuario criador
     * @param nome      nome unico da comunidade
     * @param descricao descricao da comunidade
     */
    public void criarComunidade(Usuario dono, String nome, String descricao) {
        Comunidade comunidade = new Comunidade(nome, descricao, dono.getLogin());
        comunidades.adicionar(comunidade);
        dono.entrarComunidade(nome);
    }

    /**
     * Retorna a descricao de uma comunidade.
     *
     * @param nome nome da comunidade
     * @return descricao
     */
    public String getDescricao(String nome) {
        return comunidades.buscar(nome).getDescricao();
    }

    /**
     * Retorna o login do dono de uma comunidade.
     *
     * @param nome nome da comunidade
     * @return login do dono
     */
    public String getDono(String nome) {
        return comunidades.buscar(nome).getDono();
    }

    /**
     * Retorna os membros de uma comunidade no formato {@code {a,b,c}}.
     *
     * @param nome nome da comunidade
     * @return string formatada com os membros
     */
    public String getMembros(String nome) {
        return formatar(comunidades.buscar(nome).getMembros());
    }

    /**
     * Adiciona um usuario como membro de uma comunidade.
     *
     * @param usuario usuario que entra na comunidade
     * @param nome    nome da comunidade
     */
    public void adicionarComunidade(Usuario usuario, String nome) {
        Comunidade comunidade = comunidades.buscar(nome);
        comunidade.adicionarMembro(usuario.getLogin());
        usuario.entrarComunidade(nome);
    }

    /**
     * Retorna as comunidades de um usuario no formato {@code {a,b,c}}.
     *
     * @param login login do usuario
     * @return string formatada com as comunidades
     */
    public String getComunidades(String login) {
        Usuario usuario = usuarios.buscar(login);
        return formatar(usuario.getComunidades());
    }

    private String formatar(List<String> itens) {
        return "{" + String.join(",", itens) + "}";
    }
}
