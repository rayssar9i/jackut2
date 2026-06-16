/**
 * Pacote raiz do Jackut, uma rede de relacionamentos inspirada no Orkut.
 *
 * <p>Contem os pontos de entrada e coordenacao do sistema:</p>
 * <ul>
 *   <li>{@link br.ufal.ic.p2.jackut.Facade} &mdash; fachada acessada pelos
 *       testes de aceitacao (EasyAccept); apenas delega ao sistema.</li>
 *   <li>{@link br.ufal.ic.p2.jackut.Sistema} &mdash; coordenador que orquestra
 *       os colaboradores especializados (repositorio, servicos e
 *       persistencia), sem implementar regras de negocio diretamente.</li>
 * </ul>
 *
 * <p>As responsabilidades de dominio estao distribuidas nos subpacotes
 * {@code models}, {@code repository}, {@code service}, {@code persistence} e
 * {@code exceptions}.</p>
 */
package br.ufal.ic.p2.jackut;
