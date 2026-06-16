package br.ufal.ic.p2.jackut.exceptions;

/** Lancada ao reenviar convite para quem ja tem convite pendente. */
public class ConvitePendenteException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public ConvitePendenteException() {
        super("Usu\u00e1rio j\u00e1 est\u00e1 adicionado como amigo, esperando aceita\u00e7\u00e3o do convite.");
    }
}
