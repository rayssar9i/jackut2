package br.ufal.ic.p2.jackut.persistence;

import br.ufal.ic.p2.jackut.repository.ComunidadeRepository;
import br.ufal.ic.p2.jackut.repository.UsuarioRepository;

import java.io.Serializable;

/**
 * Agregado serializavel com todo o estado persistente do Jackut.
 *
 * <p>Reune os repositorios de usuarios e de comunidades em um unico objeto,
 * de modo que a gravacao e a leitura em disco preservem, de forma atomica,
 * todo o estado necessario para sobreviver ao ciclo de encerrar e reabrir o
 * sistema exigido pelos testes de aceitacao.</p>
 */
public class Dados implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Repositorio de usuarios. */
    private final UsuarioRepository usuarios;

    /** Repositorio de comunidades. */
    private final ComunidadeRepository comunidades;

    /**
     * Cria o agregado de dados.
     *
     * @param usuarios    repositorio de usuarios
     * @param comunidades repositorio de comunidades
     */
    public Dados(UsuarioRepository usuarios, ComunidadeRepository comunidades) {
        this.usuarios = usuarios;
        this.comunidades = comunidades;
    }

    /**
     * Retorna o repositorio de usuarios.
     *
     * @return repositorio de usuarios
     */
    public UsuarioRepository getUsuarios() {
        return usuarios;
    }

    /**
     * Retorna o repositorio de comunidades.
     *
     * @return repositorio de comunidades
     */
    public ComunidadeRepository getComunidades() {
        return comunidades;
    }
}
