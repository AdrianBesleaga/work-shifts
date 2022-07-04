package xyz.adrianweb.workshifts.core.validation.guards;

public class TextGuard extends BaseGuard<Text> {

    public TextGuard(Text value) {
        super(value);
    }

    public void againstNullOrWhitespace(String message) {
        against(value::isNullOrWhitespace, message);
    }
}