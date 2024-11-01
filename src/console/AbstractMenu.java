package console;

import service.EcosystemService;

import java.io.IOException;
import java.util.Scanner;

public abstract class AbstractMenu {
    protected final EcosystemService ecosystemService;
    protected final Scanner console;
    protected final ProcessBuilder flushProcess;

    public AbstractMenu(EcosystemService ecosystemService, Scanner console, ProcessBuilder flushProcess) {
        this.ecosystemService = ecosystemService;
        this.console = console;
        this.flushProcess = flushProcess;
    }

    protected void pressEnterToContinue() {
        System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
            String garbage = console.nextLine();
            if (garbage != null && !garbage.isEmpty())
                console.nextLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void flushConsole() {
        if (flushProcess != null) {
            try {
                flushProcess.start().waitFor();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected int chooseOption(int lastOption) {
        int optionNumber;
        while (true) {
            if (console.hasNextInt()) {
                optionNumber = console.nextInt();
                if (optionNumber > lastOption || optionNumber < 0) {
                    System.out.println("Incorrect option number!");
                    continue;
                }
            } else {
                System.out.println("Enter an option number!");
                console.next();
                continue;
            }
            break;
        }
        return optionNumber;
    }

    protected int requireNonNegativeInt() {
        int i;
        while(true) {
            if(console.hasNextInt()) {
                i = console.nextInt();
                if (i >= 0)
                    return i;
                else
                    System.out.println("Enter positive integer!");
            } else {
                System.out.println("Enter correct integer!");
                console.next();
            }
        }
    }

    protected int requireBounderedInt(int min, int max) {
        int i;
        while(true) {
            if(console.hasNextInt()) {
                i = console.nextInt();
                if (i >= min && i <= max)
                    return i;
                else
                    System.out.println("Enter number between " + min + " and " + max + "!");
            } else {
                System.out.println("Enter correct integer!");
                console.next();
            }
        }
    }

    protected float requireFloat() {
        float f;
        while(true) {
            if(console.hasNextFloat()) {
                return console.nextFloat();
            } else {
                System.out.println("Enter correct float!");
                console.next();
            }
        }
    }

    protected float requireNonNegativeFloat() {
        float f;
        while(true) {
            if(console.hasNextFloat()) {
                f = console.nextFloat();
                if (f >= 0.00)
                    return f;
                else
                    System.out.println("Enter positive float!");
            } else {
                System.out.println("Enter correct float!");
                console.next();
            }
        }
    }

    protected float requireNormalizedValue() {
        float f;
        while(true) {
            if(console.hasNextFloat()) {
                f = console.nextFloat();
                if (f >= 0.00 && f <= 1.00)
                    return f;
                else
                    System.out.println("Enter number between 0,00 and 1,00!");
            } else {
                System.out.println("Enter correct float!");
                console.next();
            }
        }
    }

    protected boolean requireYesOrNo() {
        while (true) {
            String answer = console.next();
            if (answer.toLowerCase().strip().equals("y"))
                return true;
            if (answer.toLowerCase().strip().equals("n"))
                return false;
            System.out.println("Enter 'y' for YES and 'n' for NO");
        }
    }
}
