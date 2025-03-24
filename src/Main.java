import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static final String CSV_FILE = "src/pica.csv";
    private static final String[] HEADER = {"URL", "Evento", "Categoria", "Descrizione", "Città", "Indirizzo", "Numero", "Telefono", "Email", "Sito", "Latitudine", "Longitudine", "Periodo", "miovalore", "cancellato"};

    public static void main(String[] args) throws IOException {
        List<String[]> records = readCSV(CSV_FILE);
        addFields(records);
        writeCSV(CSV_FILE, records);
    }

    private static List<String[]> readCSV(String filePath) throws IOException {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                records.add(line.split(";"));
            }
        }
        return records;
    }

    private static void writeCSV(String filePath, List<String[]> records) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] record : records) {
                bw.write(String.join(";", record));
                bw.newLine();
            }
        }
    }

    private static void addFields(List<String[]> records) {
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
}