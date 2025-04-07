import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final String FILE_CSV = "src/pica.csv";
    private static final String[] INTESTAZIONE = {"URL", "Evento", "Categoria", "Descrizione", "Città", "Indirizzo", "Numero", "Telefono", "Email", "Sito", "Latitudine", "Longitudine", "Periodo", "miovalore", "cancellato"};

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Leggere il file CSV");
            System.out.println("2. Aggiungere campi ai record");
            System.out.println("3. Calcolare la lunghezza massima dei record");
            System.out.println("4. Aggiungere spazi per rendere fissa la dimensione dei record");
            System.out.println("5. Aggiungere un nuovo record");
            System.out.println("6. Scrivere i record nel file CSV");
            System.out.println("7. Uscire");
            System.out.print("Scegli un'opzione: ");
            int scelta = scanner.nextInt();
            scanner.nextLine(); // Consuma il newline

            switch (scelta) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
        }
    }
}