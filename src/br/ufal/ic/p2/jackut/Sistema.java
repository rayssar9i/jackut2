package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.LoginInvalidoException;
import br.ufal.ic.p2.jackut.exceptions.LoginSenhaInvalidosException;
import br.ufal.ic.p2.jackut.exceptions.SenhaInvalidaException;
import br.ufal.ic.p2.jackut.exceptions.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.persistence.Dados;
import br.ufal.ic.p2.jackut.persistence.PersistenciaManager;
import br.ufal.ic.p2.jackut.repository.ComunidadeRepository;
import br.ufal.ic.p2.jackut.repository.UsuarioRepository;
import br.ufal.ic.p2.jackut.service.AmizadeService;
import br.ufal.ic.p2.jackut.service.ComunidadeService;
import br.ufal.ic.p2.jackut.service.MensagemService;
import br.ufal.ic.p2.jackut.service.RecadoService;
import br.ufal.ic.p2.jackut.service.RelacionamentoService;
import br.ufal.ic.p2.jackut.service.RemocaoService;
import br.ufal.ic.p2.jackut.service.SessaoManager;

/**
 * Orquestrador central do Jackut.
 *
 * <p>Coordena os repositorios, o gerenciador de sessoes, a persistencia e os
 * servicos de dominio, delegando a eles toda a logica de negocio. Mantem-se
 * enxuto: cada operacao apenas resolve a sessao/usuario envolvido e encaminha
 * a chamada ao servico ou repositorio responsavel.</p>
 */
public class Sistema {

    private UsuarioRepository usuarios;
    private ComunidadeRepository comunidades;
    private final SessaoManager sessoes;
    private final PersistenciaManager persistencia;

    private AmizadeService amizadeService;
    private RecadoService recadoService;
    private ComunidadeService comunidadeService;
    private MensagemService mensagemService;
    private RelacionamentoService relacionamentoService;
    private RemocaoService remocaoService;

    /** Cria o sistema, carregando o estado persistido (se houver). */
    public Sistema() {
        this.persistencia = new PersistenciaManager();
        this.sessoes = new SessaoManager();
        Dados dados = persistencia.carregar();
        this.usuarios = dados.getUsuarios();
        this.comunidades = dados.getComunidades();
        montarServicos();
    }

    /** (Re)constroi os servicos sobre os repositorios atuais. */
    private void montarServicos() {
        this.amizadeService = new AmizadeService(usuarios);
        this.recadoService = new RecadoService(usuarios);
        this.comunidadeService = new ComunidadeService(usuarios, comunidades);
        this.mensagemService = new MensagemService(usuarios, comunidades);
        this.relacionamentoService = new RelacionamentoService(usuarios);
        this.remocaoService = new RemocaoService(usuarios, comunidades);
    }

    // ===================== US1: conta, sessao, atributos =====================

    /** Apaga todos os dados do sistema (usuarios, comunidades e sessoes). */
    public void zerarSistema() {
        this.usuarios = new UsuarioRepository();
        this.comunidades = new ComunidadeRepository();
        sessoes.limpar();
        persistencia.apagarArquivo();
        montarServicos();
    }

    /**
     * Cria um novo usuario.
     *
     * @param login login unico
     * @param senha senha de acesso
     * @param nome  nome de exibicao
     * @throws LoginInvalidoException se o login for nulo ou vazio
     * @throws SenhaInvalidaException se a senha for nula ou vazia
     */
    public void criarUsuario(String login, String senha, String nome) {
        if (login == null || login.isEmpty()) {
            throw new LoginInvalidoException();
        }
        if (senha == null || senha.isEmpty()) {
            throw new SenhaInvalidaException();
        }
        usuarios.adicionar(new Usuario(login, senha, nome));
    }

    /**
     * Abre uma sessao para o usuario, validando login e senha.
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
     * Retorna um atributo do perfil de um usuario.
     *
     * @param login    login do usuario
     * @param atributo nome do atributo
     * @return valor do atributo
     */
    public String getAtributoUsuario(String login, String atributo) {
        return usuarios.buscar(login).getAtributo(atributo);
    }

