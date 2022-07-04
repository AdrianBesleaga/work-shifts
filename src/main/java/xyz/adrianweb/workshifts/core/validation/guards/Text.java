package xyz.adrianweb.workshifts.core.validation.guards;

public record Text(String value) {

    public static Text of(String value) {
        return new Text(value);
    }

    public boolean isNullOrWhitespace() {
        return value == null || value.trim().equals("");
    }

    @Override
    public String toString() {
        return value();
    }
}
