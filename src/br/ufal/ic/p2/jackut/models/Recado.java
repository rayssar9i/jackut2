package br.ufal.ic.p2.jackut.models;

import java.io.Serializable;

/**
 * Recado enviado de um usuario a outro.
 *
 * <p>Alem do texto exibido ao destinatario, guarda o login do remetente. Isso
 * permite remover, ao encerrar uma conta, todos os recados enviados por aquele
 * usuario. Recados gerados pelo proprio sistema (por exemplo, o aviso de
 * paquera correspondida) usam remetente {@code null}.</p>
 */
public class Recado implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Login de quem enviou o recado, ou {@code null} se for do sistema. */
    private final String remetente;

    /** Texto do recado. */
    private final String texto;

    /**
     * Cria um recado.
     *
     * @param remetente login do remetente, ou {@code null} para recado do sistema
     * @param texto     conteudo do recado
     */
    public Recado(String remetente, String texto) {
        this.remetente = remetente;
        this.texto = texto;
    }

    /**
     * Retorna o login do remetente.
     *
     * @return login do remetente, ou {@code null} se for do sistema
     */
    public String getRemetente() {
        return remetente;
    }

    /**
     * Retorna o texto do recado.
     *
     * @return texto do recado
     */
    public String getTexto() {
        return texto;
    }
}
