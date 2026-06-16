package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.LoginInvalidoException;
import br.ufal.ic.p2.jackut.exceptions.LoginSenhaInvalidosException;
import br.ufal.ic.p2.jackut.exceptions.SenhaInvalidaException;
import br.ufal.ic.p2.jackut.models.Sessao;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.persistence.PersistenciaManager;
import br.ufal.ic.p2.jackut.repository.UsuarioRepository;
import br.ufal.ic.p2.jackut.service.AmizadeService;
import br.ufal.ic.p2.jackut.service.RecadoService;
import br.ufal.ic.p2.jackut.service.SessaoManager;

/**
 * Coordenador central do Jackut.
 *
 * <p>O {@code Sistema} nao implementa as regras de negocio diretamente: ele
 * orquestra os colaboradores especializados ({@link UsuarioRepository},
 * {@link SessaoManager}, {@link AmizadeService}, {@link RecadoService} e
 * {@link PersistenciaManager}), delegando cada responsabilidade a quem de
 * direito. Suas validacoes proprias limitam-se as credenciais de entrada.</p>
 */
public class Sistema {

    private UsuarioRepository usuarios;
    private final SessaoManager sessoes;
    private final PersistenciaManager persistencia;
    private AmizadeService amizades;
    private RecadoService recados;

    /**
     * Inicializa o sistema, carregando os dados persistidos e montando os
     * servicos sobre o repositorio carregado.
     */
    public Sistema() {
        this.persistencia = new PersistenciaManager();
        this.sessoes = new SessaoManager();
        this.usuarios = persistencia.carregar();
        montarServicos();
    }

    /** (Re)constroi os servicos que dependem do repositorio atual. */
    private void montarServicos() {
        this.amizades = new AmizadeService(usuarios);
        this.recados = new RecadoService(usuarios);
    }

    /**
     * Zera o estado do sistema: remove usuarios, sessoes e o arquivo de dados.
     */
    public void zerarSistema() {
        usuarios = new UsuarioRepository();
        sessoes.limpar();
        persistencia.apagarArquivo();
        montarServicos();
    }

    /**
     * Cria um novo usuario apos validar login e senha.
     *
     * @param login login unico
     * @param senha senha de acesso
     * @param nome  nome de exibicao
     * @throws LoginInvalidoException se o login for nulo ou vazio
     * @throws SenhaInvalidaException se a senha for nula ou vazia
     */
    public void criarUsuario(String login, String senha, String nome) {
        if (login == null || login.trim().isEmpty()) {
            throw new LoginInvalidoException();
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new SenhaInvalidaException();
        }
        usuarios.adicionar(new Usuario(login, senha, nome));
    }

    /**
     * Abre uma sessao para o usuario autenticado.
     *
     * @param login login do usuario
     * @param senha senha do usuario
     * @return id da sessao aberta
     * @throws LoginSenhaInvalidosException se as credenciais forem invalidas
     */
    public String abrirSessao(String login, String senha) {
        if (login == null || login.isEmpty() || senha == null || senha.isEmpty()) {
            throw new LoginSenhaInvalidosException();
        }
        Usuario usuario = usuarios.buscarOuNulo(login);
        if (usuario == null || !usuario.verificarSenha(senha)) {
            throw new LoginSenhaInvalidosException();
        }
        return sessoes.abrir(usuario);
    }

    /**
     * Retorna o valor de um atributo de perfil de um usuario.
     *
     * @param login    login do usuario
     * @param atributo nome do atributo
     * @return valor do atributo
     */
    public String getAtributoUsuario(String login, String atributo) {
        return usuarios.buscar(login).getAtributo(atributo);
    }

    /**
     * Edita um atributo de perfil do usuario logado na sessao.
     *
     * @param id       id da sessao
     * @param atributo nome do atributo
     * @param valor    novo valor
     */
    public void editarPerfil(String id, String atributo, String valor) {
        sessoes.getSessao(id).getUsuario().editarPerfil(atributo, valor);
    }

    /**
     * Adiciona um amigo a partir da sessao do solicitante.
     *
     * @param id    id da sessao do solicitante
     * @param amigo login do amigo a adicionar
     */
    public void adicionarAmigo(String id, String amigo) {
        Usuario solicitante = sessoes.getSessao(id).getUsuario();
        amizades.adicionarAmigo(solicitante, amigo);
    }

    /**
     * Indica se dois usuarios sao amigos.
     *
     * @param login login de referencia
     * @param amigo login a verificar
     * @return "true" ou "false"
     */
    public String ehAmigo(String login, String amigo) {
        return String.valueOf(amizades.ehAmigo(login, amigo));
    }

    /**
     * Retorna os amigos de um usuario no formato {@code {a,b,c}}.
     *
     * @param login login do usuario
     * @return string formatada com os amigos
     */
    public String getAmigos(String login) {
        return amizades.getAmigos(login);
    }

    /**
     * Envia um recado de um usuario logado para um destinatario.
     *
     * @param id           id da sessao do remetente
     * @param destinatario login do destinatario
     * @param recado       texto do recado
     */
    public void enviarRecado(String id, String destinatario, String recado) {
        Usuario remetente = sessoes.getSessao(id).getUsuario();
        recados.enviarRecado(remetente, destinatario, recado);
    }

    /**
     * Le o proximo recado do usuario logado na sessao.
     *
     * @param id id da sessao
     * @return texto do recado
     */
    public String lerRecado(String id) {
        Usuario usuario = sessoes.getSessao(id).getUsuario();
        return recados.lerRecado(usuario);
    }

    /** Persiste o estado atual do sistema em disco. */
    public void encerrarSistema() {
        persistencia.salvar(usuarios);
    }
}
