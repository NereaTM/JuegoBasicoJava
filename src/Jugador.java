public class Jugador {
    private int fila;
    private int columna;
    private int vidas;
    private final char simbolo;

    public Jugador(char simbolo, int vidasIniciales) {
        this.simbolo = simbolo;
        this.vidas = vidasIniciales;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    // Indicar las posiciones
    public void setPosicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public char getSimbolo() {
        return simbolo;
    }

    public int getVidas() {
        return vidas;
    }

    // -- menos vida
    public void perderVida() {
        this.vidas--;
    }

    // ++ más vida
    public void sumarVida() {
        this.vidas++;
    }

    public boolean estaVivo() {
        return vidas > 0;
    }

}

