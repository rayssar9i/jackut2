package br.ufal.ic.p2.jackut.exceptions;

/** Lancada quando um usuario tenta ser inimigo de si mesmo. */
public class AutoInimigoException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public AutoInimigoException() {
        super("Usuário não pode ser inimigo de si mesmo.");
    }
}
