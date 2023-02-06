import java.io.Reader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.File;
import java.lang.StringBuilder;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;

public class MyScanner {
    private static final int BLOCK_SIZE = 1024;
    private int lastIndexInBuffer = 0;
    private final StringBuilder word = new StringBuilder();
    private int read = -1;
    private int numberOfLines = 0;
    private final char[] buffer = new char[BLOCK_SIZE];
    private final Reader reader;
    private static final String LINE_FEED = System.lineSeparator();
    private final CheckToken tokenChecker;

    private static class DefaultTokenChecker implements CheckToken {
        public boolean isToken(char ch) {
            return !Character.isWhitespace(ch);
        }
    }

    MyScanner(String name, String charset, CheckToken checker) throws FileNotFoundException, UnsupportedEncodingException {
        this.reader = new BufferedReader(
            new InputStreamReader (new FileInputStream(name), charset)
        );
        tokenChecker = checker;
    }

    MyScanner(File file, CheckToken checker) throws FileNotFoundException, UnsupportedEncodingException {
        this.reader = new BufferedReader(
            new InputStreamReader (new FileInputStream(file))
        );
        tokenChecker = checker;
    }

    MyScanner(InputStream file, CheckToken checker) throws FileNotFoundException, UnsupportedEncodingException {
        this.reader = new BufferedReader(
            new InputStreamReader (file)
        );
        tokenChecker = checker;
    }

    MyScanner(String text, CheckToken checker) {
        this.reader = new StringReader(text);
        tokenChecker = checker;
    }

    MyScanner(String name, String charset) throws FileNotFoundException, UnsupportedEncodingException {
        this(name, charset, new DefaultTokenChecker());
    }

    MyScanner(String text) throws FileNotFoundException, UnsupportedEncodingException {
        this(text, new DefaultTokenChecker());
    }

    MyScanner(File file) throws FileNotFoundException, UnsupportedEncodingException {
        this(file, new DefaultTokenChecker());
    }

    MyScanner(InputStream file) throws FileNotFoundException, UnsupportedEncodingException {
        this(file, new DefaultTokenChecker());
    }

    public int getLineNumber() {
        return numberOfLines;
    }

    public void closeFile() throws IOException {
        reader.close();
    }

    private boolean checkSymbolInLineFeed(int idx) {
        return LINE_FEED.charAt(idx) == buffer[lastIndexInBuffer];
    }

    private void nextBuffer() throws IOException {
        read = reader.read(buffer);
        lastIndexInBuffer = 0;        
    }

    boolean hasNextToken() throws IOException {
        if (read < 0 || lastIndexInBuffer >= read) {
            nextBuffer();
        }
        int idxInLineFeed = 0;
        while (read >= 0) {
            while (lastIndexInBuffer < read) {
                if (checkSymbolInLineFeed(idxInLineFeed)) {
                    idxInLineFeed++;
                    if (idxInLineFeed == LINE_FEED.length()) {
                        numberOfLines++;
                        idxInLineFeed = 0;
                    }
                }
                if (lastIndexInBuffer < read && tokenChecker.isToken(buffer[lastIndexInBuffer])) {
                    return true;
                }
                lastIndexInBuffer++;
            }
            nextBuffer();
        }
        return false;
    }

    String nextToken() throws IOException {
        if (!hasNextToken()) {
            throw new NoSuchElementException("No elements");
        }
        word.setLength(0);
        while (lastIndexInBuffer < read) {
            while (lastIndexInBuffer < read && tokenChecker.isToken(buffer[lastIndexInBuffer])) {
                word.append(buffer[lastIndexInBuffer]);
                lastIndexInBuffer++;
            }
            if (lastIndexInBuffer < read) {
                break;
            }
            nextBuffer();
        }
        return word.toString();
    }

    int nextInt() throws IOException, InputMismatchException {
        return Integer.parseInt(nextToken());
    }

    String nextWord() throws IOException {
        return nextToken();
    }
}