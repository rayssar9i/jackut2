/**
 * Servicos de negocio do Jackut.
 *
 * <p>Concentra as regras de aplicacao que operam sobre as entidades de
 * dominio, mantendo o coordenador {@code Sistema} livre dessas
 * responsabilidades:</p>
 * <ul>
 *   <li>{@link br.ufal.ic.p2.jackut.service.SessaoManager} &mdash; ciclo de
 *       vida das sessoes e geracao de identificadores.</li>
 *   <li>{@link br.ufal.ic.p2.jackut.service.AmizadeService} &mdash; envio e
 *       aceitacao de convites e consultas de amizade.</li>
 *   <li>{@link br.ufal.ic.p2.jackut.service.RecadoService} &mdash; envio e
 *       leitura de recados entre usuarios.</li>
 * </ul>
 */
package br.ufal.ic.p2.jackut.service;
