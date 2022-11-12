import java.util.Scanner;
import java.util.Set;

public class Stroka {

    private static final Set<Character> vow = Set.of(
            'а', 'е', 'ё', 'и', 'о', 'у', 'ы', 'э', 'ю', 'я'
    );

    public static void str_parse(String s) {
        int vowels = 0, consonants = 0;
        for (int i = 0; i < s.length(); ++i) {
            if (vow.contains(s.charAt(i))) {
                vowels++;
            } else if (s.charAt(i) != 'ь' && s.charAt(i) != 'ъ'){
                consonants++;
            }
        }
        System.out.println("Гласных: " + vowels + ", согласных: " + consonants);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        str_parse(str);
    }
}
