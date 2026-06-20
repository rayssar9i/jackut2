package br.ufal.ic.p2.jackut.exceptions;

/** Lancada ao tentar ler mensagem de uma caixa vazia. */
public class SemMensagensException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public SemMensagensException() {
        super("Não há mensagens.");
    }
}
