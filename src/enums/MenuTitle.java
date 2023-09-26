package enums;

public enum MenuTitle {
    
    MAIN("Главное меню"),
    SEARCH("Поиск"),
    SELECT("Выделить книгу для выполнения операций"),
    FILTER_BY_TITLE("Фильтрация по наименованию"),
    FILTER_BY_AUTHOR("Фильтрация по автору"),
    FILTER_BY_ALL("Фильтрация по всем строкам"),
    MANAGE("Управление"),
    CANCEL("Отменить операцию"),
    LIST("Перечень"),
    BACK("Назад"),
    HOME("В главное меню"),
    ADD("Добавить книгу"),
    EDIT("Редактировать запись о книге"),
    REMOVE("Удалить книгу"),
    PRINT_SELECTED("Показать выбранную книгу"),
    EXIT("Выход");
    
    public String getValue() {
        return value;
    }
    
    String value;
    
    MenuTitle(String value) {
        this.value = value;
    }
}
