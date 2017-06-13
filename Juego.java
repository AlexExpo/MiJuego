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
    
    private int contadorDeEnemigos;

    private static final int ANCHO_ESCENA = 600;
    
    private static final int ALTO_ESCENA = 600;
    
    private static boolean annadido = false;
    
    private ArrayList<Bala> balas;
    
    private ArrayList<Enemigo> enemigos;
    
    public Juego()
    {
        tiempoEnSegundos = 0;
        contadorDeBalas = 0;
        contadorDeEnemigos = 0;
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

        Nave nave = new Nave(0, ANCHO_ESCENA);
        contenedor.getChildren().add(nave);
        
        Label tiempoPasado = new Label("0");
        contenedor.getChildren().add(tiempoPasado);

        

        escenario.show();
        
        
        escena.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT) {
                    nave.moverDerecha();
            }
            else if (event.getCode() == KeyCode.LEFT) {
                    nave.moverIzquierda();
            }
            //Cada vez que se pulsa el espacio se crea una bala y se mete a la escena.
            else if (event.getCode() == KeyCode.SPACE) {
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
            
            int minutos = tiempoEnSegundos / 60;
            int segundos = tiempoEnSegundos % 60;
            
            
            //Creo un enemigo cada 10 segundos.
            if (segundos % 10 == 0) {
                Enemigo enemigo = new Enemigo(ANCHO_ESCENA, ALTO_ESCENA);
                enemigos.add(enemigo);
                contenedor.getChildren().add(enemigos.get(contadorDeEnemigos));
                contadorDeEnemigos++;
            }
            
            
            nave.hacerAvanzarNave();
            
            /**
             * Le doy movimineto a las balas y en caso de que 
             * esten fuera de la escena las elimino del ArrayList 
             * y de la escena.
             */
            for (int contador = 0; contador < balas.size(); contador++) {
                balas.get(contador).hacerAvanzarBala();
                if (balas.get(contador).getBoundsInParent().getMaxY() < 0) {
                    contenedor.getChildren().remove(balas.get(contador));
                    balas.remove(balas.get(contador));
                    contadorDeBalas--;
                    contador--;
                }
            }
            
            //Le doy movimineto a los enemigos.
            for (Enemigo enemigoActual : enemigos) {
                enemigoActual.moverEnemigo();
            }
            
            /**
             * Compruebo si una bala impacta contra un enemigo, 
             * y en caso verdadero, elimino a la bala que imapacto 
             * contra el ememigo y al propio enemigo de sus respectivos 
             * ArrayList y de la escena.
             */
            for (int contador = 0; contador < enemigos.size(); contador++) {
                for (int contador2 = 0; contador < balas.size(); contador2++) {
                    if (balas.get(contador2).controlarSiImpactaContraEnemigo(enemigos.get(contador))) {
                        contenedor.getChildren().remove(enemigos.get(contador));
                        enemigos.remove(enemigos.get(contador));
                        contadorDeEnemigos--;
                        contador--;
                        contenedor.getChildren().remove(balas.get(contador2));
                        balas.remove(balas.get(contador2));
                        contadorDeBalas--;
                        contador2--;
                    }
                }
            }
            
            
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








