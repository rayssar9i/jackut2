package br.ufal.ic.p2.jackut.service;

import br.ufal.ic.p2.jackut.exceptions.AutoRecadoException;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.repository.UsuarioRepository;

/**
 * Servico responsavel pelo envio e leitura de recados entre usuarios.
 *
 * <p>Valida a regra de que um usuario nao pode enviar recado para si mesmo e
 * delega o armazenamento/leitura ao proprio {@link Usuario} destinatario.</p>
 */
public class RecadoService {

    private final UsuarioRepository usuarios;

    /**
     * Cria o servico de recados.
     *
     * @param usuarios repositorio de usuarios
     */
    public RecadoService(UsuarioRepository usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * Envia um recado de um remetente para um destinatario.
     *
     * @param remetente     usuario que envia o recado
     * @param destinatario  login do destinatario
     * @param recado        texto do recado
     * @throws AutoRecadoException se remetente e destinatario forem o mesmo
     */
    public void enviarRecado(Usuario remetente, String destinatario, String recado) {
        if (remetente.getLogin().equals(destinatario)) {
            throw new AutoRecadoException();
        }
        usuarios.buscar(destinatario).receberRecado(recado);
    }

    /**
     * Le o proximo recado da caixa do usuario.
     *
     * @param usuario usuario que le o recado
     * @return texto do recado
     */
    public String lerRecado(Usuario usuario) {
        return usuario.lerRecado();
    }
}
