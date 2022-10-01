//----------------------------------------------------------------------------------------------------------------------

package hac.Classes;

//----------------------------------------------------------------------------------------------------------------------

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

//----------------------------------------------------------------------------------------------------------------------

public class Data {

    private String question;
    private List<Option> options;

    //------------------------------------------------------------------------------------------------------------------

    public Data(final String filePath) {

        options = new ArrayList<>();

        if(filePath != null)
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filePath), StandardCharsets.UTF_8))) {

                String line;
                while ((line = reader.readLine()) != null)
                    if(question == null)
                        question = line;
                    else
                        options.add(new Option(line));

            } catch (IOException ignored) { }
    }

    //------------------------------------------------------------------------------------------------------------------

    public boolean vote(String optionId) {
        try {
            options.get(Integer.parseInt(optionId)).addVote();
            return true;
        } catch(Exception ignored) {}

        return false;
    }

    //------------------------------------------------------------------------------------------------------------------

    public JsonObject getData() {

        final JsonArrayBuilder options = Json.createArrayBuilder();
        for (Option option : this.options)
            options.add(option.getOptionsAndVotes());

        return Json.createObjectBuilder().add("question", this.question).add("options", options).build();
    }

    //------------------------------------------------------------------------------------------------------------------

    public boolean ok() {
        return question == null || options.size() < 2;
    }

    //------------------------------------------------------------------------------------------------------------------

}

//----------------------------------------------------------------------------------------------------------------------