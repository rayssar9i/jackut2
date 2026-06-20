package br.ufal.ic.p2.jackut.exceptions;

/** Lancada quando um usuario tenta ser fa de si mesmo. */
public class AutoFaException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public AutoFaException() {
        super("Usuário não pode ser fã de si mesmo.");
    }
}
