package ir.ac.kntu;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Scanner;

public class DrawShapeFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Level:");
        int level = scanner.nextInt();
        System.out.print("SideLength:");
        int sideLength = scanner.nextInt();

        /* TEST:
        int level = 5;
        int sideLength = 400;
        */
        drawSierpinskiTriangle(root, new Point(50+sideLength/2, 50), level,
                sideLength);
        drawFractal2(root, new Point(50+sideLength+50,50), sideLength-50, level);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void drawSierpinskiTriangle(Group root, Point startPoint,
                                              int level, int sideLength){
        final double sin30 = Math.sin(Math.toRadians(30));
        final double cos30 = Math.cos(Math.toRadians(30));

        if( sideLength<=0 || level<=0){
            return;
        }

        Polyline bounds = new Polyline(
                startPoint.x, startPoint.y,
                startPoint.x+sideLength*sin30, startPoint.y+sideLength*cos30,
                startPoint.x-sideLength*sin30, startPoint.y+sideLength*cos30,
                startPoint.x, startPoint.y
        );
        root.getChildren().add(bounds);

        int subside = sideLength/2;
        double[] vertexes = {
                startPoint.x, startPoint.y+2*subside*cos30,
                startPoint.x+subside*sin30 , startPoint.y+subside*cos30,
                startPoint.x-subside*sin30 , startPoint.y+subside*cos30,
                startPoint.x, startPoint.y+2*subside*cos30
        };
        Polyline bigTriangle = new Polyline(vertexes);
        if( sideLength>=100 ){
            bigTriangle.setFill(javafx.scene.paint.Color.BLACK);
        }else{
            bigTriangle.setFill(Color.RED);
        }
        root.getChildren().add(bigTriangle);

        drawSierpinskiTriangle(root, startPoint, level-1, subside);
        drawSierpinskiTriangle(root,new Point
                ((int)vertexes[2],(int)vertexes[3]), level-1, subside);
        drawSierpinskiTriangle(root, new Point
                ((int)vertexes[4],(int)vertexes[5]), level-1, subside);
    }

    public static void drawFractal2(Group root, Point startPoint, int size,
                                    int level){
        if( level<=0 ){
            return;
        }

        for(int i=startPoint.x ; i<size+startPoint.x ; i+=10){
            Line line = new Line(
                    i, startPoint.y, size+startPoint.x,
                    i+startPoint.y-startPoint.x
            );
            line.setStroke(Color.rgb(50, 150, 150));
            root.getChildren().add(line);
        }
        for(int j=startPoint.y ; j<size+startPoint.y ; j+=10){
            Line line = new Line(
                    size+startPoint.x, j,
                    size+startPoint.x-j+startPoint.y, size+startPoint.y
            );
            line.setStroke(Color.rgb(50, 150, 150));
            root.getChildren().add(line);
        }
        for(int i=size+startPoint.x ; i>startPoint.x ; i-=10){
            Line line = new Line(
                    i, size+startPoint.y,
                    startPoint.x, i+startPoint.y-startPoint.x
            );
            line.setStroke(Color.rgb(50, 150, 150));
            root.getChildren().add(line);
        }
        for(int j=size+startPoint.y ; j>startPoint.y ; j-=10){
            Line line = new Line(
                    startPoint.x, j,
                    size+startPoint.x-j+startPoint.y, startPoint.y
            );
            line.setStroke(Color.rgb(50, 150, 150));
            root.getChildren().add(line);
        }

        drawFractal2(root, new Point((int)(startPoint.x+size*0.25),
                (int)(startPoint.y+size*0.25)), size/2, level-1);
    }
}
