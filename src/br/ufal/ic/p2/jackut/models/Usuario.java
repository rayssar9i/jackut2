package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.AmigoJaAdicionadoException;
import br.ufal.ic.p2.jackut.exceptions.AtributoNaoPreenchidoException;
import br.ufal.ic.p2.jackut.exceptions.ConvitePendenteException;
import br.ufal.ic.p2.jackut.exceptions.FuncaoInvalidaInimigoException;
import br.ufal.ic.p2.jackut.exceptions.SemMensagensException;
import br.ufal.ic.p2.jackut.exceptions.SemRecadosException;

import java.io.Serializable;
import java.util.*;

/**
 * Representa um usuario do Jackut.
 *
 * <p>Concentra o comportamento do dominio do usuario: perfil editavel, relacoes
 * de amizade (convites e amigos), relacoes de fa/idolo, paquera e inimizade,
 * as comunidades de que participa e as caixas de recados e de mensagens.
 * As colecoes internas sao protegidas, expondo-se apenas operacoes de alto
 * nivel.</p>
 */
public class Usuario implements Serializable {

    private static final long serialVersionUID = 2L;

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
    private final Queue<Recado> recados;

    /** Fila de mensagens de comunidades recebidas, em ordem de chegada (FIFO). */
    private final Queue<String> mensagens;

    /** Logins dos idolos deste usuario (de quem ele e fa). */
    private final Set<String> idolos;

    /** Logins dos fas deste usuario, em ordem de chegada. */
    private final Set<String> fas;

    /** Logins das paqueras deste usuario, em ordem de adicao (privado). */
    private final Set<String> paqueras;

    /** Logins dos inimigos declarados por este usuario. */
    private final Set<String> inimigos;

