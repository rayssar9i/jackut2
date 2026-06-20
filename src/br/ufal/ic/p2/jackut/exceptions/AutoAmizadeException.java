package br.ufal.ic.p2.jackut.exceptions;

/** Lancada quando um usuario tenta adicionar a si mesmo como amigo. */
public class AutoAmizadeException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public AutoAmizadeException() {
        super("Usuário não pode adicionar a si mesmo como amigo.");
    }
}
