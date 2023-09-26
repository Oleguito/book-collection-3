import java.util.Scanner;
import java.util.regex.Pattern;

public class Book {
    
    private String title;
    private String author;
    private String publisher;
    private String releaseYear;
    private String location; // Стеллаж
    private String path; // Файл
    private String isbn;
    private String article; // Скорее всего индекс/ключ в структуре данных
    private String genre;
    private String tags;
    
    /* Это тоже можно в файл настроек по идее */
    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MAX_AUTHOR_LENGTH = 100;
    private static final int MAX_PUBLISHER_LENGTH = 100;
    private static final int     RELEASE_YEAR_LENGTH = 4;
    private static final int MAX_LOCATION_LENGTH = 100;
    private static final int MAX_PATH_LENGTH = 500;
    private static final int MAX_ISBN_LENGTH = 20;
    private static final int MAX_ARTICLE_LENGTH = 20;
    private static final int MAX_GENRE_LENGTH = 250;
    private static final int MAX_TAGS_LENGTH = 1000;
    
    private static final int CURRENT_YEAR = Settings.CURRENT_YEAR;
    
    static Scanner scanner = new Scanner(System.in);
    
    public Book(String title, String author, String publisher, String releaseYear, String location, String path, String isbn, String article, String genre, String tags) {
        setTitle(title);
        setAuthor(author);
        setPublisher(publisher);
        setReleaseYear(releaseYear, CURRENT_YEAR);
        setLocation(location);
        setPath(path);
        setIsbn(isbn);
        setArticle(article);
        setGenre(genre);
        setTags(tags);
        // this.scanner = scanner;
    }
    
    public Book(String[] csvLineSplit) {
        if (csvLineSplit.length != 10) {
            throw new IllegalArgumentException("Массив для создания книги должен иметь 10 элементов");
        }
        setTitle(csvLineSplit[0]);
        setAuthor(csvLineSplit[1]);
        setPublisher(csvLineSplit[2]);
        setReleaseYear(csvLineSplit[3], CURRENT_YEAR);
        setGenre(csvLineSplit[4]);
        setIsbn(csvLineSplit[5]);
        setTags(csvLineSplit[6]);
        setLocation(csvLineSplit[7]);
        setPath(csvLineSplit[8]);
        setArticle(csvLineSplit[9]);
    }
    
    public boolean containsInAnyRecord (String substring) {
        substring = substring.toLowerCase();
        if (title.toLowerCase().contains(substring)) return true;
        if (author.toLowerCase().contains(substring)) return true;
        if (publisher.toLowerCase().contains(substring)) return true;
        if (releaseYear.toLowerCase().contains(substring)) return true;
        if (genre.toLowerCase().contains(substring)) return true;
        if (isbn.toLowerCase().contains(substring)) return true;
        if (tags.toLowerCase().contains(substring)) return true;
        if (location.toLowerCase().contains(substring)) return true;
        if (path.toLowerCase().contains(substring)) return true;
        if (article.toLowerCase().contains(substring)) return true;
        return false;
    }
    
    public boolean edit() {
        if(!editTitle()) return false;
        if(!editAuthor()) return false;
        if(!editPublisher()) return false;
        if(!editReleaseYear()) return false;
        if(!editGenre()) return false;
        if(!editIsbn()) return false;
        if(!editTags()) return false;
        if(!editLocation()) return false;
        if(!editPath()) return false;
        if(!editArticle()) return false;
        return true;
    }
    
    public boolean editLocation() {
        try {
            System.out.print("Введите располжение книги в зале или нажмите Enter для отмены: ");
            String entry = scanner.nextLine();
            if (entry.isBlank() || entry.isEmpty()) return false;
            setLocation(entry);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return editLocation();
        }
    }
    
    public boolean editTags() {
        try {
            System.out.println("НЕ ИСПОЛЬЗУЙТЕ \";\" ПРИ ВВОДЕ!");
            System.out.print("Введите список тегов через запятую или нажмите Enter для отмены: ");
            String entry = scanner.nextLine();
            if (entry.isBlank() || entry.isEmpty()) return false;
            setTags(entry);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return editTags();
        }
    }
    
    public boolean editPath() {
        try {
            System.out.print("Введите путь к книге в файловой системе или нажмите Enter для отмены: ");
            String entry = scanner.nextLine();
            if (entry.isBlank() || entry.isEmpty()) return false;
            setPath(entry);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return editPath();
        }
    }
    
    public boolean editIsbn() {
        try {
            System.out.print("Введите код ISBN книги или нажмите Enter для отмены: ");
            String entry = scanner.nextLine();
            if (entry.isBlank() || entry.isEmpty()) return false;
            setIsbn(entry);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return editIsbn();
        }
    }
    
    public boolean editArticle() {
        try {
            System.out.print("Введите артикул книги в каталоге или нажмите Enter для отмены: ");
            String entry = scanner.nextLine();
            if (entry.isBlank() || entry.isEmpty()) return false;
            setArticle(entry);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return editArticle();
        }
    }
    
    public boolean editGenre() {
        try {
            System.out.println("НЕ ИСПОЛЬЗУЙТЕ \";\" ПРИ ВВОДЕ!");
            System.out.print("Введите список жанров через запятую или нажмите Enter для отмены: ");
            String entry = scanner.nextLine();
            if (entry.isBlank() || entry.isEmpty()) return false;
            setGenre(entry);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return editGenre();
        }
    }
    
    public boolean editPublisher() {
        try {
            System.out.print("Введите название издательства или нажмите Enter для отмены: ");
            String entry = scanner.nextLine();
            if (entry.isBlank() || entry.isEmpty()) return false;
            setPublisher(entry);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return editPublisher();
        }
    }
    
