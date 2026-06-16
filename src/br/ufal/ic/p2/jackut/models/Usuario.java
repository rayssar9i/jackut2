package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.AtributoNaoPreenchidoException;
import br.ufal.ic.p2.jackut.exceptions.SemRecadosException;

import java.io.Serializable;
import java.util.*;

/**
 * Representa um usuario do Jackut.
 *
 * <p>Mantem dados de identificacao, perfil editavel, relacoes de amizade
 * (convites pendentes e amigos confirmados) e a caixa de recados. A classe
 * concentra o comportamento proprio do dominio do usuario, expondo operacoes
 * de alto nivel e protegendo suas colecoes internas.</p>
 */
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Atributo especial de perfil que mapeia diretamente para o nome. */
    private static final String ATRIBUTO_NOME = "nome";

    /** Login unico e imutavel do usuario. */
    private final String login;

    /** Senha de acesso do usuario. */
    private String senha;

    /** Nome de exibicao do usuario. */
    private String nome;

    /** Atributos livres do perfil, indexados por nome. */
    private final Map<String, String> perfil;

    /** Logins para os quais este usuario enviou convite de amizade. */
    private final Set<String> convitesPendentes;

    /** Logins dos amigos confirmados, em ordem de adicao. */
    private final List<String> amigos;

    /** Fila de recados recebidos, em ordem de chegada (FIFO). */
    private final Queue<String> recados;

    /**
     * Cria um novo usuario.
     *
     * @param login login unico do usuario
     * @param senha senha de acesso
     * @param nome  nome de exibicao
     */
    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.perfil = new LinkedHashMap<>();
        this.convitesPendentes = new LinkedHashSet<>();
        this.amigos = new ArrayList<>();
        this.recados = new LinkedList<>();
    }

    /**
     * Retorna o login do usuario.
     *
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Verifica se a senha informada confere com a do usuario.
     *
     * @param senha senha a verificar
     * @return {@code true} se a senha estiver correta
     */
    public boolean verificarSenha(String senha) {
        return this.senha.equals(senha);
    }

    /**
     * Retorna o valor de um atributo do perfil.
     * O atributo "nome" e especial e retorna o nome do usuario.
     *
     * @param atributo nome do atributo
     * @return valor do atributo
     * @throws AtributoNaoPreenchidoException se o atributo nao estiver preenchido
     */
    public String getAtributo(String atributo) {
        if (ATRIBUTO_NOME.equals(atributo)) {
            return nome;
        }
        if (!perfil.containsKey(atributo)) {
            throw new AtributoNaoPreenchidoException();
        }
        return perfil.get(atributo);
    }

    /**
     * Edita um atributo do perfil. O atributo "nome" altera o nome de exibicao;
     * qualquer outro e armazenado como atributo livre.
     *
     * @param atributo nome do atributo
     * @param valor    novo valor
     */
    public void editarPerfil(String atributo, String valor) {
        if (ATRIBUTO_NOME.equals(atributo)) {
            this.nome = valor;
        } else {
            perfil.put(atributo, valor);
        }
    }

    /**
     * Registra um convite de amizade pendente para o login informado.
     *
     * @param loginAmigo login do destinatario do convite
     * @throws br.ufal.ic.p2.jackut.exceptions.AmigoJaAdicionadoException se ja for amigo
     * @throws br.ufal.ic.p2.jackut.exceptions.ConvitePendenteException   se ja houver convite pendente
     */
    public void enviarConvite(String loginAmigo) {
        if (amigos.contains(loginAmigo)) {
            throw new br.ufal.ic.p2.jackut.exceptions.AmigoJaAdicionadoException();
        }
        if (convitesPendentes.contains(loginAmigo)) {
            throw new br.ufal.ic.p2.jackut.exceptions.ConvitePendenteException();
        }
        convitesPendentes.add(loginAmigo);
    }

    /**
     * Aceita (consome) um convite pendente e adiciona o login como amigo.
     *
     * @param loginAmigo login do amigo a confirmar
     */
    public void aceitarConvite(String loginAmigo) {
        convitesPendentes.remove(loginAmigo);
        if (!amigos.contains(loginAmigo)) {
            amigos.add(loginAmigo);
        }
    }

    /**
     * Indica se este usuario possui convite pendente enviado ao login informado.
     *
     * @param loginAmigo login a consultar
     * @return {@code true} se houver convite pendente para o login
     */
    public boolean temConvitePendentePara(String loginAmigo) {
        return convitesPendentes.contains(loginAmigo);
    }

    /**
     * Indica se o login informado e amigo confirmado deste usuario.
     *
     * @param loginAmigo login a consultar
     * @return {@code true} se for amigo
     */
    public boolean ehAmigo(String loginAmigo) {
        return amigos.contains(loginAmigo);
    }

    /**
     * Retorna a lista de amigos confirmados, em ordem de adicao.
     * A lista retornada e imutavel.
     *
     * @return lista nao modificavel de logins de amigos
     */
    public List<String> getAmigos() {
        return Collections.unmodifiableList(amigos);
    }

    /**
     * Adiciona um recado a caixa de entrada do usuario.
     *
     * @param recado texto do recado
     */
    public void receberRecado(String recado) {
        recados.add(recado);
    }

    /**
     * Le e remove o recado mais antigo da caixa (ordem FIFO).
     *
     * @return texto do recado
     * @throws SemRecadosException se nao houver recados
     */
    public String lerRecado() {
        if (recados.isEmpty()) {
            throw new SemRecadosException();
        }
        return recados.poll();
    }
}
