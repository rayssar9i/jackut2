package br.ufal.ic.p2.jackut.exceptions;

/** Lancada ao consultar um atributo de perfil ainda nao preenchido. */
public class AtributoNaoPreenchidoException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public AtributoNaoPreenchidoException() {
        super("Atributo n\u00e3o preenchido.");
    }
}
