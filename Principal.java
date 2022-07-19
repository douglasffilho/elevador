import java.util.Scanner;

/**
 * Classe principal responsável por executar a logica do elevador
 */
public class Principal {
    /**
     * Parametro usado para compartilhar o status de manter as threads executando
     */
    public static boolean executando = true;

    /**
     * Método chamado pela JVM para executar a logica do elevador
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("******** Execução: início ********");

        /**
         * Cria objeto de elevador com definição de terreo e cobertura (andar minimo e maximo)
         */
        Elevador elevador = new Elevador(0 , 10);

        /**
         * Define a quantidade de ciclos que o elevador levar em uma andar até atualizar seu status
         * Cada ciclo dura 1 segundo (1000ms)
         */
        elevador.setCiclosPorAndar(4);

        /**
         * Thread usada para pegar entrada do teclado de forma assincrona
         */
        Thread entrada = new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 * Cria um scanner para ler entradas do console/teclado
                 */
                Scanner scanner = new Scanner(System.in);

                /**
                 * Enquanto o parametro de execução for verdadeiro, mantem o loop
                 * quando for falso, a thread acaba
                 */
                while (Principal.executando) {
                    String comando = scanner.next();
                    /**
                     * Caso o botão pressionado seja "p", manda parar a execução
                     */
                    if (comando.equals("p")) {
                        Principal.executando = false;
                    } else {
                        try {
                            /**
                             * Caso não seja "p"
                             * Tenta converter o botão pressionado em um andar do elevador (destino)
                             * e envia o novo destino à fila de destinos do elevador
                             */
                            elevador.addDestino(Integer.parseInt(comando));
                        } catch (Exception ignora) {} //ignora erros de qualquer tipo pra manter a execução
                    }
                }

                /**
                 * Para a thread corrente ao sair do loop de execução da obtenção de comandos do teclado
                 */
                Thread.currentThread().interrupt();
            }
        });
        /**
         * Inicializa a thread e não espera acabar
         */
        entrada.start();

        /**
         * Teste de comandos para mover o elevador
         */
        elevador.addDestino(3);
        elevador.addDestino(5);
        elevador.addDestino(1);
        elevador.addDestino(1);

        System.out.println("run:");
        /**
         * Obtem momento que inicia a execução do elevador
         */
        long start = System.currentTimeMillis();

        /**
         * inicia a thread do elevador e espera acabar (quando apertamos o botão "p" do teclado e enviamos com Enter)
         */
        elevador.inicia().join();

        /**
         * Coleta o momento que a exeução acaba
         */
        long end = System.currentTimeMillis();

        /**
         * Calcula a diferença do tempo
         */
        double diffSecs = (double) (end - start) / 1000d;


        System.out.printf("CONSTRUÇÃO PARADA (tempo total: %s segundos)%n", (int) diffSecs);
        System.out.println("******** Execução: fim ********");

        /**
         * Finaliza a execução atual
         */
        System.exit(0);
    }
}
