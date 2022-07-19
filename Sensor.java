/**
 * Classe do objeto usado para controlar o posicionamento do elevador e ajudar a ter menos codigo
 * na classe do Elevador
 */
public class Sensor {
    private boolean chegouNaCobertura;
    private boolean chegouNoTerreo;
    private int andarAtual;
    private final int terreo;
    private final int cobertura;

    /**
     * Inicia o sensor com os limites minimo e maximo de andares
     */
    public Sensor(final int terreo, final int cobertura) {
        this.chegouNaCobertura = false;
        this.chegouNoTerreo = true;
        this.andarAtual = terreo;
        this.terreo = terreo;
        this.cobertura = cobertura;
    }

    /**
     * Movimenta o elevador pra cima e atualiza os dados que serão usados por ele
     */
    public boolean sobe() {
        if (this.chegouNaCobertura) {
            System.out.println("ja chegou na cobertura");
            return false;
        }

        this.andarAtual++;
        this.chegouNoTerreo = false;
        this.chegouNaCobertura = this.andarAtual == this.cobertura;

        return true;
    }

    /**
     * Movimenta o elevador pra baixo e atualiza os dados que serão usados por ele
     */
    public boolean desce() {
        if (this.chegouNoTerreo) {
            System.out.println("ja chegou no terreo");
            return false;
        }

        this.andarAtual--;
        this.chegouNoTerreo = this.andarAtual == this.terreo;
        this.chegouNaCobertura = false;

        return true;
    }

    /**
     * Disponibiliza a informação de andar atual para ser usada no ciclo
     */
    public int getAndarAtual() {
        return this.andarAtual;
    }
}