    /** Nomes das comunidades de que participa, em ordem de entrada. */
    private final Set<String> comunidades;

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
        this.mensagens = new LinkedList<>();
        this.idolos = new LinkedHashSet<>();
        this.fas = new LinkedHashSet<>();
        this.paqueras = new LinkedHashSet<>();
        this.inimigos = new LinkedHashSet<>();
        this.comunidades = new LinkedHashSet<>();
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
     * Retorna o nome de exibicao do usuario.
     *
     * @return nome
     */
    public String getNome() {
        return nome;
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

    // ----- Amizade -----

    /**
     * Registra um convite de amizade pendente para o login informado.
     *
     * @param loginAmigo login do destinatario do convite
     * @throws AmigoJaAdicionadoException se ja for amigo
     * @throws ConvitePendenteException   se ja houver convite pendente
     */
    public void enviarConvite(String loginAmigo) {
        if (amigos.contains(loginAmigo)) {
            throw new AmigoJaAdicionadoException();
        }
        if (convitesPendentes.contains(loginAmigo)) {
            throw new ConvitePendenteException();
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
     * Retorna a lista de amigos confirmados, em ordem de adicao. Imutavel.
     *
     * @return lista nao modificavel de logins de amigos
     */
    public List<String> getAmigos() {
        return Collections.unmodifiableList(amigos);
    }

    // ----- Recados -----

    /**
     * Adiciona um recado a caixa de entrada do usuario.
     *
     * @param recado recado recebido
     */
    public void receberRecado(Recado recado) {
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
        return recados.poll().getTexto();
    }

    /**
     * Remove todos os recados enviados pelo remetente informado.
     *
     * @param remetente login do remetente cujos recados serao descartados
     */
    public void removerRecadosDe(String remetente) {
        recados.removeIf(r -> remetente != null && remetente.equals(r.getRemetente()));
    }

    // ----- Mensagens de comunidade -----

    /**
     * Adiciona uma mensagem de comunidade a caixa de mensagens.
     *
     * @param mensagem texto da mensagem
     */
    public void receberMensagem(String mensagem) {
        mensagens.add(mensagem);
    }

    /**
     * Le e remove a mensagem mais antiga da caixa (ordem FIFO).
     *
     * @return texto da mensagem
     * @throws SemMensagensException se nao houver mensagens
     */
    public String lerMensagem() {
        if (mensagens.isEmpty()) {
            throw new SemMensagensException();
        }
        return mensagens.poll();
    }

    // ----- Fa / idolo -----

    /**
     * Registra que este usuario passou a ser fa do idolo informado.
     *
     * @param loginIdolo login do idolo
     */
    public void adicionarIdolo(String loginIdolo) {
        idolos.add(loginIdolo);
    }

    /**
     * Registra um fa deste usuario.
     *
     * @param loginFa login do fa
     */
    public void adicionarFa(String loginFa) {
        fas.add(loginFa);
    }

    /**
     * Indica se este usuario e fa do idolo informado.
     *
     * @param loginIdolo login do idolo
     * @return {@code true} se for fa
     */
    public boolean ehFa(String loginIdolo) {
        return idolos.contains(loginIdolo);
    }

    /**
     * Retorna os fas deste usuario, em ordem de chegada.
     *
     * @return lista de logins dos fas
     */
    public List<String> getFas() {
        return new ArrayList<>(fas);
    }

    // ----- Paquera -----

    /**
     * Adiciona uma paquera (informacao privada deste usuario).
     *
     * @param loginPaquera login da paquera
     */
    public void adicionarPaquera(String loginPaquera) {
        paqueras.add(loginPaquera);
    }

    /**
     * Indica se o login informado e paquera deste usuario.
     *
     * @param loginPaquera login a consultar
     * @return {@code true} se for paquera
     */
    public boolean ehPaquera(String loginPaquera) {
        return paqueras.contains(loginPaquera);
    }

    /**
     * Retorna as paqueras deste usuario, em ordem de adicao.
     *
     * @return lista de logins das paqueras
     */
    public List<String> getPaqueras() {
        return new ArrayList<>(paqueras);
    }

    // ----- Inimizade -----

    /**
     * Declara o login informado como inimigo deste usuario.
     *
     * @param loginInimigo login do inimigo
     */
    public void adicionarInimigo(String loginInimigo) {
        inimigos.add(loginInimigo);
    }

    /**
     * Indica se o login informado foi declarado inimigo por este usuario.
     *
     * @param loginInimigo login a consultar
     * @return {@code true} se for inimigo
     */
    public boolean temComoInimigo(String loginInimigo) {
        return inimigos.contains(loginInimigo);
    }

    /**
     * Valida que nao ha relacao de inimizade (em qualquer sentido) entre este
     * usuario e o alvo de uma interacao.
     *
     * @param alvo usuario alvo da interacao
     * @throws FuncaoInvalidaInimigoException se um for inimigo do outro
     */
    public void validarNaoInimigo(Usuario alvo) {
        if (inimigos.contains(alvo.getLogin()) || alvo.temComoInimigo(login)) {
            throw new FuncaoInvalidaInimigoException(alvo.getNome());
        }
    }

    // ----- Comunidades -----

    /**
     * Registra a entrada deste usuario em uma comunidade.
     *
     * @param nomeComunidade nome da comunidade
     */
    public void entrarComunidade(String nomeComunidade) {
        comunidades.add(nomeComunidade);
    }

    /**
     * Remove o registro de participacao em uma comunidade.
     *
     * @param nomeComunidade nome da comunidade
     */
    public void sairComunidade(String nomeComunidade) {
        comunidades.remove(nomeComunidade);
    }

    /**
     * Retorna as comunidades de que participa, em ordem de entrada.
     *
     * @return lista de nomes de comunidades
     */
    public List<String> getComunidades() {
        return new ArrayList<>(comunidades);
    }

    // ----- Remocao de conta -----

    /**
     * Remove todas as referencias ao login informado nas relacoes deste
     * usuario (amizade, convites, fa/idolo, paquera e inimizade).
     *
     * @param loginRemovido login do usuario que esta sendo removido do sistema
     */
    public void removerReferencias(String loginRemovido) {
        amigos.remove(loginRemovido);
        convitesPendentes.remove(loginRemovido);
        idolos.remove(loginRemovido);
        fas.remove(loginRemovido);
        paqueras.remove(loginRemovido);
        inimigos.remove(loginRemovido);
    }
}
