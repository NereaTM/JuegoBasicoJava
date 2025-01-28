import java.util.Scanner;

//Se crea un menu para poder determinar las funcionalidades del juego
public class Menu {
    public Menu() {
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("----- MENU PRINCIPAL -----");
            System.out.println("1. Jugar");
            System.out.println("2. Salir");
            System.out.print("Selecciona una opcion: ");
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    System.out.print("Introduce el tamaño del tablero (minimo 6): ");
                    int tamano = scanner.nextInt();
                    if (tamano < 6) {
                        System.out.println("El tamaño minimo es 6. Usando tamaño 6 por defecto");
                        tamano = 6;
                    }

                    //para poder elegir digicultad tenemos que volveer a hacer un switch
                    System.out.println("Selecciona la dificultad:");
                    System.out.println("1. Facil (5 enemigos)");
                    System.out.println("2. Normal (8 enemigos)");
                    System.out.println("3. Dificil (12 enemigos)");
                    int dificultad = scanner.nextInt();
                    int cantidadEnemigos;
                    switch (dificultad) {
                        case 1:
                            cantidadEnemigos = 5;
                            break;
                        case 2:
                            cantidadEnemigos = 8;
                            break;
                        case 3:
                            cantidadEnemigos = 12;
                            break;
                        default:
                            System.out.println("Opcion no valida. Seleccionada dificultad Normal");
                            cantidadEnemigos = 8;
                    }

                    System.out.print("Quieres activar los trucos de ver los enemigos? (S/N): ");
                    // next = leer un caracter y toUpperCase = convertirlo en mayusculas -- toLowerCase sería en minusculas
                    // charAt para determinar las variables
                    char activarTrucos = scanner.next().toUpperCase().charAt(0);
                    boolean trucosActivados = activarTrucos == 'S';

                    System.out.print("Quieres jugar con paredes, es decir sin saltos? (S/N): ");
                    char jugarConParedes = scanner.next().toUpperCase().charAt(0);
                    boolean conParedes = jugarConParedes == 'S';

                    Juego juego = new Juego(tamano, cantidadEnemigos, trucosActivados, conParedes);
                    juego.iniciar();
                    break;

                case 2:
                    System.out.println("Adios");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opcion no valida");
            }
        }
    }
}
