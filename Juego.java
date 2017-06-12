import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.util.ArrayList;

/**
 * Write a description of class Juego here.
 * 
 * @author (Alejandro Expósito) 
 * @version (a version number or a date)
 */
public class Juego extends Application
{

    private int tiempoEnSegundos;
    
    private int contadorDeBalas;

    private static final int ANCHO_ESCENA = 600;
    
    private static final int ALTO_ESCENA = 600;
    
    private static boolean annadido = false;
    
    private ArrayList<Bala> balas;
    
    private ArrayList<Enemigo> enemigos;
    
    /**
     * Constructor for objects of class Juego
     */
    public Juego()
    {
        tiempoEnSegundos = 0;
        contadorDeBalas = 0;
        balas = new ArrayList<>();
        enemigos = new ArrayList<>();
    }

    public static void main(String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage escenario)
    {
        Group contenedor = new Group();
        
        Scene escena = new Scene(contenedor, ANCHO_ESCENA, ALTO_ESCENA);
        escenario.setTitle("Juego Alex Expo");
        escenario.setScene(escena);
        
        Image image = new Image("fondojuego.jpg");
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
        contenedor.getChildren().add(iv1);

        Nave nave = new Nave(0, (int) escena.getWidth());
        contenedor.getChildren().add(nave);
        
        Label tiempoPasado = new Label("0");
        contenedor.getChildren().add(tiempoPasado);

        

        escenario.show();
        
        
        escena.setOnKeyPressed(event2 -> {
            if (event2.getCode() == KeyCode.RIGHT) {
                    nave.moverDerecha();
            }
            else if (event2.getCode() == KeyCode.LEFT) {
                    nave.moverIzquierda();
            }
            else if (event2.getCode() == KeyCode.SPACE) {
                Bala bala = new Bala();
                balas.add(bala);
                balas.get(contadorDeBalas).setTranslateY(nave.getBoundsInParent().getMinY());
                balas.get(contadorDeBalas).setTranslateX(nave.getBoundsInParent().getMaxX() - 10);
                contenedor.getChildren().add(balas.get(contadorDeBalas));
                contadorDeBalas++;
            }
        });
        
        
        Timeline timeline = new Timeline();
        KeyFrame keyframe = new KeyFrame(Duration.seconds(0.01), event -> {
            
            nave.hacerAvanzarNave();
            
            for (Bala balaActual : balas) {
                balaActual.hacerAvanzarBala();
                if (balaActual.getBoundsInParent().getMaxY() < 0) {
                    contenedor.getChildren().remove(balaActual);
                }
            }
            
            int minutos = tiempoEnSegundos / 60;
            int segundos = tiempoEnSegundos % 60;
            tiempoPasado.setText(minutos + ":" + segundos);          
            
        });
        
        timeline.getKeyFrames().add(keyframe);

        timeline.setCycleCount(Timeline.INDEFINITE);

        Button boton = new Button("Empezar a jugar");
        boton.setPrefSize(150, 50);
        boton.setLayoutX(450);
        boton.setLayoutY(0);
        boton.setOnAction(event -> {
            timeline.play();
            contenedor.getChildren().remove(boton);
        });
        contenedor.getChildren().add(boton);
        
        
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                tiempoEnSegundos++;
            }                        
        };
        Timer timer = new Timer();
        timer.schedule(tarea, 0, 1000);
        
        
    }
    
}








