package br.ufal.ic.p2.jackut.exceptions;

/** Lancada ao adicionar como idolo alguem que ja e idolo. */
public class IdoloJaAdicionadoException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public IdoloJaAdicionadoException() {
        super("Usuário já está adicionado como ídolo.");
    }
}
