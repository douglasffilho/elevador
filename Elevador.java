/**
 * Classe responsavel pelo objeto com a logica de um elevador
 * Implementa runnable para facilitar a criação de uma thread com essa classe
 */
public class Elevador implements Runnable {
    private static final int CICLOS_POR_ANDAR_PADRAO = 4; // Define um padrão de ciclos (ciclos de 4 segundos)
    private final Sensor sensor;
    private final GestorDeFila gestorDeFila;
    private Sentido sentido;
    private int destinoAtual;
    private GestorDeCiclos gestorDeCiclos;

    /**
     * Cria objeto de elevador com limites maximo e minimo de andares e padrão de elevador parado no terreo
     */
    public Elevador(final int terreo, final int cobertura) {
        this.sensor = new Sensor(terreo, cobertura);
        this.sentido = Sentido.PARADO;
        this.destinoAtual = 0;
        this.gestorDeCiclos = new GestorDeCiclos(CICLOS_POR_ANDAR_PADRAO);
        this.gestorDeFila = new GestorDeFila();
    }

    /**
     * Método para redefinir a quantidade de ciclos por andar
     */
    public void setCiclosPorAndar(int ciclosPorAndar) {
        this.gestorDeCiclos = new GestorDeCiclos(ciclosPorAndar);
    }

    /**
     * Método que é executado ao se criar uma Thread com Elevador e chamar o método start()
     */
    @Override
    public void run() {
        /**
         * Enquanto o programa rodar:
         * - tenta executar a atualização de status
         * - imprime o status atual
         * - espera 1 segundo
         * - atualiza o ciclo atual (incremento e/ou reset de ciclo)
         */
        while (Principal.executando) {
            try {
                atualizaStatus();
                imprimeStatus();
                Thread.sleep(1000);
                this.gestorDeCiclos.atualizaCiclo();
            } catch (Exception ignora) {
            }
        }
    }

    /**
     * Método para auxiliar na criação de uma Thread com Elevador e iniciar a mesma a partir do programa principal
     */
    public Thread inicia() throws InterruptedException {
        Thread thread = new Thread(this, "elevador");
        thread.start();
        return thread;
    }

    /**
     * Método para adicionar um destino à fila de destinos controlando o sentido do elevador
     */
    public void addDestino(int destino) {
        this.sentido = this.sensor.getAndarAtual() < destino ? Sentido.SUBINDO : Sentido.DESCENDO;
        this.gestorDeFila.addDestino(destino, this.sentido);
    }

    /**
     * Método para saber se o elevador chegou em seu destino
     */
    private boolean chegouNoDestino() {
        return this.destinoAtual == this.sensor.getAndarAtual();
    }

    /**
     * Método para atualizar o status do ciclo
     * O sentido do elevador sempre será atualizado, independente do ciclo
     * A obtenção ou não de um novo destino a partir da fila será feita apenas no primeiro ciclo
     */
    private void atualizaStatus() {
        // Se o andar atual está abaixo do destino atual, o sentido do elevador é SUBINDO
        if (this.sensor.getAndarAtual() < this.destinoAtual)
            this.sentido = Sentido.SUBINDO;
        // Se o andar atual está acima do destino atual, o sentido do elevador é DESCENDO
        else if (this.sensor.getAndarAtual() > this.destinoAtual)
            this.sentido = Sentido.DESCENDO;
        // Se o andar atual está no destino atual, elevador fica PARADO
        else
            this.sentido = Sentido.PARADO;

        // Se o ciclo atual é o primeiro ciclo, faz as validações para atualização
        if (this.gestorDeCiclos.getCicloAtual() == 0) {
            if (this.gestorDeFila.temDestinoEsperando()) {
                /**
                 * Se a fila tem destinos e o elevador já chegou no destino atual
                 * pega o proximo destino e notifica que o elevador vai se mover para ele no priximo inicio de ciclo
                 * enquanto não chega no inicio do ciclo, o elevador fica parado
                 * PS: o elevador estando no terreo e o programa tendo iniciado agora, significa que ja está no destino
                 */
                if (chegouNoDestino()) {
                    this.sentido = Sentido.PARADO;
                    this.destinoAtual = this.gestorDeFila.getProximoDestino();
                    System.out.printf("Movendo para %s%n", this.destinoAtual);
                }
                /**
                 * Se ainda não chegou no destino, mas, tem destino esperando, movimenta o elevador até o destino
                 */
                else {
                    if (this.sentido == Sentido.SUBINDO)
                        this.sensor.sobe();
                    else
                        this.sensor.desce();
                }
            } else {
                /**
                 * Se não tem destino esperando, mas, ainda não chegou no destino, movimenta o elevador até o destino
                 */
                if (!chegouNoDestino()) {
                    if (this.sentido == Sentido.SUBINDO)
                        this.sensor.sobe();
                    else
                        this.sensor.desce();
                }

                /**
                 * Caso o elevador tenha chegado no ultimo destino, para a execução do programa
                 * Essa é uma validação para parar a execução quando chegar no fim da fila
                 * Quando for usar Java2D, teremos que manter o processo executando, então, é só remover esse "else"
                 */
                else {
                    Principal.executando = false;
                }
            }
        }
    }

    /**
     * Método que ajuda a imprimir o status atual do elevador
     */
    private void imprimeStatus() {
        System.out.printf(
                "andar = %s sentido = %s ciclo = %s fila = %s%n",
                this.sensor.getAndarAtual(),
                this.sentido,
                this.gestorDeCiclos.getCicloAtual(),
                this.gestorDeFila.getFila()
        );
    }
}
