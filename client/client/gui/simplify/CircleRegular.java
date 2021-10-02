package client.gui.simplify;

import javafx.animation.ScaleTransition;
import javafx.scene.DepthTest;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;
import server.spacemarine.SpaceMarine;

import java.util.Hashtable;
import java.util.Random;

public class CircleRegular {
    Hashtable<String, Double> hueList = new Hashtable<>();
    Hashtable<String, Double> saturationList = new Hashtable<>();
    Hashtable<String, Double> luminanceList = new Hashtable<>();
    public Circle getCircle(SpaceMarine spaceMarine, double width,
                            Stage stage,
                            double k_x, double k_y,
                            double minY, double minX,
                            double maxY, double maxX){

        Circle circle = new Circle();
        //Setting the position of the circle
        double x = (stage.getWidth() - width) / 2;
        double y = stage.getHeight() / 2;

        if (maxX - minX != 0) {
            x = (spaceMarine.getCoordinates().getX() - minX) * k_x;
        }
        if (maxY - minY != 0){
            y = (spaceMarine.getCoordinates().getY() - minY) * k_y;
        }
        circle.setCenterX(width + x + 50);
        circle.setCenterY(stage.getHeight() - y - 450);
        circle.setRadius(5.0f);
        circle.setStrokeWidth(0.5);
        circle.setStroke(Color.valueOf("#000000"));

        circle.setFill(Color.hsb(getHue(spaceMarine), getSaturation(spaceMarine), getLuminance(spaceMarine)));

        //Creating scale Transition
        ScaleTransition scaleTransition = new ScaleTransition();
        //Setting the duration for the transition
        scaleTransition.setDuration(Duration.millis(1000));
        //Setting the node for the transition
        scaleTransition.setNode(circle);

        //Setting the dimensions for scaling
        scaleTransition.setByY(5);
        scaleTransition.setByX(5);

        //Setting the cycle count for the translation
        scaleTransition.setCycleCount(1);

        //Setting auto reverse value to true
        scaleTransition.setAutoReverse(false);

        //Playing the animation
        scaleTransition.play();

        return circle;
    }

    // Оттенок
    private double getHue(SpaceMarine spaceMarine){
        for (String user : hueList.keySet()){
            if (user.equals(spaceMarine.getUser())){
                return hueList.get(user);
            }
        }
        Random random = new Random();
        while (true){
            double hue = random.nextDouble() * 255;
            if (!hueList.contains(hue)){
                hueList.put(spaceMarine.getUser(), hue);
                return hue;
            }
        }
    }

    // Насыщенность
    //1.0 for brilliant, 0.0 for dull
    private double getSaturation(SpaceMarine spaceMarine){
        for (String user : saturationList.keySet()){
            if (user.equals(spaceMarine.getUser())){
                return saturationList.get(user);
            }
        }
        Random random = new Random();
        while (true){
            double saturation = random.nextDouble();
            if ((!saturationList.contains(saturation)) && (saturation > 0.4)){
                saturationList.put(spaceMarine.getUser(), saturation);
                return saturation;
            }
        }
    }

    // Яркость
    //1.0 for brighter, 0.0 for black
    private double getLuminance(SpaceMarine spaceMarine){
        for (String user : luminanceList.keySet()){
            if (user.equals(spaceMarine.getUser())){
                return luminanceList.get(user);
            }
        }
        Random random = new Random();
        while (true){
            double luminance = random.nextDouble();
            if ((!luminanceList.contains(luminance)) && (luminance > 0.4) ){
                luminanceList.put(spaceMarine.getUser(), luminance);
                return luminance;
            }
        }
    }

}
