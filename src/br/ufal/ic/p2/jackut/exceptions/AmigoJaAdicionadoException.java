package br.ufal.ic.p2.jackut.exceptions;

/** Lancada ao tentar adicionar alguem que ja e amigo. */
public class AmigoJaAdicionadoException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public AmigoJaAdicionadoException() {
        super("Usu\u00e1rio j\u00e1 est\u00e1 adicionado como amigo.");
    }
}
