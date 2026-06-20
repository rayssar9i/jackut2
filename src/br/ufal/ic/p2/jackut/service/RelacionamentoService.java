package br.ufal.ic.p2.jackut.service;

import br.ufal.ic.p2.jackut.exceptions.AutoFaException;
import br.ufal.ic.p2.jackut.exceptions.AutoInimigoException;
import br.ufal.ic.p2.jackut.exceptions.AutoPaqueraException;
import br.ufal.ic.p2.jackut.exceptions.IdoloJaAdicionadoException;
import br.ufal.ic.p2.jackut.exceptions.InimigoJaAdicionadoException;
import br.ufal.ic.p2.jackut.exceptions.PaqueraJaAdicionadaException;
import br.ufal.ic.p2.jackut.models.Recado;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.repository.UsuarioRepository;

import java.util.List;

/**
 * Servico que coordena os relacionamentos alem da amizade: fa/idolo, paquera e
 * inimizade.
 *
 * <p>A relacao fa/idolo e publica; a de paquera e privada (consultada por
 * sessao) e, quando correspondida, gera um recado automatico do sistema para
 * ambos; a de inimizade impede interacoes do alvo e e verificada de forma
 * bidirecional pelo proprio {@link Usuario}.</p>
 */
public class RelacionamentoService {

    /** Sufixo do recado automatico de paquera correspondida. */
    private static final String SUFIXO_PAQUERA = " é seu paquera - Recado do Jackut.";

    private final UsuarioRepository usuarios;

    /**
     * Cria o servico de relacionamentos.
     *
     * @param usuarios repositorio de usuarios
     */
    public RelacionamentoService(UsuarioRepository usuarios) {
        this.usuarios = usuarios;
    }

    // ----- Fa / idolo -----

    /**
     * Adiciona um idolo, tornando o solicitante seu fa.
     *
     * @param fa         usuario que passa a ser fa
     * @param loginIdolo login do idolo
     * @throws AutoFaException            se tentar ser fa de si mesmo
     * @throws IdoloJaAdicionadoException se o idolo ja tiver sido adicionado
     */
    public void adicionarIdolo(Usuario fa, String loginIdolo) {
        if (fa.getLogin().equals(loginIdolo)) {
            throw new AutoFaException();
        }
        Usuario idolo = usuarios.buscar(loginIdolo);
        fa.validarNaoInimigo(idolo);
        if (fa.ehFa(loginIdolo)) {
            throw new IdoloJaAdicionadoException();
        }
        fa.adicionarIdolo(loginIdolo);
        idolo.adicionarFa(fa.getLogin());
    }

    /**
     * Indica se um usuario e fa de um idolo.
     *
     * @param login login do possivel fa
     * @param idolo login do idolo
     * @return {@code true} se for fa
     */
    public boolean ehFa(String login, String idolo) {
        Usuario usuario = usuarios.buscarOuNulo(login);
        return usuario != null && usuario.ehFa(idolo);
    }

    /**
     * Retorna os fas de um usuario no formato {@code {a,b,c}}.
     *
     * @param login login do usuario
     * @return string formatada com os fas
     */
    public String getFas(String login) {
        Usuario usuario = usuarios.buscar(login);
        return formatar(usuario.getFas());
    }

    // ----- Paquera -----

    /**
     * Adiciona uma paquera. Se a paquera for correspondida, envia um recado
     * automatico do sistema a ambos os usuarios.
     *
     * @param usuario      usuario que adiciona a paquera
     * @param loginPaquera login da paquera
     * @throws AutoPaqueraException        se tentar paquerar a si mesmo
     * @throws PaqueraJaAdicionadaException se a paquera ja tiver sido adicionada
     */
    public void adicionarPaquera(Usuario usuario, String loginPaquera) {
        if (usuario.getLogin().equals(loginPaquera)) {
            throw new AutoPaqueraException();
        }
        Usuario outro = usuarios.buscar(loginPaquera);
        usuario.validarNaoInimigo(outro);
        if (usuario.ehPaquera(loginPaquera)) {
            throw new PaqueraJaAdicionadaException();
        }
        usuario.adicionarPaquera(loginPaquera);
        if (outro.ehPaquera(usuario.getLogin())) {
            usuario.receberRecado(new Recado(null, outro.getNome() + SUFIXO_PAQUERA));
            outro.receberRecado(new Recado(null, usuario.getNome() + SUFIXO_PAQUERA));
        }
    }

    /**
     * Indica se um login e paquera do usuario informado.
     *
     * @param usuario usuario de referencia
     * @param paquera login a verificar
     * @return {@code true} se for paquera
     */
    public boolean ehPaquera(Usuario usuario, String paquera) {
        return usuario.ehPaquera(paquera);
    }

    /**
     * Retorna as paqueras de um usuario no formato {@code {a,b,c}}.
     *
     * @param usuario usuario de referencia
     * @return string formatada com as paqueras
     */
    public String getPaqueras(Usuario usuario) {
        return formatar(usuario.getPaqueras());
    }

    // ----- Inimizade -----

    /**
     * Declara um usuario como inimigo do solicitante.
     *
     * @param usuario      usuario que declara a inimizade
     * @param loginInimigo login do inimigo
     * @throws AutoInimigoException        se tentar ser inimigo de si mesmo
     * @throws InimigoJaAdicionadoException se o inimigo ja tiver sido adicionado
     */
    public void adicionarInimigo(Usuario usuario, String loginInimigo) {
        if (usuario.getLogin().equals(loginInimigo)) {
            throw new AutoInimigoException();
        }
        usuarios.buscar(loginInimigo);
        if (usuario.temComoInimigo(loginInimigo)) {
            throw new InimigoJaAdicionadoException();
        }
        usuario.adicionarInimigo(loginInimigo);
    }

    private String formatar(List<String> itens) {
        return "{" + String.join(",", itens) + "}";
    }
}
