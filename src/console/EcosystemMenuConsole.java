package console;

import model.Ecosystem;

import java.util.Scanner;

public class EcosystemMenuConsole {
    private final Scanner console;

    public EcosystemMenuConsole(Scanner console) {
        this.console = console;
    }

    public Ecosystem createEcosystemMenu(){
        System.out.println("Create");
        return null;
    }

    public void ecosystemMenu(Ecosystem ecosystem) {
        System.out.println(ecosystem.getName());
    }

    private int requireNonNegativeInt() {
        int i;
        while(true) {
            if(console.hasNextInt()) {
                i = console.nextInt();
                if (i > 0)
                    return i;
                else
                    System.out.println("Enter positive integer!");
            } else {
                System.out.println("Enter correct integer!");
            }
        }
    }
}
