package dk.monopoly;

import dk.monopoly.entities.InfoService;
import dk.monopoly.entities.impls.FieldsInitializer;
import dk.monopoly.entities.impls.MessageBag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FieldsInitializerTest {
    @BeforeEach
    void setUp() {    }
    @AfterEach
    void tearDown() {   }

    @Test
    public void givenOneStringAndVariableName_returnValueByVariableName(){
        FieldsInitializer fieldsInitializer = new FieldsInitializer();
        MessageBag messageBag = new MessageBag();
        List<String> texts = fieldsInitializer.populateFields( messageBag, "DK.txt");
        assertEquals("Du har fundet Tower og får 250 kr, du er rig!", messageBag.towerMessage );
        assertEquals("Du har fundet Crater og får -100 kr, du er ikke rig!", messageBag.craterMessage );
        assertEquals("Du har fundet Crater og får -100 kr, du er ikke rig!", texts.get(1) );
    }

    private List<String> getLines(String filePath) {
        List<String> strings = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader( new FileReader(filePath));
            while (reader.ready()){
                strings.add(reader.readLine());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
    }

    class FakeInfoService extends InfoService {
        @Override
        public void setPoints(int points) { }
        @Override
        public int getScore() {
            return 0;
        }
        @Override
        public String getMessage() {
            return null;
        }
        @Override
        public String getLocation() {
            return null;
        }
    }
}