package br.ufal.ic.p2.jackut.exceptions;

/** Lancada quando o usuario tenta entrar em comunidade da qual ja participa. */
public class UsuarioJaNaComunidadeException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public UsuarioJaNaComunidadeException() {
        super("Usuario já faz parte dessa comunidade.");
    }
}
