import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class NorthernLightsGUI extends JFrame {
    private JSONObject weatherData;

     public NorthernLightsGUI() {
        super("Northern Lights App");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 650);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(Color.black);

        Components();
    }

    private void Components(){
         //   Search box
         //JTextField searchBox = new JTextField();
         //searchBox.setBounds(15,15,351,45);
         //searchBox.setFont(new Font("Dialog", Font.PLAIN,24 ));
         //add(searchBox);

        //  Text
        //JLabel titleText = new JLabel("Northern Lights Tonight");
        //titleText.setBounds(80,15,351,45);
        //titleText.setFont(new Font("Title", Font.PLAIN,24 ));
        //titleText.setForeground(Color.white);
        //add(titleText);

        // Title
        JLabel title = new JLabel(loadImage("src/assets/title.png"));
        title.setBounds(-5,3,450,80);
        add(title);

          //A picture of northern lights
        JLabel lights = new JLabel(loadImage("src/assets/lights2.png"));
        lights.setBounds(250,50,100,100);
        add(lights);

        //  Location, doesn't change
        JLabel location = new JLabel("Location: Reykjavik");
        location.setBounds(60,60,351,45);
        location.setFont(new Font("Title", Font.PLAIN,14 ));
        location.setForeground(Color.white);
        add(location);

        weatherData = Weather.getWeather();
        double visibility = (double) weatherData.get("visibility");
        JLabel visibilityText = new JLabel("Visibility: "+visibility +" m");
        visibilityText.setBounds(60,80,351,45);
        visibilityText.setFont(new Font("Title", Font.PLAIN,14 ));
        visibilityText.setForeground(Color.white);
        add(visibilityText);

        double rain = (double) weatherData.get("rain");
        JLabel rainText = new JLabel("Rain: "+rain +" mm");
        rainText.setBounds(60,100,351,45);
        rainText.setFont(new Font("Title", Font.PLAIN,14 ));
        rainText.setForeground(Color.white);
        add(rainText);

        double snowfall = (double) weatherData.get("snowfall");
        JLabel snowText = new JLabel("Snowfall: "+snowfall +" cm");
        snowText.setBounds(60,120,351,45);
        snowText.setFont(new Font("Title", Font.PLAIN,14 ));
        snowText.setForeground(Color.white);
        add(snowText);

        double diffuse_radiation= (double) weatherData.get("diffuse_radiation");
        JLabel radiationText= new JLabel("Diffuse solar radiation: "+diffuse_radiation +" W/m2");
        radiationText.setBounds(60,140,351,45);
        radiationText.setFont(new Font("Title", Font.PLAIN,14 ));
        radiationText.setForeground(Color.white);
        add(radiationText);


        JLabel totalText= new JLabel("Northern lights probability at this time");
        totalText.setBounds(100,230,351,45);
        totalText.setFont(new Font("Title", Font.PLAIN,14 ));
        totalText.setForeground(Color.white);
        add(totalText);

        JLabel gauge = new JLabel(loadImage("src/assets/gauge1.png"));
        gauge.setBounds(90,300,250,250);
        add(gauge);

        JLabel attribution= new JLabel("vecteezy.com/emiltimplaru");
        attribution.setBounds(170,540,351,45);
        attribution.setFont(new Font("Title", Font.PLAIN,6 ));
        attribution.setForeground(Color.darkGray);
        add(attribution);


        int totalNum = (int) weatherData.get("probability");

        switch(totalNum){
            case 0,1:
                gauge.setIcon(loadImage("src/assets/gauge1.png"));
                break;

            case 2,3:
                gauge.setIcon(loadImage("src/assets/gauge2.png"));
                break;

            case 4:
                gauge.setIcon(loadImage("src/assets/gauge3.png"));
                break;
        }







    }
    private ImageIcon loadImage(String resourcePath){
         try{
             BufferedImage image = ImageIO.read(new File(resourcePath));

             return new ImageIcon(image);
         }catch(IOException e){
             e.printStackTrace();
         }
         System.out.println("image not found");
         return null;
    }


}
