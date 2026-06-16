package br.ufal.ic.p2.jackut.exceptions;

/** Lancada quando login ou senha estao incorretos na abertura de sessao. */
public class LoginSenhaInvalidosException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public LoginSenhaInvalidosException() {
        super("Login ou senha inv\u00e1lidos.");
    }
}
