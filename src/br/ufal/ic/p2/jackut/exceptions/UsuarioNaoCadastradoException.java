package br.ufal.ic.p2.jackut.exceptions;

/** Lancada quando um usuario ou sessao nao e encontrado. */
public class UsuarioNaoCadastradoException extends JackutException {
    private static final long serialVersionUID = 1L;
    /** Cria a excecao com a mensagem padrao definida para este erro. */
    public UsuarioNaoCadastradoException() {
        super("Usuário não cadastrado.");
    }
}
