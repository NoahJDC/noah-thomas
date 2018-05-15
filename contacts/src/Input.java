
import java.util.Scanner;

public class Input {
    private Scanner scanner;

    public Input() {
        this.scanner = new Scanner(System.in);
    }

    public String getString() {
        return scanner.nextLine();
    }

    public boolean yesNo() {
        String answer = scanner.next();
        return "y".equalsIgnoreCase(answer) || "yes".equalsIgnoreCase(answer);
    }

    public int getInt(int min, int max) {
        int value = getInt();
        if (value < min || value > max) {
            System.out.printf("Enter a number between %d and %d%n", min, max);
            return getInt(min, max);
        }
        return value;

    }

    public int getInt() {
        System.out.println("Please, enter an Integer");
        String s = this.scanner.nextLine();
        int num;
        try {
            num = Integer.valueOf(s);
            return num;
        } catch (NumberFormatException e) {
            System.out.println("That is not a Number.");
            return getInt();
        }
    }

    public double getDouble(double min, double max) {
        double value = getDouble();
        if (value < min || value > max) {
            System.out.printf("Enter a number between %f and %f%n", min, max);
            return getDouble(min, max);
        }
        return value;
    }

    public double getDouble() {
        System.out.println("Please, enter a Double");
        String s = this.scanner.nextLine();
        double num;
        try {
            num = Double.valueOf(s);
            return num;
        } catch (NumberFormatException e) {
            System.out.println("That is not a Number.");
            return getDouble();
        }
    }
}