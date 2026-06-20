package br.ufal.ic.p2.jackut;

/**
 * Fachada do Jackut usada pelo EasyAccept.
 *
 * <p>Expoe a interface esperada pelos scripts de aceitacao e delega cada
 * chamada ao {@link Sistema}. Nao contem logica de negocio: e apenas o ponto
 * de entrada (padrao Facade) que isola o framework de testes da arquitetura
 * interna.</p>
 */
public class Facade {

    private final Sistema sistema = new Sistema();

    // ----- US1 -----

    /** Zera todos os dados do sistema. */
    public void zerarSistema() {
        sistema.zerarSistema();
    }

    /**
     * Cria um usuario.
     *
     * @param login login
     * @param senha senha
     * @param nome  nome
     */
    public void criarUsuario(String login, String senha, String nome) {
        sistema.criarUsuario(login, senha, nome);
    }

    /**
     * Abre uma sessao.
     *
     * @param login login
     * @param senha senha
     * @return id da sessao
     */
    public String abrirSessao(String login, String senha) {
        return sistema.abrirSessao(login, senha);
    }

    /**
     * Retorna um atributo do perfil de um usuario.
     *
     * @param login    login
     * @param atributo atributo
     * @return valor
     */
    public String getAtributoUsuario(String login, String atributo) {
        return sistema.getAtributoUsuario(login, atributo);
    }

    // ----- US2 -----

    /**
     * Edita o perfil do usuario logado.
     *
     * @param id       id da sessao
     * @param atributo atributo
     * @param valor    valor
     */
    public void editarPerfil(String id, String atributo, String valor) {
        sistema.editarPerfil(id, atributo, valor);
    }

    // ----- US3 -----

    /**
     * Adiciona um amigo.
     *
     * @param id    id da sessao
     * @param amigo login do amigo
     */
    public void adicionarAmigo(String id, String amigo) {
        sistema.adicionarAmigo(id, amigo);
    }

    /**
     * Indica se dois usuarios sao amigos.
     *
     * @param login login
     * @param amigo amigo
     * @return "true" ou "false"
     */
    public String ehAmigo(String login, String amigo) {
        return sistema.ehAmigo(login, amigo);
    }

    /**
     * Retorna os amigos de um usuario.
     *
     * @param login login
     * @return amigos no formato {@code {a,b}}
     */
    public String getAmigos(String login) {
        return sistema.getAmigos(login);
    }

    // ----- US4 -----

    /**
     * Envia um recado.
     *
     * @param id           id da sessao
     * @param destinatario login do destinatario
     * @param recado       texto
     */
    public void enviarRecado(String id, String destinatario, String recado) {
        sistema.enviarRecado(id, destinatario, recado);
    }

    /**
     * Le o proximo recado do usuario logado.
     *
     * @param id id da sessao
     * @return texto do recado
     */
    public String lerRecado(String id) {
        return sistema.lerRecado(id);
    }

    // ----- US5 / US6 -----

    /**
     * Cria uma comunidade.
     *
     * @param sessao    id da sessao do dono
     * @param nome      nome
     * @param descricao descricao
     */
    public void criarComunidade(String sessao, String nome, String descricao) {
        sistema.criarComunidade(sessao, nome, descricao);
    }

    /**
     * Retorna a descricao de uma comunidade.
     *
     * @param nome nome
     * @return descricao
     */
    public String getDescricaoComunidade(String nome) {
        return sistema.getDescricaoComunidade(nome);
    }

    /**
     * Retorna o dono de uma comunidade.
     *
     * @param nome nome
     * @return login do dono
     */
    public String getDonoComunidade(String nome) {
        return sistema.getDonoComunidade(nome);
    }

    /**
     * Retorna os membros de uma comunidade.
     *
     * @param nome nome
     * @return membros no formato {@code {a,b}}
     */
    public String getMembrosComunidade(String nome) {
        return sistema.getMembrosComunidade(nome);
    }

    /**
     * Adiciona o usuario logado a uma comunidade.
     *
     * @param sessao id da sessao
     * @param nome   nome da comunidade
     */
    public void adicionarComunidade(String sessao, String nome) {
        sistema.adicionarComunidade(sessao, nome);
    }

    /**
     * Retorna as comunidades de um usuario.
     *
     * @param login login
     * @return comunidades no formato {@code {a,b}}
     */
    public String getComunidades(String login) {
        return sistema.getComunidades(login);
    }

    // ----- US7 -----

    /**
     * Envia uma mensagem a uma comunidade.
     *
     * @param sessao     id da sessao
     * @param comunidade nome da comunidade
     * @param mensagem   texto
     */
    public void enviarMensagem(String sessao, String comunidade, String mensagem) {
        sistema.enviarMensagem(sessao, comunidade, mensagem);
    }

    /**
     * Le a proxima mensagem de comunidade do usuario logado.
     *
     * @param sessao id da sessao
     * @return texto da mensagem
     */
    public String lerMensagem(String sessao) {
        return sistema.lerMensagem(sessao);
    }

    // ----- US8 -----

    /**
     * Adiciona um idolo.
     *
     * @param sessao id da sessao
     * @param idolo  login do idolo
     */
    public void adicionarIdolo(String sessao, String idolo) {
        sistema.adicionarIdolo(sessao, idolo);
    }

    /**
     * Indica se um usuario e fa de um idolo.
     *
     * @param login login
     * @param idolo idolo
     * @return "true" ou "false"
     */
    public String ehFa(String login, String idolo) {
        return sistema.ehFa(login, idolo);
    }

    /**
     * Retorna os fas de um usuario.
     *
     * @param login login
     * @return fas no formato {@code {a,b}}
     */
    public String getFas(String login) {
        return sistema.getFas(login);
    }

    /**
     * Adiciona uma paquera.
     *
     * @param sessao  id da sessao
     * @param paquera login da paquera
     */
    public void adicionarPaquera(String sessao, String paquera) {
        sistema.adicionarPaquera(sessao, paquera);
    }

    /**
     * Indica se um login e paquera do usuario logado.
     *
     * @param sessao  id da sessao
     * @param paquera login a verificar
     * @return "true" ou "false"
     */
    public String ehPaquera(String sessao, String paquera) {
        return sistema.ehPaquera(sessao, paquera);
    }

    /**
     * Retorna as paqueras do usuario logado.
     *
     * @param sessao id da sessao
     * @return paqueras no formato {@code {a,b}}
     */
    public String getPaqueras(String sessao) {
        return sistema.getPaqueras(sessao);
    }

    /**
     * Declara um inimigo.
     *
     * @param sessao  id da sessao
     * @param inimigo login do inimigo
     */
    public void adicionarInimigo(String sessao, String inimigo) {
        sistema.adicionarInimigo(sessao, inimigo);
    }

    // ----- US9 -----

    /**
     * Remove a conta do usuario logado.
     *
     * @param sessao id da sessao
     */
    public void removerUsuario(String sessao) {
        sistema.removerUsuario(sessao);
    }

    /** Encerra o sistema, persistindo os dados. */
    public void encerrarSistema() {
        sistema.encerrarSistema();
    }
}
