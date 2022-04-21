import java.text.Normalizer;
import java.text.Normalizer.Form;

public class StringUtils {



    public String removeAccents(String text) {
        return text == null ? null :
                Normalizer.normalize(text, Form.NFD)
                        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

}