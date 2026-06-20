package br.ufal.ic.p2.jackut.exceptions;

/** Lancada ao tentar ler recado de uma caixa vazia. */
public class SemRecadosException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public SemRecadosException() {
        super("Não há recados.");
    }
}
