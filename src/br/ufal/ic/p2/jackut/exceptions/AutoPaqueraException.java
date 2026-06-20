package br.ufal.ic.p2.jackut.exceptions;

/** Lancada quando um usuario tenta ser paquera de si mesmo. */
public class AutoPaqueraException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public AutoPaqueraException() {
        super("Usuário não pode ser paquera de si mesmo.");
    }
}
