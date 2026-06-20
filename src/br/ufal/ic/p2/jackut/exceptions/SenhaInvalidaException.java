package br.ufal.ic.p2.jackut.exceptions;

/** Lancada quando a senha informada e nula ou vazia. */
public class SenhaInvalidaException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public SenhaInvalidaException() {
        super("Senha inválida.");
    }
}
