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
            System.out.println("7. Creare un file HTML");
            System.out.println("8. Uscire");
            System.out.print("Scegli un'opzione: ");
            int scelta = scanner.nextInt();
            scanner.nextLine(); // Consuma il newline

            switch (scelta) {
                case 1:
                    leggiFile(FILE_CSV);
                    break;
                case 2:
                    aggiungiCampi(FILE_CSV);
                    break;
                case 3:
                    calcolaLunghezzaMassima(FILE_CSV);
                    break;
                case 4:
                    rendiDimensioneFissa(FILE_CSV);
                    break;
                case 5:
                    System.out.print("Inserisci il nuovo record (separato da ';'): ");
                    String nuovoRecord = scanner.nextLine();
                    aggiungiRecord(FILE_CSV, nuovoRecord);
                    break;
                case 6:
                    System.out.println("Scrittura completata.");
                    break;
                case 7:
                    creaHtml(FILE_CSV, "output.html");
                    System.out.println("File HTML creato con successo.");
                    break;
                case 8:
                    System.out.println("Uscita dal programma.");
                    return;
                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
        }
    }

    private static void leggiFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    private static void aggiungiCampi(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String header = reader.readLine();
            lines.add(header + ";miovalore;cancellato");
            String line;
            Random random = new Random();
            while ((line = reader.readLine()) != null) {
                int miovalore = 10 + random.nextInt(11); // Genera un numero casuale tra 10 e 20
                lines.add(line + ";" + miovalore + ";false");
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    private static void calcolaLunghezzaMassima(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int maxRecordLength = 0;
            int[] maxFieldLengths = null;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(";");
                if (maxFieldLengths == null) {
                    maxFieldLengths = new int[fields.length];
                }
                maxRecordLength = Math.max(maxRecordLength, line.length());
                for (int i = 0; i < fields.length; i++) {
                    maxFieldLengths[i] = Math.max(maxFieldLengths[i], fields[i].length());
                }
            }

            System.out.println("Lunghezza massima del record: " + maxRecordLength);
            System.out.println("Lunghezza massima di ogni campo:");
            for (int i = 0; i < maxFieldLengths.length; i++) {
                System.out.println("Campo " + (i + 1) + ": " + maxFieldLengths[i]);
            }
        }
    }

    private static void rendiDimensioneFissa(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String header = reader.readLine();
            lines.add(header);
            int[] maxFieldLengths = header.split(";").length > 0 ? new int[header.split(";").length] : null;

            // Calcola la lunghezza massima di ogni campo
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(";");
                for (int i = 0; i < fields.length; i++) {
                    maxFieldLengths[i] = Math.max(maxFieldLengths[i], fields[i].length());
                }
                lines.add(line);
            }

            // Aggiunge spazi per rendere fissa la dimensione
            for (int i = 1; i < lines.size(); i++) {
                String[] fields = lines.get(i).split(";");
                StringBuilder fixedLine = new StringBuilder();
                for (int j = 0; j < fields.length; j++) {
                    fixedLine.append(String.format("%-" + maxFieldLengths[j] + "s", fields[j]));
                    if (j < fields.length - 1) {
                        fixedLine.append(";");
                    }
                }
                lines.set(i, fixedLine.toString());
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    private static void aggiungiRecord(String filePath, String nuovoRecord) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(nuovoRecord);
            writer.newLine();
        }
    }

    private static void creaHtml(String filePath, String outputHtml) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             PrintWriter writer = new PrintWriter(new FileWriter(outputHtml))) {
            writer.println("<!DOCTYPE html>");
            writer.println("<html lang=\"it\">");
            writer.println("<head><title>CSV Visualizzato</title></head>");
            writer.println("<body><table border='1'>");

            String line;
            while ((line = reader.readLine()) != null) {
                writer.println("<tr>");
                for (String field : line.split(";")) {
                    writer.println("<td>" + field + "</td>");
                }
                writer.println("</tr>");
            }

            writer.println("</table></body></html>");
        }
    }
}