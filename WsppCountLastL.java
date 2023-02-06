import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class WsppCountLastL {
    private static class TokenCheckerStat implements CheckToken {
        public boolean isToken(char ch) {
            return Character.isLetter(ch) || Character.getType(ch) == Character.DASH_PUNCTUATION || ch == '\'';
        }
    }
    public static void main(String[] args) {
        try {
            Map<String, Integer> numberOfWords = new LinkedHashMap<>();
            Map<String, IntList> lastIdxWords = new LinkedHashMap<>();
            MyScanner in = new MyScanner(args[0], "utf-8", new TokenCheckerStat());

            while (in.hasNextToken()) {
                Map<String, Integer> wordsInLine = new LinkedHashMap<>();
                int idxInLine = 1;
                int lastLineNumber = in.getLineNumber();

                while (in.hasNextToken() && in.getLineNumber() == lastLineNumber) {
                    String word = in.nextWord().toLowerCase();
                    Integer cnt = numberOfWords.get(word);
                    if (cnt != null) {
                        numberOfWords.put(word, cnt + 1);
                    } else {
                        numberOfWords.put(word, 1);
                    }
                    wordsInLine.put(word, idxInLine);
                    idxInLine++;
                }

                for (Map.Entry<String, Integer> entry : wordsInLine.entrySet()) {
                    String word = entry.getKey();
                    Integer number = entry.getValue();
                    if (lastIdxWords.containsKey(word)) {
                        lastIdxWords.get(word).add(number);
                    } else {
                        lastIdxWords.put(word, new IntList(number));
                    }
                }
            }
            in.closeFile();

            Map<Integer, ArrayList<String>> lastCount = new TreeMap<>();
            for (Map.Entry<String, Integer> entry : numberOfWords.entrySet()) {
                Integer number = entry.getValue();
                String word = entry.getKey();
                lastCount.putIfAbsent(number, new ArrayList<>());
                lastCount.get(number).add(word);
            }

            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter (
                        new FileOutputStream(args[1]),
                        StandardCharsets.UTF_8
                ));
                try {
                    for (Map.Entry<Integer, ArrayList<String>> entry : lastCount.entrySet()) {
                        Integer number = entry.getKey();
                        List<String> words = entry.getValue();
                        for (String word : words) {
                            writer.write(word + " " + number);
                            IntList answer = lastIdxWords.get(word);
                            for (int i = 0; i < answer.size(); i++) {
                                writer.write(" " + answer.get(i));
                            }
                            writer.newLine();
                        }
                    }
                } finally {
                    writer.close();
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