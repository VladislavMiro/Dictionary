import com.company.model.DataBaseManger;
import com.company.model.Word;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class DataBaseManagerTests {

    private void connect() throws SQLException, ClassNotFoundException {
        String url = "jdbc:postgresql://localhost/dictionary";
        String username = "dictadmin";
        String password = "943221";
        DataBaseManger.getDataBaseManager().setConfig(url, username, password);
        DataBaseManger.getDataBaseManager().connect();
    }

    private void disconnect() throws SQLException {
        DataBaseManger.getDataBaseManager().disconnect();
    }

    @Test
    public void connectionTest() {
        assertDoesNotThrow(this::connect);
    }

    @Test
    public void disconnectionTest() {
        assertDoesNotThrow(() -> {
            connect();
            disconnect();
        });
    }

    @Test
    public void selectRequestTest() {

        List<String> testInput = Arrays.asList("Мул", "авто", "АВТОМОБИЛЬ", "мОлОт");
        assertDoesNotThrow(() -> {
            connect();

            for (String key : testInput) {
                ArrayList<Word> words = DataBaseManger.getDataBaseManager().selectRequest(key);

                for (Word word: words) {
                    System.out.println(word.getId() + ") " + word.getWord() + ", " + word.getDescription());
                }
            }

            disconnect();
        });

    }

    @Test
    public void insertRequestTest() {
        ArrayList<Word> testInput = new ArrayList<>();

        for (int i=0; i<4; i++) {
            testInput.add(new Word("testWord"+ i, "exampleText"+ i));
        }

        assertDoesNotThrow(() -> {
            connect();
            for (Word newWord: testInput) {
                DataBaseManger.getDataBaseManager().insertRequest(newWord);
            }

            ArrayList<Word> words = DataBaseManger.getDataBaseManager().selectRequest("test");

            for (Word word: words) {
                System.out.println(word.getId() + ") " + word.getWord() + ", " + word.getDescription());
            }

            disconnect();
        });

    }

    @Test
    public void deleteRequest() {

        assertDoesNotThrow(() -> {
            connect();
            ArrayList<Word> testInput = DataBaseManger.getDataBaseManager().selectRequest("test");

            System.out.println("Записи для удаления:");

            for (Word word: testInput) {
                System.out.println(word.getId() + ") " + word.getWord() + ", " + word.getDescription());
            }

            for (Word word: testInput) {
                DataBaseManger.getDataBaseManager().deleteRequest(word);
            }

            testInput = DataBaseManger.getDataBaseManager().selectRequest("test");

            if (testInput.isEmpty()) {
                System.out.println("Данные удалены");
            }

            disconnect();
        });
    }

}
