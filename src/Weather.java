import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Weather {
    public static JSONObject getWeather(){
        String urlString = "https://api.open-meteo.com/v1/forecast?latitude=64.1355&longitude=-21.8954&current=is_day&hourly=rain,snowfall,visibility,diffuse_radiation&timezone=Europe%2FLondon";
        try{
            HttpURLConnection conn = fetchApiResponse(urlString);

            if(conn.getResponseCode() != 200){
                System.out.println("Error: Failed to connect to API");
                return null;
            }
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while(scanner.hasNext()){
                resultJson.append(scanner.nextLine());
            }

            scanner.close();
            conn.disconnect();

            JSONParser parser = new JSONParser();
            JSONObject resultJsonObject = (JSONObject) parser.parse(String.valueOf(resultJson));
              //data hourly
            JSONObject hourly = (JSONObject) resultJsonObject.get("hourly");
            JSONArray time = (JSONArray) hourly.get("time");

            int index = findIndexOfCurrentTime(time);



            JSONArray rainData = (JSONArray) hourly.get("rain");
            double rain = (double) rainData.get(index);

            JSONArray snowData = (JSONArray) hourly.get("snowfall");
            double snowfall = (double) snowData.get(index);

            JSONArray visibilityData = (JSONArray) hourly.get("visibility");
            double visibility = (double) visibilityData.get(index);

            JSONArray radiationData = (JSONArray) hourly.get("diffuse_radiation");
            double diffuse_radiation = (double) radiationData.get(index);



            int probability = totalCalc(rain,snowfall,visibility,diffuse_radiation);

            JSONObject weatherData = new JSONObject();
            weatherData.put("rain", rain);
            weatherData.put("snowfall", snowfall);
            weatherData.put("visibility", visibility);
            weatherData.put("diffuse_radiation",diffuse_radiation );
            weatherData.put("probability", probability);

            return weatherData;


        }catch(Exception e){
            e.printStackTrace();
        }
        return null;

    }
    private static HttpURLConnection fetchApiResponse(String urlString){
        try{
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();
            return conn;
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
    private static int findIndexOfCurrentTime(JSONArray timeList){
        String currentTime = getCurrentTime();

        for(int i =0; i < timeList.size(); i++){
            String time = (String) timeList.get(i);
            if(time.equalsIgnoreCase(currentTime)){
                return i;
            }
        }

        return 0;
    }

    public static String getCurrentTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");
        String formattedDateTime = currentDateTime.format(formatter);
        return formattedDateTime;

    }
        //Calculates northern lights probability
    public static int totalCalc(double rain, double snowfall, double visibility, double diffuse_radiation){
        int Total = 0;
        String periodOfDay = DateTimeFormatter
                .ofPattern("B")
                .format(LocalDateTime.now());
        if(rain == 0){Total++;}
        if(snowfall == 0){Total++;}
        if(visibility > 20000 ){Total++;}
        if(diffuse_radiation > 100){Total++;}
        if(periodOfDay != "at night"){Total=0;}
        return Total;

    }

}
