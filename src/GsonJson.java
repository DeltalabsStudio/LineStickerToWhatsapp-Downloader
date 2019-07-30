

import static java.lang.System.out;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class GsonJson {

	public static String printIntoJSON(StickerPacks stickerpacks) {
		String json = new Gson().toJson(stickerpacks);
		return json;
	}
	
	public static void prettyJsonString(String json) {
		Gson gson_builder = new GsonBuilder()
					       .setPrettyPrinting()
						   .disableHtmlEscaping()
						   .create();
    	JsonParser json_parser = new JsonParser();
    	JsonElement json_element = json_parser.parse(json);
    	String prettyJsonString = gson_builder.toJson(json_element);
    	out.println(prettyJsonString);
	}
}