    // ===================== US2: edicao de perfil =====================

    /**
     * Edita um atributo do perfil do usuario logado.
     *
     * @param idSessao id da sessao
     * @param atributo nome do atributo
     * @param valor    novo valor
     */
    public void editarPerfil(String idSessao, String atributo, String valor) {
        sessoes.getSessao(idSessao).getUsuario().editarPerfil(atributo, valor);
    }

    // ===================== US3: amizade =====================

    /**
     * Adiciona um amigo a partir da sessao do solicitante.
     *
     * @param idSessao id da sessao do solicitante
     * @param amigo    login do amigo
     */
    public void adicionarAmigo(String idSessao, String amigo) {
        amizadeService.adicionarAmigo(sessoes.getSessao(idSessao).getUsuario(), amigo);
    }

    /**
     * Indica se dois usuarios sao amigos.
     *
     * @param login login de referencia
     * @param amigo login a verificar
     * @return "true" ou "false"
     */
    public String ehAmigo(String login, String amigo) {
        return String.valueOf(amizadeService.ehAmigo(login, amigo));
    }

    /**
     * Retorna os amigos de um usuario no formato {@code {a,b,c}}.
     *
     * @param login login do usuario
     * @return string formatada
     */
    public String getAmigos(String login) {
        return amizadeService.getAmigos(login);
    }

    // ===================== US4: recados =====================

    /**
     * Envia um recado para um destinatario.
     *
     * @param idSessao     id da sessao do remetente
     * @param destinatario login do destinatario
     * @param recado       texto do recado
     */
    public void enviarRecado(String idSessao, String destinatario, String recado) {
        recadoService.enviarRecado(sessoes.getSessao(idSessao).getUsuario(), destinatario, recado);
    }

    /**
     * Le o proximo recado do usuario logado.
     *
     * @param idSessao id da sessao
     * @return texto do recado
     */
    public String lerRecado(String idSessao) {
        return recadoService.lerRecado(sessoes.getSessao(idSessao).getUsuario());
    }

    // ===================== US5/US6: comunidades =====================

    /**
     * Cria uma comunidade tendo o usuario logado como dono.
     *
     * @param idSessao  id da sessao do dono
     * @param nome      nome da comunidade
     * @param descricao descricao da comunidade
     */
    public void criarComunidade(String idSessao, String nome, String descricao) {
        Usuario dono = sessoes.getSessao(idSessao).getUsuario();
        comunidadeService.criarComunidade(dono, nome, descricao);
    }

    /**
     * Retorna a descricao de uma comunidade.
     *
     * @param nome nome da comunidade
     * @return descricao
     */
    public String getDescricaoComunidade(String nome) {
        return comunidadeService.getDescricao(nome);
    }

    /**
     * Retorna o dono de uma comunidade.
     *
     * @param nome nome da comunidade
     * @return login do dono
     */
    public String getDonoComunidade(String nome) {
        return comunidadeService.getDono(nome);
    }

    /**
     * Retorna os membros de uma comunidade no formato {@code {a,b,c}}.
     *
     * @param nome nome da comunidade
     * @return string formatada
     */
    public String getMembrosComunidade(String nome) {
        return comunidadeService.getMembros(nome);
    }

    /**
     * Adiciona o usuario logado como membro de uma comunidade.
     *
     * @param idSessao id da sessao
     * @param nome     nome da comunidade
     */
    public void adicionarComunidade(String idSessao, String nome) {
        Usuario usuario = sessoes.getSessao(idSessao).getUsuario();
        comunidadeService.adicionarComunidade(usuario, nome);
    }

    /**
     * Retorna as comunidades de um usuario no formato {@code {a,b,c}}.
     *
     * @param login login do usuario
     * @return string formatada
     */
    public String getComunidades(String login) {
        return comunidadeService.getComunidades(login);
    }

