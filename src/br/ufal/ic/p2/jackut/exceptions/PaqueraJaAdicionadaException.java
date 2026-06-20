package br.ufal.ic.p2.jackut.exceptions;

/** Lancada ao adicionar como paquera alguem que ja e paquera. */
public class PaqueraJaAdicionadaException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public PaqueraJaAdicionadaException() {
        super("Usuário já está adicionado como paquera.");
    }
}
