package br.ufal.ic.p2.jackut.service;

import br.ufal.ic.p2.jackut.models.Comunidade;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.repository.ComunidadeRepository;
import br.ufal.ic.p2.jackut.repository.UsuarioRepository;

/**
 * Servico responsavel pelo envio e leitura de mensagens de comunidades.
 *
 * <p>Uma mensagem enviada a uma comunidade e entregue, em ordem, a caixa de
 * mensagens de cada um de seus membros. As mensagens sao independentes dos
 * recados privados trocados entre usuarios.</p>
 */
public class MensagemService {

    private final UsuarioRepository usuarios;
    private final ComunidadeRepository comunidades;

    /**
     * Cria o servico de mensagens.
     *
     * @param usuarios    repositorio de usuarios
     * @param comunidades repositorio de comunidades
     */
    public MensagemService(UsuarioRepository usuarios, ComunidadeRepository comunidades) {
        this.usuarios = usuarios;
        this.comunidades = comunidades;
    }

    /**
     * Envia uma mensagem a todos os membros de uma comunidade.
     *
     * @param nomeComunidade nome da comunidade destinataria
     * @param mensagem       texto da mensagem
     */
    public void enviarMensagem(String nomeComunidade, String mensagem) {
        Comunidade comunidade = comunidades.buscar(nomeComunidade);
        for (String loginMembro : comunidade.getMembros()) {
            Usuario membro = usuarios.buscarOuNulo(loginMembro);
            if (membro != null) {
                membro.receberMensagem(mensagem);
            }
        }
    }

    /**
     * Le a proxima mensagem da caixa do usuario.
     *
     * @param usuario usuario que le a mensagem
     * @return texto da mensagem
     */
    public String lerMensagem(Usuario usuario) {
        return usuario.lerMensagem();
    }
}
