import easyaccept.EasyAccept;

/**
 * Executa, em sequencia, todos os scripts de aceitacao do Jackut sobre a
 * {@link br.ufal.ic.p2.jackut.Facade}.
 *
 * <p>Requer o easyaccept.jar no classpath. Cada par de testes ({@code _1}/
 * {@code _2}) exercita a persistencia: o primeiro grava o estado e o segundo
 * o reabre.</p>
 */
public class Main {

    /**
     * Ponto de entrada.
     *
     * @param args nao utilizados
     */
    public static void main(String[] args) {
        String[] testes = {
            "tests/us1_1.txt", "tests/us1_2.txt",
            "tests/us2_1.txt", "tests/us2_2.txt",
            "tests/us3_1.txt", "tests/us3_2.txt",
            "tests/us4_1.txt", "tests/us4_2.txt",
            "tests/us5_1.txt", "tests/us5_2.txt",
            "tests/us6_1.txt", "tests/us6_2.txt",
            "tests/us7_1.txt", "tests/us7_2.txt",
            "tests/us8_1.txt", "tests/us8_2.txt",
            "tests/us9_1.txt", "tests/us9_2.txt",
        };
        for (String teste : testes) {
            EasyAccept.main(new String[] {"br.ufal.ic.p2.jackut.Facade", teste});
        }
    }
}
