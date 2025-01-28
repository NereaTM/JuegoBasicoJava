import java.util.Random;

public class Tablero {
    private final int medidaTablero;
    private final char[][] tablero;
    private final char vacio = 'L';
    private final char enemigo = 'E';
    private final char salida = 'S';
    private final char vidaExtra = 'V';
    private final boolean conParedes;

    // Los tableros son arrays bidimensionales y se leen primero horizontalmente y luego verticalmente
    public Tablero(int medidaTablero, boolean conParedes) {
        this.medidaTablero = medidaTablero;
        this.tablero = new char[medidaTablero][medidaTablero];
        this.conParedes = conParedes;
        inicializarTablero();
    }

    private void inicializarTablero() {
        for (int i = 0; i < medidaTablero; i++) {
            for (int j = 0; j < medidaTablero; j++) {
                tablero[i][j] = vacio;
            }
        }
    }

    public void colocarJugador(int fila, int columna, char jugador) {
        tablero[fila][columna] = jugador;
    }

    public void colocarEnemigos(int cantidadEnemigos) {
        Random random = new Random();
        int colocados = 0;
        while (colocados <= cantidadEnemigos) {
            int fila = random.nextInt(medidaTablero);
            int columna = random.nextInt(medidaTablero);
            if (tablero[fila][columna] == vacio) {
                tablero[fila][columna] = enemigo;
                colocados++;
            }
        }
    }

    public void colocarVidaExtra() {
        Random random = new Random();
        int colocados = 0;
        while (colocados < 2) {
            int fila = random.nextInt(medidaTablero);
            int columna = random.nextInt(medidaTablero);
            if (tablero[fila][columna] == vacio) {
                tablero[fila][columna] = vidaExtra;
                colocados++;
            }
        }
    }

    public void colocarSalida() {
        Random random = new Random();
        int fila, columna;
        do {
            fila = random.nextInt(medidaTablero);
            columna = random.nextInt(medidaTablero);
        } while (tablero[fila][columna] != vacio);
        tablero[fila][columna] = salida;
    }

    // Utilizamos booleans para que nos de valores true
    public boolean esEnemigo(int fila, int columna) {
        return tablero[fila][columna] == enemigo;
    }

    public boolean esVidaExtra(int fila, int columna) {
        return tablero[fila][columna] == vidaExtra;
    }

    public boolean esSalida(int fila, int columna) {
        return tablero[fila][columna] == salida;
    }

    public void actualizarPosicion(int filaAntigua, int columnaAntigua, int filaNueva, int columnaNueva, char jugador) {
        tablero[filaAntigua][columnaAntigua] = vacio;
        tablero[filaNueva][columnaNueva] = jugador;
    }

    public void mostrarTablero(char jugador, boolean trucosActivados) {
        for (int i = 0; i < medidaTablero; i++) {
            for (int j = 0; j < medidaTablero; j++) {
                if (!trucosActivados && tablero[i][j] == enemigo) {
                    System.out.print(vacio + " "); // Oculta enemigos
                } else {
                    System.out.print(tablero[i][j] + " "); // Muestra enemigos
                }
            }
            System.out.println();
        }
    }

    public int getMedidaTablero() {
        return medidaTablero;
    }

    public void eliminarEnemigo(int fila, int columna) {
        tablero[fila][columna] = vacio;
    }

    public void eliminarVidaExtra(int fila, int columna) {
        tablero[fila][columna] = vacio;
    }

}
