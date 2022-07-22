import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe responsavel por fazer a gestão da fila de destinos
 */
public class GestorDeFila {
    private final List<Integer> fila;

    public GestorDeFila() {
        this.fila = new ArrayList<>();
    }

    /**
     * Sempre que adicionar um destino, informar o sentido do elevador para poder reordenar a fila da maneira correta
     */
    public void addDestino(int destino, Sentido sentido) {
        /**
         * Só adiciona o destino se ele não existir na fila
         */
        if (!this.fila.contains(destino)) {
            this.fila.add(destino);
            if (sentido == Sentido.DESCENDO) { // Caso esteja descendo
                // Inverte a fila para que o proximo destino a ser coletado no ciclo seja o andar mais perto
                Collections.reverse(this.fila);
            } else { // Caso esteja parado ou subindo
                // Ordena a fila para que o proximo destino a ser coletado no ciclo seja o andar mais perto
                Collections.sort(this.fila);
            }
        }
    }

    /**
     * Método para saber se tem algum destino na fila
     */
    public boolean temDestinoEsperando() {
        return !this.fila.isEmpty();
    }

    /**
     * Le o proximo destino da fila e remove ele
     */
    public int getProximoDestino() {
        int proximoDestino = this.fila.get(0);
        this.fila.remove(0);
        return proximoDestino;
    }

    /**
     * Le a fila
     */
    public List<Integer> getFila() {
        return this.fila;
    }
}
