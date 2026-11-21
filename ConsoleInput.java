import java.util.Scanner;

public final class ConsoleInput {
    private static final Scanner SCANNER = new Scanner(System.in);

    private ConsoleInput() {
    }

    public static Scanner scanner() {
        return SCANNER;
    }
}
