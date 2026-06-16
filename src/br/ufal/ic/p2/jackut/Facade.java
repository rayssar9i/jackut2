package br.ufal.ic.p2.jackut;

/**
 * Fachada do Jackut: unico ponto de entrada usado pelos testes de aceitacao
 * (EasyAccept). Cada metodo delega diretamente ao {@link Sistema}, sem
 * implementar regras de negocio.
 */
public class Facade {

    private final Sistema sistema = new Sistema();

    /** Cria a fachada e inicializa o sistema subjacente. */
    public Facade() {
    }

    /** Zera o estado do sistema. */
    public void zerarSistema() {
        sistema.zerarSistema();
    }

    /** Persiste o estado e encerra o sistema. */
    public void encerrarSistema() {
        sistema.encerrarSistema();
    }

    /**
     * Cria um usuario.
     *
     * @param login login unico
     * @param senha senha de acesso
     * @param nome  nome de exibicao
     */
    public void criarUsuario(String login, String senha, String nome) {
        sistema.criarUsuario(login, senha, nome);
    }

    /**
     * Abre uma sessao e retorna seu id.
     *
     * @param login login do usuario
     * @param senha senha do usuario
     * @return id da sessao
     */
    public String abrirSessao(String login, String senha) {
        return sistema.abrirSessao(login, senha);
    }

    /**
     * Retorna um atributo de perfil de um usuario.
     *
     * @param login    login do usuario
     * @param atributo nome do atributo
     * @return valor do atributo
     */
    public String getAtributoUsuario(String login, String atributo) {
        return sistema.getAtributoUsuario(login, atributo);
    }

    /**
     * Edita um atributo de perfil do usuario logado.
     *
     * @param id       id da sessao
     * @param atributo nome do atributo
     * @param valor    novo valor
     */
    public void editarPerfil(String id, String atributo, String valor) {
        sistema.editarPerfil(id, atributo, valor);
    }

    /**
     * Adiciona um amigo.
     *
     * @param id    id da sessao do solicitante
     * @param amigo login do amigo
     */
    public void adicionarAmigo(String id, String amigo) {
        sistema.adicionarAmigo(id, amigo);
    }

    /**
     * Indica se dois usuarios sao amigos.
     *
     * @param login login de referencia
     * @param amigo login a verificar
     * @return "true" ou "false"
     */
    public String ehAmigo(String login, String amigo) {
        return sistema.ehAmigo(login, amigo);
    }

    /**
     * Retorna os amigos de um usuario.
     *
     * @param login login do usuario
     * @return string no formato {@code {a,b,c}}
     */
    public String getAmigos(String login) {
        return sistema.getAmigos(login);
    }

    /**
     * Envia um recado.
     *
     * @param id           id da sessao do remetente
     * @param destinatario login do destinatario
     * @param recado       texto do recado
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
}
