import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

public class Main {
    
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        
        // List <User> users = Database.readUsersFromFile("users.csv");
        // List <Book> books = Database.readBooksFromFile("books.csv");
        // Database.writeBooksToFile(books,"out.csv");
        
        Server.run();
        
        // System.out.println(
        //         Database.convertUserToString(users.get(0))
        // );

        // Database.writeUsersToFile(users, "out.txt");
        
        // Book book = new Book(
        //         "Стань диким!",
        //         "Эрин Хантер",
        //         "Олма-Пресс",
        //         "2003",
        //         "Н/Д",
        //         "Н/Д",
        //         "5-224-04342-5",
        //         "00000001",
        //         "Сказка",
        //         "детская литература, коты-воители, кошки, животные");
        //
        // System.out.println(books.size());
                
    }
    
}