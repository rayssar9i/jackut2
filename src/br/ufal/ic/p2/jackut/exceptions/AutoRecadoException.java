package br.ufal.ic.p2.jackut.exceptions;

/** Lancada quando um usuario tenta enviar recado para si mesmo. */
public class AutoRecadoException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public AutoRecadoException() {
        super("Usuário não pode enviar recado para si mesmo.");
    }
}