    // ===================== US7: mensagens de comunidade =====================

    /**
     * Envia uma mensagem a todos os membros de uma comunidade.
     *
     * @param idSessao   id da sessao do remetente
     * @param comunidade nome da comunidade
     * @param mensagem   texto da mensagem
     */
    public void enviarMensagem(String idSessao, String comunidade, String mensagem) {
        sessoes.getSessao(idSessao);
        mensagemService.enviarMensagem(comunidade, mensagem);
    }

    /**
     * Le a proxima mensagem de comunidade do usuario logado.
     *
     * @param idSessao id da sessao
     * @return texto da mensagem
     */
    public String lerMensagem(String idSessao) {
        Usuario usuario = sessoes.getSessao(idSessao).getUsuario();
        return mensagemService.lerMensagem(usuario);
    }

    // ===================== US8: fa/idolo, paquera, inimigo =====================

    /**
     * Adiciona um idolo, tornando o usuario logado seu fa.
     *
     * @param idSessao id da sessao
     * @param idolo    login do idolo
     */
    public void adicionarIdolo(String idSessao, String idolo) {
        Usuario fa = sessoes.getSessao(idSessao).getUsuario();
        relacionamentoService.adicionarIdolo(fa, idolo);
    }

    /**
     * Indica se um usuario e fa de um idolo.
     *
     * @param login login do possivel fa
     * @param idolo login do idolo
     * @return "true" ou "false"
     */
    public String ehFa(String login, String idolo) {
        return String.valueOf(relacionamentoService.ehFa(login, idolo));
    }

    /**
     * Retorna os fas de um usuario no formato {@code {a,b,c}}.
     *
     * @param login login do usuario
     * @return string formatada
     */
    public String getFas(String login) {
        return relacionamentoService.getFas(login);
    }

    /**
     * Adiciona uma paquera ao usuario logado.
     *
     * @param idSessao id da sessao
     * @param paquera  login da paquera
     */
    public void adicionarPaquera(String idSessao, String paquera) {
        Usuario usuario = sessoes.getSessao(idSessao).getUsuario();
        relacionamentoService.adicionarPaquera(usuario, paquera);
    }

    /**
     * Indica se um login e paquera do usuario logado.
     *
     * @param idSessao id da sessao
     * @param paquera  login a verificar
     * @return "true" ou "false"
     */
    public String ehPaquera(String idSessao, String paquera) {
        Usuario usuario = sessoes.getSessao(idSessao).getUsuario();
        return String.valueOf(relacionamentoService.ehPaquera(usuario, paquera));
    }

    /**
     * Retorna as paqueras do usuario logado no formato {@code {a,b,c}}.
     *
     * @param idSessao id da sessao
     * @return string formatada
     */
    public String getPaqueras(String idSessao) {
        Usuario usuario = sessoes.getSessao(idSessao).getUsuario();
        return relacionamentoService.getPaqueras(usuario);
    }

    /**
     * Declara um usuario como inimigo do usuario logado.
     *
     * @param idSessao id da sessao
     * @param inimigo  login do inimigo
     */
    public void adicionarInimigo(String idSessao, String inimigo) {
        Usuario usuario = sessoes.getSessao(idSessao).getUsuario();
        relacionamentoService.adicionarInimigo(usuario, inimigo);
    }

    // ===================== US9: remocao de conta =====================

    /**
     * Remove a conta do usuario logado e todas as suas informacoes.
     *
     * @param idSessao id da sessao
     * @throws UsuarioNaoCadastradoException se o usuario ja tiver sido removido
     */
    public void removerUsuario(String idSessao) {
        Usuario alvo = sessoes.getSessao(idSessao).getUsuario();
        if (!usuarios.existe(alvo.getLogin())) {
            throw new UsuarioNaoCadastradoException();
        }
        remocaoService.remover(alvo);
    }

    /** Persiste o estado atual do sistema em disco. */
    public void encerrarSistema() {
        persistencia.salvar(new Dados(usuarios, comunidades));
    }
}
