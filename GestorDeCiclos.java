/**
 * Classe responsavel por criar o objeto que gerencia os ciclos do elevador
 */
public class GestorDeCiclos {
    private final int ciclosPorAndar;
    private int cicloAtual;

    public GestorDeCiclos(int ciclosPorAndar) {
        this.ciclosPorAndar = ciclosPorAndar; // Inicia o gerenciador com essa quantidade de ciclos
        this.cicloAtual = 0; // inicia no ciclo 0 (primeiro ciclo)
    }

    public void atualizaCiclo() {
        this.cicloAtual++; // Incrementa o ciclo
        if (this.cicloAtual == this.ciclosPorAndar) // Se chegou no limite, reseta para o primeiro ciclo
            this.cicloAtual = 0;
    }

    public int getCicloAtual() {
        return this.cicloAtual;
    }
}
