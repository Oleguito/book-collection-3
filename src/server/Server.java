package server;

import db.Database;
import entity.Book;
import entity.Menu;
import entity.User;
import enums.ActionPermission;
import enums.Role;
import exceptions.InvalidCredentialsException;
import exceptions.InattentiveProgrammerException;
import settings.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    
    public static User currentUser = null;
    /* При выполнении любого поиска будет заполняться */
    private static List<Book> searchResult = null;
    private static Scanner scanner = new Scanner(System.in);
    private static Book selectedBook = null;
    
    public static void run() {
        currentUser = authenticate(); // Database.users.get(1); // authenticate();
        if(currentUser == null) {
            throw new InvalidCredentialsException("Пользователь не найден. Завершение работы.");
        }
        System.out.printf("Вы вошли под логином: %s. Ваша роль: %s\n",
                currentUser.getCredentials().getLogin(),
                String.valueOf(currentUser.getRole()));
        Menu.run();
    };
    
    private static User authenticate () {
        System.out.println("Введите данные для доступа");
        System.out.println("Введите логин");
        String login = scanner.nextLine().trim();
        System.out.println("Введите пароль");
        String password = scanner.nextLine().trim();
        User resultUser = null;
        for (User user : Database.users) {
            if(
                    user.getCredentials().getLogin().equals(login) &&
                    user.getCredentials().getPassword().equals(password)
            ) {
                resultUser = user;
                break;
            }
        }
        return resultUser;
    }
    
    public static void listBooks(ActionPermission actionPermission) {
        if(userCanDo(actionPermission)) {
            for (Book book : Database.books) {
                book.printHorizontal();
            }
        } else {
            System.out.println("Error 401: Unauthorized");
        }
    }
  
    public static void addBook(ActionPermission actionPermission) {
        if(userCanDo(actionPermission)) {
            String[] prompts = {
                    "",
                    "Введите значения автора через запятую или пустую строку для отмены операции"
            };
            
            Book bookToFill = new Book (
                    "Стань диким!",
                    "Эрин Хантер",
                    "Олма-Пресс",
                    "2003",
                    "Н/Д",
                    "Н/Д",
                    "5-224-04342-5",
                    "00000001",
                    "Сказка",
                    "детская литература, коты-воители, кошки, животные");
            String almostFileLine = "";
            String entry;
            
            if(bookToFill.edit()) {
                Database.books.add(bookToFill);
                System.out.println("Книга добавлена в каталог");
                Database.writeBooksToFile(Database.books, Settings.booksFile);
                System.out.println("Книга сохранена");
            } else {
                System.out.println("Операция отменена");
            }

        } else {
            System.out.println("Error 401: Unauthorized");
        }
    }
    
    private static String getStringFromUser() {
        String entry;
        if (!(entry = scanner.nextLine()).equals("")) {
            return entry;
        } else return null;
    }
    
    private static boolean userCanDo(ActionPermission actionPermission) {
        for (Role role : actionPermission.getValues()) {
            if (currentUser.getRole().equals(role)) {
                return true;
            }
        }
        return false;
    }
    
    public static void filterByTitle(ActionPermission actionPermission) {
        if(userCanDo(actionPermission)) {
            System.out.println("Введите подстроку, которая должна содержаться в наименовании книги");
            System.out.println("Поиск нечувствителен к регистру");
            String entry = scanner.nextLine().trim();
            System.out.printf("Вы ввели: %s\n", entry);
            List <Book> filteredList = new ArrayList<>();
            for (Book book : Database.books) {
                if (book.getTitle().toLowerCase().contains(entry.toLowerCase())) {
                    filteredList.add(book);
                }
            }
            searchResult = filteredList;
            printSearchResult();
        } else {
            System.out.println("Error 401 - Unauthorized");
        }
    }
    
    public static void filterByAuthor(ActionPermission actionPermission) {
        if(userCanDo(actionPermission)) {
            System.out.println("Введите подстроку, которая должна содержаться в списке авторов книги");
            System.out.println("Поиск нечувствителен к регистру");
            String entry = scanner.nextLine().trim();
            System.out.printf("Вы ввели: %s\n", entry);
            List <Book> filteredList = new ArrayList<>();
            for (Book book : Database.books) {
                if (book.getAuthor().toLowerCase().contains(entry.toLowerCase())) {
                    filteredList.add(book);
                }
            }
            searchResult = filteredList;
            printSearchResult();
        } else {
            System.out.println("Error 401 - Unauthorized");
        }
    }
    
    public static void filterByAll(ActionPermission actionPermission) {
        if(userCanDo(actionPermission)) {
            System.out.println("Введите подстроку, которая должна содержаться в любой строке в записи книги");
            System.out.println("`Поиск нечувствителен к регистру");
            String entry = scanner.nextLine().trim();
            System.out.printf("Вы ввели: %s\n", entry);
            List <Book> filteredList = new ArrayList<>();
            for (Book book : Database.books) {
                if (book.containsInAnyRecord(entry)) {
                    filteredList.add(book);
                }
            }
            searchResult = filteredList;
            printSearchResult();
        } else {
            System.out.println("Error 401 - Unauthorized");
        }
    }
    
    static void printSearchResult () {
        if (searchResult != null) {
            System.out.println("\n---------------- Результаты поиска ----------------");
            for (int i = 0; i < searchResult.size(); i++) {
                System.out.printf("%d - %s\n", i + 1, searchResult.get(i).toStringHorizontal());
            }
            System.out.println("---------------------------------------------------");
        } else {
            System.out.println("Поиск не дал результатов");
        }
    }
    
    public static void selectBook(ActionPermission actionPermission) {
        if(userCanDo(actionPermission)) {
            if (searchResult != null) {
                System.out.println("\nВведите номер книги, которую следует выделить");
                int choice = Menu.getChoice(1, searchResult.size());
                selectedBook = searchResult.get(choice - 1);
                System.out.println("Вы выбрали книгу номер " + choice);
                System.out.println("Книга успешно выделена");
                System.out.println("Воспользуйтесь меню управления для совершения операций");
            } else {
                System.out.println("К сожалению, последний поиск не дал результатов");
                System.out.println("Попытайтесь выполнить поиск еще раз");
            }
        } else {
            System.out.println("Error 401 - Unauthorized");
        }
    }
    
    public static void editBook(ActionPermission actionPermission) {
        if (userCanDo(actionPermission)) {
            if(selectedBook != null) {
                System.out.println("Выбранная книга:");
                selectedBook.printHorizontal();
                System.out.println("Выберите цифру, соответствующую желаемому действию");
                System.out.println("    1 - Редактировать наименование");
                System.out.println("    2 - Редактировать список авторов");
                System.out.println("    3 - Редактировать список издательств");
                System.out.println("    4 - Редактировать год выпуска");
                System.out.println("    5 - Редактировать жанр");
                System.out.println("    6 - Редактировать ISBN");
                System.out.println("    7 - Редактировать список тегов");
                System.out.println("    8 - Редактировать расположение в книжном зале");
                System.out.println("    9 - Редактировать путь в файловой системе");
                System.out.println("   10 - Редактировать артикул в каталоге");
                System.out.println("Enter - Отменить операцию");
                int choice = 0;
                int low = 1;
                int high = 10;
                String entry = scanner.nextLine();
                if(entry.isEmpty() || entry.isBlank()) {
                    System.out.println("Отмена операции");
                } else {
                    try {
                        choice = Integer.parseInt(entry);
                    } catch (Exception e) {
                        choice = Menu.getChoice(low, high);
                    }
                    if(choice < 1 || choice > high) choice = Menu.getChoice(low, high);
                    switch (choice) {
                        case 1:
                            if(selectedBook.editTitle()) {
                                System.out.println("Операция выполнена");
                            } else {
                                System.out.println("Операция отменена");
                            }
                            break;
                        case 2:
                            if(selectedBook.editAuthor()) {
                                System.out.println("Операция выполнена");
                            } else {
                                System.out.println("Операция отменена");
                            }
                            break;
                        case 3:
                            if(selectedBook.editPublisher()) {
                                System.out.println("Операция выполнена");
                            } else {
                                System.out.println("Операция отменена");
                            }
                            break;
                        case 4:
                            if(selectedBook.editReleaseYear()) {
                                System.out.println("Операция выполнена");
                            } else {
                                System.out.println("Операция отменена");
                            }
                            break;
                        case 5:
                            if(selectedBook.editGenre()) {
                                System.out.println("Операция выполнена");
                            } else {
                                System.out.println("Операция отменена");
                            }
                            break;
                        case 6:
                            if(selectedBook.editIsbn()) {
                                System.out.println("Операция выполнена");
                            } else {
                                System.out.println("Операция отменена");
                            }
                            break;
                        case 7:
                            if(selectedBook.editTags()) {
                                System.out.println("Операция выполнена");
                            } else {
                                System.out.println("Операция отменена");
                            }
                            break;
                        case 8:
                            if(selectedBook.editLocation()) {
                                System.out.println("Операция выполнена");
                            } else {
                                System.out.println("Операция отменена");
                            }
                            break;
                        case 9:
                            if(selectedBook.editPath()) {
                                System.out.println("Операция выполнена");
                            } else {
                                System.out.println("Операция отменена");
                            }
                            break;
                        case 10:
                            if(selectedBook.editArticle()) {
                                System.out.println("Операция выполнена");
                            } else {
                                System.out.println("Операция отменена");
                            }
                            break;
                        default:
                            throw new InattentiveProgrammerException("Если вы видите эту надпись, значит эту программу написал невнимательный программист");
                    }
                }
            } else {
                System.out.println("К сожалению, книга еще не выбрана");
                System.out.println("Воспользуйтесь поиском для выбора книги");
            }
        } else {
            System.out.println("Error 401 - Unauthorized");
        }
    }
    
    public static void printSelectedBook() {
        if(selectedBook != null) {
            System.out.println("Выбранная книга:");
            selectedBook.printHorizontal();
        } else {
            System.out.println("Книга не выбрана");
        }
        
    }
    
    public static void removeBook(ActionPermission actionPermission) {
        if (userCanDo(actionPermission)) {
            Database.books.remove(selectedBook);
            System.out.println("Выбранная книга удалена");
            Database.writeBooksToFile(Database.books, Settings.booksFile);
            System.out.println("Изменения сохранены");
        } else {
            System.out.println("Error 401 - Unauthorized");
        }
    }
}
