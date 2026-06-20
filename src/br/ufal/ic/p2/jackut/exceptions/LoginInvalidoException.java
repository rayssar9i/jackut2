package br.ufal.ic.p2.jackut.exceptions;

/** Lancada quando o login informado e nulo ou vazio. */
public class LoginInvalidoException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public LoginInvalidoException() {
        super("Login inválido.");
    }
}
