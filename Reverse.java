import java.io.IOException;
import java.util.InputMismatchException;

public class Reverse {
    public static void main(String[] args) {
        try {
            MyScanner in = new MyScanner(System.in);
            IntList array = new IntList();
            IntList arraySize = new IntList();
            int lastLineNumber = 0;
            int numberOfElements = 0;
            int lineNumber;

            while (in.hasNextToken()) {
                int number = in.nextInt();
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
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
    }
}