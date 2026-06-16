package br.ufal.ic.p2.jackut.exceptions;

/** Lancada ao tentar criar usuario com login ja existente. */
public class ContaJaExisteException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public ContaJaExisteException() {
        super("Conta com esse nome j\u00e1 existe.");
    }
}
