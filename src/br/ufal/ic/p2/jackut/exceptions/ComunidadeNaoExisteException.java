package br.ufal.ic.p2.jackut.exceptions;

/** Lancada ao referenciar comunidade inexistente. */
public class ComunidadeNaoExisteException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public ComunidadeNaoExisteException() {
        super("Comunidade não existe.");
    }
}
