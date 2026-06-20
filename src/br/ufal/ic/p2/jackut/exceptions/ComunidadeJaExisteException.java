package br.ufal.ic.p2.jackut.exceptions;

/** Lancada ao tentar criar comunidade com nome ja existente. */
public class ComunidadeJaExisteException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public ComunidadeJaExisteException() {
        super("Comunidade com esse nome já existe.");
    }
}