    public boolean editAuthor() {
        try {
            System.out.println("НЕ ИСПОЛЬЗУЙТЕ \";\" ПРИ ВВОДЕ!");
            System.out.println("В целях экономии экранного пространства рекомендуется записывать");
            System.out.println("авторов в таком виде: \"Фамилия ИО\", например: \"Пушкин АС, Лермонтов МЮ\"");
            System.out.print("Введите список авторов через запятую или нажмите Enter для отмены: ");
            String entry = scanner.nextLine();
            if (entry.isBlank() || entry.isEmpty()) return false;
            setAuthor(entry);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return editAuthor();
        }
    }
    
    public boolean editTitle() {
        try {
            System.out.print("Введите наименование книги или нажмите Enter для отмены: ");
            String entry = scanner.nextLine();
            if (entry.isBlank() || entry.isEmpty()) return false;
            setTitle(entry);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return editTitle();
        }
    }
    
    public boolean editReleaseYear() {
        try {
            System.out.print("Введите значение года выпуска или нажмите Enter для отмены: ");
            String entry = scanner.nextLine();
            if (entry.isBlank() || entry.isEmpty()) return false;
            setReleaseYear(entry, CURRENT_YEAR);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return editReleaseYear();
        }
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        if(title.length() == 0 || title.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException("Значение наименования книги слишком короткое или слишком длинное");
        }
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        if(author.length() == 0 || author.length() > MAX_AUTHOR_LENGTH) {
            throw new IllegalArgumentException("Значение автора книги слишком короткое или слишком длинное");
        }
        this.author = author;
    }
    
    public String getPublisher() {
        return publisher;
    }
    
    public void setPublisher(String publisher) {
        if(publisher.length() == 0 || publisher.length() > MAX_PUBLISHER_LENGTH) {
            throw new IllegalArgumentException("Значение издательства книги слишком короткое или слишком длинное");
        }
        this.publisher = publisher;
    }
    
    public String getReleaseYear() {
        return releaseYear;
    }
    
    public void setReleaseYear(String releaseYear, int currentYear) {
        
        if(releaseYear.length() != RELEASE_YEAR_LENGTH) {
            throw new IllegalArgumentException("Формат года выпуска книги должен быть 4 символа");
        }
        
        String pattern = "\\d{4}";
        if(!Pattern.matches(pattern, releaseYear)) {
            throw new IllegalArgumentException("Неверный символ в формате года");
        }
        
        if(
                Integer.parseInt(releaseYear) < 1900 ||
                Integer.parseInt(releaseYear) > currentYear
        ) {
            throw new IllegalArgumentException("Значение года вне допустимых пределов");
        }
        
        this.releaseYear = releaseYear;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        if(location.length() == 0 || location.length() > MAX_LOCATION_LENGTH) {
            throw new IllegalArgumentException("Значение места хранения книги слишком короткое или слишком длинное");
        }
        this.location = location;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        if(path.length() == 0 || path.length() > MAX_PATH_LENGTH) {
            throw new IllegalArgumentException("Значение пути к файлу книги слишком короткое или слишком длинное");
        }
        this.path = path;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        if(isbn.length() == 0 || isbn.length() > MAX_ISBN_LENGTH) {
            throw new IllegalArgumentException("Значение ISBN книги слишком короткое или слишком длинное");
        }
        String pattern = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$";
        if(!Pattern.matches(pattern, isbn)) {
            throw new IllegalArgumentException("Неверный формат ISBN");
        }
        this.isbn = isbn;
    }
    
    public String getArticle() {
        return article;
    }
    
    public void setArticle(String article) {
        if(article.length() == 0 || article.length() > MAX_ARTICLE_LENGTH) {
            throw new IllegalArgumentException("Значение артикула книги слишком короткое или слишком длинное");
        }
        String pattern = "^\\d+$";
        if(!Pattern.matches(pattern, article)) {
            throw new IllegalArgumentException("Неверный формат артикула");
        }
        this.article = article;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        if(genre.length() == 0 || genre.length() > MAX_GENRE_LENGTH) {
            throw new IllegalArgumentException("Значение списка жанров книги слишком короткое или слишком длинное");
        }
        if(genre.contains(";")) {
            throw new IllegalArgumentException("Список жанров не должен содержать \";\"");
        }
        this.genre = genre;
    }
    
    public String getTags() {
        return tags;
    }
    
    public void setTags(String tags) {
        if(tags.length() == 0 || tags.length() > MAX_TAGS_LENGTH) {
            throw new IllegalArgumentException("Значение списка тегов книги слишком короткое или слишком длинное");
        }
        if(tags.contains(";")) {
            throw new IllegalArgumentException("Значение списка тегов не должно содержать символа \";\"!");
        }
        this.tags = tags;
    }
    
    public void printHorizontal() {
        System.out.println(toStringHorizontal());
    }
    
    public String toStringHorizontal() {
        return String.format(
                "%s %s %s %s %s %s",
                withinLength(title, 20),
                withinLength(author, 30),
                withinLength(publisher, 20),
                withinLength(releaseYear, 4),
                withinLength(isbn, 15),
                withinLength(article, 30)
                // withinLength(genre, 30)
                // withinLength(tags, 50),
                // withinLength(location, 30),
                // withinLength(path, 30)
        );
    }
    
    public String withinLength(String value, int length) {
        String result;
        if(value.length() <= length) {
            StringBuilder s = new StringBuilder();
            int diff = length - value.length();
            for(int i = 0; i < diff; i++) {s.append(' ');}
            result = value + s;
            // System.out.print(result.length() + " ");
            return value + s;
        } else {
            result = value.substring(0, length - 3) + "...";
            // System.out.print(result.length() + " ");
            return result;
        }
    }
}
