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
                    System.out.println("Lettura del file CSV...");
                    List<String[]> records = leggiCSV(FILE_CSV);
                    System.out.println("File CSV letto con successo.");
                    break;
                case 2:
                    System.out.println("Aggiunta dei campi ai record...");
                    aggiungiCampi(records);
                    System.out.println("Campi aggiunti con successo.");
                    break;
                case 3:
                    System.out.println("Calcolo della lunghezza massima dei record...");
                    int lunghezzaMassima = calcolaLunghezzaMassima(records);
                    System.out.println("Lunghezza massima dei record: " + lunghezzaMassima);
                    break;
                case 4:
                    System.out.println("Aggiunta degli spazi per rendere fissa la dimensione dei record...");
                    aggiungiSpazi(records, lunghezzaMassima);
                    System.out.println("Spazi aggiunti con successo.");
                    break;
                case 5:
                    System.out.println("Aggiunta di un nuovo record...");
                    String[] nuovoRecord = chiediNuovoRecord(scanner);
                    aggiungiRecord(records, nuovoRecord);
                    System.out.println("Nuovo record aggiunto con successo.");
                    break;
                case 6:
                    System.out.println("Scrittura dei record nel file CSV...");
                    scriviCSV(FILE_CSV, records);
                    System.out.println("Record scritti con successo.");
                    break;
                case 7:
                    System.out.println("Uscita...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
        }
    }

    private static List<String[]> leggiCSV(String filePath) throws IOException {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                records.add(line.split(";"));
            }
        }
        return records;
    }

    private static void scriviCSV(String filePath, List<String[]> records) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] record : records) {
                bw.write(String.join(";", record));
                bw.newLine();
            }
        }
    }

    private static void aggiungiCampi(List<String[]> records) {
        Random random = new Random();
        for (int i = 1; i < records.size(); i++) {
            String[] record = records.get(i);
            String[] newRecord = new String[record.length + 2];
            System.arraycopy(record, 0, newRecord, 0, record.length);
            newRecord[record.length] = String.valueOf(10 + random.nextInt(11));
            newRecord[record.length + 1] = "false";
            records.set(i, newRecord);
        }
    }

    private static int calcolaLunghezzaMassima(List<String[]> records) {
        int lunghezzaMassima = 0;
        for (String[] record : records) {
            int length = String.join(";", record).length();
            if (length > lunghezzaMassima) {
                lunghezzaMassima = length;
            }
        }
        return lunghezzaMassima;
    }

    private static void aggiungiSpazi(List<String[]> records, int lunghezzaMassima) {
        for (int i = 0; i < records.size(); i++) {
            String[] record = records.get(i);
            String joinedRecord = String.join(";", record);
            int paddingLength = lunghezzaMassima - joinedRecord.length();
            if (paddingLength > 0) {
                joinedRecord += " ".repeat(paddingLength);
            }
            records.set(i, joinedRecord.split(";"));
        }
    }

    private static void aggiungiRecord(List<String[]> records, String[] nuovoRecord) {
        String[] recordConCampi = new String[nuovoRecord.length + 2];
        System.arraycopy(nuovoRecord, 0, recordConCampi, 0, nuovoRecord.length);
        recordConCampi[nuovoRecord.length] = String.valueOf(10 + new Random().nextInt(11));
        recordConCampi[nuovoRecord.length + 1] = "false";
        records.add(recordConCampi);
    }

    private static String[] chiediNuovoRecord(Scanner scanner) {
        String[] nuovoRecord = new String[INTESTAZIONE.length - 2];
        for (int i = 0; i < nuovoRecord.length; i++) {
            System.out.print("Inserisci " + INTESTAZIONE[i] + ": ");
            nuovoRecord[i] = scanner.nextLine();
        }
        return nuovoRecord;
    }
}