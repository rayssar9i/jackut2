package br.ufal.ic.p2.jackut.exceptions;

/** Lancada ao reenviar convite para quem ja tem convite pendente. */
public class ConvitePendenteException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public ConvitePendenteException() {
        super("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    }
}
