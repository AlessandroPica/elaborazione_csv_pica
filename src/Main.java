import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static final String CSV_FILE = "src/pica.csv";
    private static final String[] HEADER = {"URL", "Evento", "Categoria", "Descrizione", "Città", "Indirizzo", "Numero", "Telefono", "Email", "Sito", "Latitudine", "Longitudine", "Periodo", "miovalore", "cancellato"};

    public static void main(String[] args) throws IOException {
        System.out.println("Lettura del file CSV...");
        List<String[]> records = readCSV(CSV_FILE);
        System.out.println("Aggiunta dei campi ai record...");
        addFields(records);
        System.out.println("Calcolo della lunghezza massima dei record...");
        int maxLength = calculateMaxLength(records);
        System.out.println("Aggiunta degli spazi per rendere fissa la dimensione dei record...");
        padRecords(records, maxLength);
        System.out.println("Aggiunta di un nuovo record...");
        addRecord(records, new String[]{"newURL", "newEvento", "newCategoria", "newDescrizione", "newCittà", "newIndirizzo", "newNumero", "newTelefono", "newEmail", "newSito", "newLatitudine", "newLongitudine", "newPeriodo"});
        System.out.println("Scrittura dei record nel file CSV...");
        writeCSV(CSV_FILE, records);
        System.out.println("Operazione completata.");
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

    private static int calculateMaxLength(List<String[]> records) {
        int maxLength = 0;
        for (String[] record : records) {
            int length = String.join(";", record).length();
            if (length > maxLength) {
                maxLength = length;
            }
        }
        return maxLength;
    }

    private static void padRecords(List<String[]> records, int maxLength) {
        for (int i = 0; i < records.size(); i++) {
            String[] record = records.get(i);
            String joinedRecord = String.join(";", record);
            int paddingLength = maxLength - joinedRecord.length();
            if (paddingLength > 0) {
                joinedRecord += " ".repeat(paddingLength);
            }
            records.set(i, joinedRecord.split(";"));
        }
    }

    private static void addRecord(List<String[]> records, String[] newRecord) {
        String[] recordWithFields = new String[newRecord.length + 2];
        System.arraycopy(newRecord, 0, recordWithFields, 0, newRecord.length);
        recordWithFields[newRecord.length] = String.valueOf(10 + new Random().nextInt(11));
        recordWithFields[newRecord.length + 1] = "false";
        records.add(recordWithFields);
    }
}