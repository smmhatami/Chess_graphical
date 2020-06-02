package menus;

public class Menu {
    protected static menus.Menu activeMenu;

    public static void setActiveMenu(menus.Menu activeMenu) {
        menus.Menu.activeMenu = activeMenu;
    }

    public static menus.Menu getActiveMenu() {
        return activeMenu;
    }

    public void processInputCommand(String input) {
    }
}
