package entity;

import enums.MenuTitle;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
    private MenuTitle title;
    private MenuAction action;
    private List <MenuItem> submenus;
    
    public List<MenuItem> getSubmenus() {
        return submenus;
    }
    
    public int getNumSubmenus() {
        return submenus.size();
    }
    
    public MenuTitle getTitle() {
        return title;
    }
    
    MenuItem(MenuTitle title, MenuAction action) {
        this.title = title;
        this.submenus = new ArrayList <>();
        this.action = action;
    }
    
    public void listSubmenus () {
        System.out.println();
        for (int i = 0; i < submenus.size(); i++) {
            System.out.printf("%d - %s\n", i + 1, submenus.get(i).getTitle().getValue());
        }
    }
    
    public void addSubMenu(MenuTitle title, MenuAction action) {
        submenus.add(new MenuItem(title, action));
    }
    
    public void addSubMenu(MenuItem menuItem) {
        submenus.add(menuItem);
    }
    
    public void select() {
        action.perform();
    }
}
