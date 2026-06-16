/**
 * Excecoes de dominio do Jackut.
 *
 * <p>Define um vocabulario de erros especifico do sistema. Todas as excecoes
 * herdam de {@link br.ufal.ic.p2.jackut.exceptions.JackutException}, que por
 * sua vez estende {@link java.lang.RuntimeException}. Cada subclasse fixa, em
 * seu construtor, a mensagem esperada pelos testes de aceitacao, centralizando
 * o texto e dando significado semantico a cada falha (login invalido, conta ja
 * existente, atributo nao preenchido, auto-amizade, etc.).</p>
 */
package br.ufal.ic.p2.jackut.exceptions;
