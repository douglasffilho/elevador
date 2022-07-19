/**
 * Enum usado para definir o sentido do elevador
 */
public enum Sentido {
    PARADO("0-parado"),
    SUBINDO("1-subindo"),
    DESCENDO("2-descendo");

    final String sentido;

    /**
     * Cria o enum com um texto que será usado na saída do console através do método toString()
     */
    Sentido(final String sentido) {
        this.sentido = sentido;
    }

    @Override
    public String toString() {
        return this.sentido;
    }
}
