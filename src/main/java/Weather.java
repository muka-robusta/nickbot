import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    // 9e95290b291170b0db0902528c43f73c
    public static String getWeather(String message, Model model) throws IOException {
        final URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=9e95290b291170b0db0902528c43f73c");
        final Scanner in = new Scanner((InputStream) url.getContent());
        final StringBuffer result = new StringBuffer("");

        while(in.hasNext()) {
            result.append(in.nextLine());
        }

        final JSONObject jsonObject = new JSONObject(result.toString());
        model.setName(jsonObject.getString("name"));

        final JSONObject main = jsonObject.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        final JSONArray weatherJson = jsonObject.getJSONArray("weather");
        for(int i = 0; i < weatherJson.length(); i++) {
            final JSONObject element = weatherJson.getJSONObject(i);
            model.setIcon((String) element.get("icon"));
            model.setMain(element.getString("main"));
        }

        System.out.println(model.toString());
        return model.toString();
    }
}
