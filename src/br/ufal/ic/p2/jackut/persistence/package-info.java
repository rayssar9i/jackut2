/**
 * Camada de persistencia do Jackut.
 *
 * <p>Isola toda a logica de gravacao e leitura de dados em disco. O
 * {@link br.ufal.ic.p2.jackut.persistence.PersistenciaManager} serializa e
 * desserializa o repositorio de usuarios, permitindo que o estado sobreviva ao
 * ciclo de encerrar e reabrir o sistema exigido pelos testes de aceitacao.</p>
 */
package br.ufal.ic.p2.jackut.persistence;
