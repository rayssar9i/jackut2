package br.ufal.ic.p2.jackut.exceptions;

/**
 * Lancada quando uma operacao e barrada por relacao de inimizade entre os
 * usuarios envolvidos. A mensagem nomeia o usuario considerado inimigo.
 */
public class FuncaoInvalidaInimigoException extends JackutException {
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao indicando o nome do usuario que e inimigo.
     *
     * @param nomeInimigo nome de exibicao do usuario inimigo
     */
    public FuncaoInvalidaInimigoException(String nomeInimigo) {
        super("Função inválida: " + nomeInimigo + " é seu inimigo.");
    }
}
