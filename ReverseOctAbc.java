import java.io.IOException;
import java.util.InputMismatchException;

public class ReverseOctAbc {
    public static char letterToNumber(char ch) {
        return (char)(ch - 'a' + '0');
    }

    public static int stringToInt(String newWord) {
        StringBuilder word = new StringBuilder(newWord.toLowerCase());
        boolean hasMinus = false;
        if (word.charAt(0) == '-') {
            hasMinus = true;
            word.deleteCharAt(0);
        }
        int radix = 10, number;
        if (word.charAt(word.length() - 1) == 'o') {
            word.deleteCharAt(word.length() - 1);
            radix = 8;
        }
        for (int i = 0; i < word.length(); i++) {
            char symbol = word.charAt(i);
            if (Character.isLetter(symbol)) {
                word.setCharAt(i, letterToNumber(symbol));
            }
        }
        if (hasMinus) {
            number = -Integer.parseUnsignedInt(word.toString(), radix);
        } else {
            number = Integer.parseUnsignedInt(word.toString(), radix);
        }
        return number;
    }

    public static void main(String[] args) {
        try {
            MyScanner in = new MyScanner(System.in);
            IntList array = new IntList();
            IntList arraySize = new IntList();
            int lastLineNumber = 0;
            int numberOfElements = 0;
            int lineNumber;

            while (in.hasNextToken()) {
                int number = stringToInt(in.nextWord());
                array.add(number);
                lineNumber = in.getLineNumber();
                if (lineNumber > lastLineNumber) {
                    arraySize.change(lastLineNumber, numberOfElements);
                    lastLineNumber = lineNumber;
                    numberOfElements = 0;
                }
                numberOfElements++;
            }
            
            lineNumber = in.getLineNumber();
            arraySize.change(lineNumber, 0);
            arraySize.change(lastLineNumber, numberOfElements);

            for (int i = lineNumber - 1; i >= 0; i--) {
                for (int j = 0; j < arraySize.get(i); j++) {
                    System.out.print(array.pop() + " ");
                }
                System.out.println();
            }
            in.closeFile();
        } catch (IOException | InputMismatchException e) {
            System.out.println(e.getMessage());
        }
    }
}