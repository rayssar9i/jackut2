package br.ufal.ic.p2.jackut.exceptions;

/**
 * Excecao base do sistema Jackut.
 * Todas as excecoes de dominio herdam desta classe, permitindo
 * captura generica e centralizando o vocabulario de erros.
 */
public class JackutException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Cria uma excecao com a mensagem informada.
     *
     * @param mensagem mensagem de erro exibida ao usuario
     */
    public JackutException(String mensagem) {
        super(mensagem);
    }
}
