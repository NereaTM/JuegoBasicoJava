import java.util.Random;
import java.util.Scanner;

public class Juego {
    private final Tablero tablero1;
    private final Tablero tablero2;
    private final Jugador jugador1;
    private final Jugador jugador2;
    private final boolean trucosActivados;
    private final boolean conParedes;

    public Juego(int tamanoTablero, int cantidadEnemigos, boolean trucosActivados, boolean conParedes) {
        tablero1 = new Tablero(tamanoTablero, conParedes);
        tablero2 = new Tablero(tamanoTablero, conParedes);
        jugador1 = new Jugador('A', 3);
        jugador2 = new Jugador('B', 3);
        this.trucosActivados = trucosActivados;
        this.conParedes = conParedes;

        inicializarJuego(cantidadEnemigos);
    }

    private void inicializarJuego(int cantidadEnemigos) {
        tablero1.colocarEnemigos(cantidadEnemigos);
        tablero2.colocarEnemigos(cantidadEnemigos);
        tablero1.colocarVidaExtra();
        tablero2.colocarVidaExtra();
        tablero1.colocarSalida();
        tablero2.colocarSalida();

        posicionarJugadorAleatorio(tablero1, jugador1);
        posicionarJugadorAleatorio(tablero2, jugador2);
    }

    private void posicionarJugadorAleatorio(Tablero tablero, Jugador jugador) {
        Random random = new Random();
        int fila, columna;
        do {
            fila = random.nextInt(tablero.getMedidaTablero());
            columna = random.nextInt(tablero.getMedidaTablero());
        } while (!tablero.esEnemigo(fila, columna) && !tablero.esSalida(fila, columna));
        jugador.setPosicion(fila, columna);
        tablero.colocarJugador(fila, columna, jugador.getSimbolo());
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            jugarTurno(jugador1, tablero1, scanner);
            if (!jugador1.estaVivo()) {
                System.out.println("Jugador 2 gana!");
                break;
            }
            jugarTurno(jugador2, tablero2, scanner);
            if (!jugador2.estaVivo()) {
                System.out.println("Jugador 1 gana!");
                break;
            }
        }
    }

    private void jugarTurno(Jugador jugador, Tablero tablero, Scanner scanner) {
        System.out.println("Turno de " + jugador.getSimbolo() + ". Vidas restantes: " + jugador.getVidas());
        tablero.mostrarTablero(jugador.getSimbolo(),trucosActivados);
        System.out.println("Ingresa tu movimiento 1-3 + W,A,S,D (ejemplo: 1D): ");
        String movimiento = scanner.nextLine().toUpperCase();

        // movmiento.matches(regex) es para detectar caraceres
        if (movimiento.matches("[1-3][WASD]")) {
            int numCasillas = Character.getNumericValue(movimiento.charAt(0));
            char direccion = movimiento.charAt(1);
            moverJugador(jugador, tablero, numCasillas, direccion, conParedes);
        } else {
            System.out.println("Movimiento invalido. Perdiste el turno");
        }
        tablero.mostrarTablero(jugador.getSimbolo(),trucosActivados);
        System.out.println("----------------------");
    }

    private void moverJugador(Jugador jugador, Tablero tablero, int numCasillas, char direccion, boolean conParedes) {
        int filaActual = jugador.getFila();
        int columnaActual = jugador.getColumna();
        int nuevaFila = filaActual;
        int nuevaColumna = columnaActual;

        // Calcular nueva posición basada en la direccion
        switch (direccion) {
            case 'W': // Arriba
                nuevaFila = filaActual - numCasillas;
                break;
            case 'S': // Abajo
                nuevaFila = filaActual + numCasillas;
                break;
            case 'A': // Izquierda
                nuevaColumna = columnaActual - numCasillas;
                break;
            case 'D': // Derecha
                nuevaColumna = columnaActual + numCasillas;
                break;
            default:
                System.out.println("Direccion no reconocida");
                return;
        }

        // Comprueba si tiene o no paredes
        if (conParedes) {
            if (nuevaFila < 0 || nuevaFila >= tablero.getMedidaTablero() || nuevaColumna < 0 || nuevaColumna >= tablero.getMedidaTablero()) {
                System.out.println("No puedes salir del tablero. Perdiste el turno");
                return;
            }
        } else {
            // Movimiento sin paredes, % calcula el resto de la division para poder realizar los saltos
            nuevaFila = (nuevaFila + tablero.getMedidaTablero()) % tablero.getMedidaTablero();
            nuevaColumna = (nuevaColumna + tablero.getMedidaTablero()) % tablero.getMedidaTablero();
        }

        // Funcionamiento enemigos
        if (tablero.esEnemigo(nuevaFila, nuevaColumna)) {
            jugador.perderVida();
            tablero.eliminarEnemigo(nuevaFila, nuevaColumna);
            System.out.println("Has perdido una vida! Enemigo eliminado");
        }

        //  Funcionamiento vida extra
        if (tablero.esVidaExtra(nuevaFila, nuevaColumna)) {
            jugador.sumarVida();
            tablero.eliminarVidaExtra(nuevaFila, nuevaColumna);
            System.out.println("Has ganado una vida! Vida extra eliminada");
        }

        // Ganar el juego
        if (tablero.esSalida(nuevaFila, nuevaColumna)) {
            System.out.println("Has llegado a la salida! Felicidades!");
            System.exit(0);
        }

        // Actualizar posicion en el tablero
        tablero.actualizarPosicion(filaActual, columnaActual, nuevaFila, nuevaColumna, jugador.getSimbolo());
        jugador.setPosicion(nuevaFila, nuevaColumna);
    }
}

