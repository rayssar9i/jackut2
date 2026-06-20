package br.ufal.ic.p2.jackut.exceptions;

/** Lancada ao adicionar como inimigo alguem que ja e inimigo. */
public class InimigoJaAdicionadoException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public InimigoJaAdicionadoException() {
        super("Usuário já está adicionado como inimigo.");
    }
}
