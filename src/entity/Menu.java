package entity;

import enums.ActionPermission;
import enums.MenuTitle;
import service.Service;

import java.util.Scanner;
import java.util.Stack;

public class Menu {

    static Scanner scanner = new Scanner(System.in);

    public static int getChoice(int low, int high) {
        int res = 0;
        System.out.printf("Введите число от %d до %d: ", low, high);
        String entry = scanner.nextLine();
        try {
            res = Integer.parseInt(entry);
        } catch (Exception e) {
            System.out.println("Введите правильное число");
            return getChoice(low, high);
        }
        if (res < low || res > high) {
            System.out.println("Введите правильное число");
            return getChoice(low, high);
        } else {
            return res;
        }
    }
    
    public static void run() {
        
        /* COURTESY OF CHAT-GPT */
        
        MenuItem mainMenu = new MenuItem(MenuTitle.MAIN, () -> {});
        MenuItem searchMenu = new MenuItem(MenuTitle.SEARCH, () -> {});
        MenuItem manageMenu = new MenuItem(MenuTitle.MANAGE, () -> {});
        MenuItem listMenu = new MenuItem(MenuTitle.LIST, () -> {
            Service.listBooks(ActionPermission.LIST);});
        MenuItem exitMenu =   new MenuItem(MenuTitle.EXIT, () -> {System.exit(0);});
        
        MenuItem filterByTitleMenu =  new MenuItem(MenuTitle.FILTER_BY_TITLE, () -> {
            Service.filterByTitle(ActionPermission.FILTER_BY_TITLE);});
        MenuItem filterByAuthorMenu = new MenuItem(MenuTitle.FILTER_BY_AUTHOR, () -> {
            Service.filterByAuthor(ActionPermission.FILTER_BY_AUTHOR);});
        MenuItem filterByAllMenu =    new MenuItem(MenuTitle.FILTER_BY_ALL, () -> {
            Service.filterByAll(ActionPermission.FILTER_BY_ALL);});
        
        MenuItem addBookMenu  = new MenuItem(MenuTitle.ADD, () -> {});
        MenuItem editBookMenu  = new MenuItem(MenuTitle.EDIT, () -> {});
        MenuItem removeBookMenu  = new MenuItem(MenuTitle.REMOVE, () -> {});
        
        MenuItem backMenu = new MenuItem(MenuTitle.BACK, () -> {});
        MenuItem homeMenu = new MenuItem(MenuTitle.HOME, () -> {});
        
        MenuItem addBookActionMenu  = new MenuItem(MenuTitle.ADD, () -> {
            Service.addBook(ActionPermission.ADD);});
        MenuItem editBookActionMenu  = new MenuItem(MenuTitle.EDIT, () -> {
            Service.editBook(ActionPermission.EDIT);});
        MenuItem removeBookActionMenu  = new MenuItem(MenuTitle.REMOVE, () -> {
            Service.removeBook(ActionPermission.REMOVE);});
        MenuItem selectBookActionMenu = new MenuItem(MenuTitle.SELECT, () -> {
            Service.selectBook(ActionPermission.SELECT);});
        MenuItem printSelectedBookActionMenu = new MenuItem(MenuTitle.PRINT_SELECTED, () -> {
            Service.printSelectedBook();});
        
        /* ГЛАВНОЕ МЕНЮ */
        mainMenu.addSubMenu(searchMenu);
        mainMenu.addSubMenu(manageMenu);
        mainMenu.addSubMenu(listMenu);
        mainMenu.addSubMenu(exitMenu);
        
        /* Поиск */
        searchMenu.addSubMenu(filterByTitleMenu);
        searchMenu.addSubMenu(filterByAuthorMenu);
        searchMenu.addSubMenu(filterByAllMenu);
        searchMenu.addSubMenu(backMenu);
        searchMenu.addSubMenu(homeMenu);
        
        /* Управление */
        manageMenu.addSubMenu(addBookMenu);
        manageMenu.addSubMenu(editBookMenu);
        manageMenu.addSubMenu(removeBookMenu);
        manageMenu.addSubMenu(printSelectedBookActionMenu);
        manageMenu.addSubMenu(backMenu);
        manageMenu.addSubMenu(homeMenu);
        
        /* Управление - Добавить книгу */
        addBookMenu.addSubMenu(addBookActionMenu);
        addBookMenu.addSubMenu(backMenu);
        addBookMenu.addSubMenu(homeMenu);
        
        /* Управление - Редактировать книгу */
        editBookMenu.addSubMenu(editBookActionMenu);
        editBookMenu.addSubMenu(backMenu);
        editBookMenu.addSubMenu(homeMenu);
        
        /* Управление - Удалить книгу */
        removeBookMenu.addSubMenu(removeBookActionMenu);
        removeBookMenu.addSubMenu(backMenu);
        removeBookMenu.addSubMenu(homeMenu);
        
        /* Поиск - Фильтрация по наименованию */
        filterByTitleMenu.addSubMenu(selectBookActionMenu);
        filterByTitleMenu.addSubMenu(manageMenu);
        filterByTitleMenu.addSubMenu(backMenu);
        filterByTitleMenu.addSubMenu(homeMenu);
        
        /* Поиск - Фильтрация по автору */
        filterByAuthorMenu.addSubMenu(selectBookActionMenu);
        filterByAuthorMenu.addSubMenu(manageMenu);
        filterByAuthorMenu.addSubMenu(backMenu);
        filterByAuthorMenu.addSubMenu(homeMenu);
        
        /* Поиск - Фильтрация по всему */
        filterByAllMenu.addSubMenu(selectBookActionMenu);
        filterByAllMenu.addSubMenu(manageMenu);
        filterByAllMenu.addSubMenu(backMenu);
        filterByAllMenu.addSubMenu(homeMenu);
        
        MenuItem currentMenu = mainMenu;
        Stack <MenuItem> enteredMenus = new Stack <>();
        
        
        while(true) {
            int num = currentMenu.getNumSubmenus();
            if (num > 0) {
                currentMenu.listSubmenus();
                int res = getChoice(1, num);
                MenuItem selectedMenu = currentMenu.getSubmenus().get(res - 1);
                if (selectedMenu.getTitle() == MenuTitle.BACK) {
                    currentMenu = enteredMenus.pop();
                    continue;
                } else if (selectedMenu.getTitle() == MenuTitle.HOME) {
                    currentMenu = mainMenu;
                    continue;
                } else {
                    enteredMenus.push(currentMenu);
                    currentMenu = selectedMenu;
                    currentMenu.select();
                }
            } else {
                currentMenu = enteredMenus.pop();
            }
        }
    }
}
