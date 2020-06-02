import menus.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menu.setActiveMenu(new menus.RegisterMenu());
        while (Menu.getActiveMenu() != null) {
            Menu.getActiveMenu().processInputCommand(scanner.nextLine());
        }
    }
}
