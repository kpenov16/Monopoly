package dk.monopoly;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldsInitializer {

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

    public List<String> populateFields(Object object, String filePath) {
        List<String> lines = getLines(filePath);
        List<String> texts = new ArrayList<>();
        for(String str : lines){
            String[] strings = str.trim().split("=",2);
            if(object instanceof MessageBag){
                MessageBag messageBag = (MessageBag)object;
                Field[] fields = messageBag.getClass().getFields();
                for (Field f : fields){
                    if( f.getName().equals(strings[0]) ){
                        if( f.getType().equals(String.class) ){
                            try {
                                f.set( messageBag, strings[1]);
                                f.setAccessible(true);
                                texts.add( (String)f.get(messageBag) );
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        }
        return texts;
    }
}
