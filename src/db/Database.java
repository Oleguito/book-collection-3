package db;

import settings.Settings;
import entity.Book;
import entity.Credentials;
import entity.User;
import enums.Role;
import exceptions.InvalidFileLineNumArgumentsException;
import exceptions.InvalidFileLineUserRoleException;
import settings.Settings;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    
    public static List<User> users = readUsersFromFile(Settings.usersFile);
    public static List<Book> books = readBooksFromFile(Settings.booksFile);
    
    public static Book selectedBook = null;
    
    static List <User> readUsersFromFile(String filename) {
        List<User> res = new ArrayList <>();
        String line;
        FileReader fr;
        BufferedReader br;
        try {
            fr = new FileReader(filename);
            br = new BufferedReader(fr);
            while((line = br.readLine()) != null) {
                res.add(convertStringToUser(line));
            }
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException("Файл не существует либо ошибка чтения", e);
        }
        return  res;
    }
    
    static void writeUsersToFile(List<User> users, String filename) {
        String line;
        FileWriter fw;
        BufferedWriter bw;
        try {
            fw = new FileWriter(filename);
            bw = new BufferedWriter(fw);
            for(User user : users) {
                bw.write(convertUserToString(user));
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException("Файл не существует либо ошибка записи", e);
        }
    }
    
    private static User convertStringToUser(String string) {
        String[] split = string.split(";");
        if (split.length != 4) {
            throw new InvalidFileLineNumArgumentsException("Количество разделителей в считываемом файле неверное");
        }
        User user = new User();
        Credentials credentials = new Credentials(split[1], split[2]);

        Role role = switch (split[3]) {
            case "USER" -> Role.USER;
            case "ADMIN" -> Role.ADMIN;
            default -> throw new InvalidFileLineUserRoleException("Неверно задана роль в строке в считываемом файле");
        };

        user.setName(split[0]);
        user.setCredentials(credentials);
        user.setRole(role);
        
        return user;
    }
    
    static String convertUserToString(User user) {
        return String.format(
                "%s;%s;%s;%s\n",
                user.getName(),
                user.getCredentials().getLogin(),
                user.getCredentials().getPassword(),
                user.getRole()
        );
    }
    
    public static List <Book> readBooksFromFile(String filename) {
        List<Book> res = new ArrayList <>();
        String line;
        FileReader fr;
        BufferedReader br;
        try {
            fr = new FileReader(filename);
            br = new BufferedReader(fr);
            br.readLine();
            while((line = br.readLine()) != null) {
                res.add(convertStringToBook(line));
            }
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException("Файл не существует либо ошибка чтения", e);
        }
        return  res;
    }
    
    static Book convertStringToBook(String line) {
        String[] split = line.split(";");
        if(split.length != 10) {
            throw new IllegalArgumentException("Строки в файле для чтения книг должны иметь по десять элементов, разделенных точкой с запятой");
        }
        return new Book(split);
    }
    
    public static void writeBooksToFile(List<Book> books, String filename) {
        String line;
        FileWriter fw;
        BufferedWriter bw;
        try {
            fw = new FileWriter(filename);
            bw = new BufferedWriter(fw);
            bw.write(csvTitleLine());
            for(Book book : books) {
                bw.write(convertBookToString(book));
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException("Файл не существует либо ошибка записи", e);
        }
    }
    
    static String convertBookToString(Book book) {
        return String.format(
                "%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;\n",
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getReleaseYear(),
                book.getGenre(),
                book.getIsbn(),
                book.getTags(),
                book.getLocation(),
                book.getPath(),
                book.getArticle()
        );
    }
    
    private static String csvTitleLine() {
        return "Наименование;Автор;Издательство;Год выпуска;Жанр;ISBN;Теги;Расположение;Путь;Артикул;\n";
    }
}
