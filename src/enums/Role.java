package enums;

public enum Role {
    USER, ADMIN;
    @Override
    public String toString() {
        return name(); // Returns "USER" or "ADMIN"
    }
}
