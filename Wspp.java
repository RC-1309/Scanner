import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class Wspp {
    private static class TokenCheckerStat implements CheckToken {
        public boolean isToken(char ch) {
            return Character.isLetter(ch) || Character.getType(ch) == Character.DASH_PUNCTUATION || ch == '\'';
        }
    }
    public static void main(String[] args) {
        try {
            Map<String, IntList> numberOfWords = new LinkedHashMap<>();
            MyScanner in = new MyScanner(args[0], "utf-8", new TokenCheckerStat());
            int idxInText = 1;
            while (in.hasNextToken()) {
                String word = in.nextWord().toLowerCase();
                if (numberOfWords.containsKey(word)) {
                    numberOfWords.get(word).add(idxInText);
                } else {
                    numberOfWords.put(word, new IntList(idxInText));
                }
                idxInText++;
            }
            in.closeFile();
            try {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(args[1]),
                        StandardCharsets.UTF_8
                ))) {
                    for (Map.Entry<String, IntList> entry : numberOfWords.entrySet()) {
                        IntList value = entry.getValue();
                        writer.write(entry.getKey() + " " + value.size());
                        for (int i = 0; i < value.size(); i++) {
                            writer.write(" " + value.get(i));
                        }
                        writer.newLine();
                    }
                }
            } catch (IOException e) {
                System.out.println("Output file: " + e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Input file: " + e.getMessage());
        }
    }
}