package br.ufal.ic.p2.jackut.service;

import br.ufal.ic.p2.jackut.models.Comunidade;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.repository.ComunidadeRepository;
import br.ufal.ic.p2.jackut.repository.UsuarioRepository;

/**
 * Servico responsavel por encerrar a conta de um usuario.
 *
 * <p>Remove o usuario do sistema e apaga todos os seus rastros: comunidades das
 * quais e dono (com a saida de todos os membros), participacao em comunidades
 * de terceiros, recados que enviou e referencias em relacoes de outros
 * usuarios (amizade, convites, fa/idolo, paquera e inimizade).</p>
 */
public class RemocaoService {

    private final UsuarioRepository usuarios;
    private final ComunidadeRepository comunidades;

    /**
     * Cria o servico de remocao.
     *
     * @param usuarios    repositorio de usuarios
     * @param comunidades repositorio de comunidades
     */
    public RemocaoService(UsuarioRepository usuarios, ComunidadeRepository comunidades) {
        this.usuarios = usuarios;
        this.comunidades = comunidades;
    }

    /**
     * Remove o usuario alvo e todas as suas informacoes do sistema.
     *
     * @param alvo usuario a remover
     */
    public void remover(Usuario alvo) {
        String login = alvo.getLogin();

        for (Comunidade comunidade : comunidades.todas()) {
            if (comunidade.getDono().equals(login)) {
                for (String loginMembro : comunidade.getMembros()) {
                    Usuario membro = usuarios.buscarOuNulo(loginMembro);
                    if (membro != null) {
                        membro.sairComunidade(comunidade.getNome());
                    }
                }
                comunidades.remover(comunidade.getNome());
            } else if (comunidade.temMembro(login)) {
                comunidade.removerMembro(login);
            }
        }

        for (Usuario usuario : usuarios.todos()) {
            if (!usuario.getLogin().equals(login)) {
                usuario.removerRecadosDe(login);
                usuario.removerReferencias(login);
            }
        }

        usuarios.remover(login);
    }
}
