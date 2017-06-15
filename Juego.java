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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import java.util.ArrayList;
import java.util.Random;

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
    
    private int enemigosEliminados;
    
    private int oleada;
    
    private ArrayList<Bala> balas;
    
    private ArrayList<Enemigo> enemigos;
    
    private static final int ANCHO_ESCENA = 600;
    
    private static final int ALTO_ESCENA = 600;
    
    private static final int CONTROLADOR_DE_ESCENA = 40;
    
    private static final int CONTROLADOR_MENSAJE_ERROR_X = 220;
    
    private static final int CONTROLADOR_MENSAJE_ERROR_Y = 100;
    
    private static final int NUMERO_MAXIMO_DE_ENEMIGOS_EN_OLEADA = 20;
    
    public Juego()
    {
        tiempoEnSegundos = 0;
        contadorDeBalas = 0;
        contadorDeEnemigos = 0;
        enemigosEliminados = 0;
        oleada = 1;
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
        tiempoPasado.setTextFill(Color.web("#3701E9"));
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
        
        Enemigo enemigo = new Enemigo(ANCHO_ESCENA, ALTO_ESCENA);
        enemigos.add(enemigo);
        contenedor.getChildren().add(enemigos.get(contadorDeEnemigos));
        contadorDeEnemigos++;
        
        Timeline timeline = new Timeline();
        KeyFrame keyframe = new KeyFrame(Duration.seconds(0.01), event -> {
            
            int minutos = tiempoEnSegundos / 60;
            int segundos = tiempoEnSegundos % 60;
            
            //Creo un enemigo cada 4 segundos.
            //if (segundos % 5 == 0) {
                //Enemigo enemigo = new Enemigo(ANCHO_ESCENA, ALTO_ESCENA);
                //Comprobamos que los enemigos que metemos en la escena no se solapan.
                //boolean encontradoEnemigo = false;
                //while (!encontradoEnemigo) {
                    //if (enemigos.size() > 0) {
                        //boolean solapamientoDetectado = false;
                        //int enemigoActual = 0;
                        //while (enemigoActual < enemigos.size() && !solapamientoDetectado) {
                            //if (enemigo.getBoundsInParent().intersects(enemigos.get(enemigoActual).getBoundsInParent())) {
                                //solapamientoDetectado = true;
                                //Random aleatorio = new Random();
                                //enemigo.setTranslateX(CONTROLADOR_DE_ESCENA + aleatorio.nextInt(ANCHO_ESCENA - CONTROLADOR_DE_ESCENA));
                            //}
                            //enemigoActual++;
                        //}
                        //Encontrado enemigo valido.
                        //if (!solapamientoDetectado) {
                            //encontradoEnemigo = true;
                        //}
                    //}
                    //else {
                        //encontradoEnemigo = true;
                    //}
                //}
                //enemigos.add(enemigo);
                //contenedor.getChildren().add(enemigos.get(contadorDeEnemigos));
                //contadorDeEnemigos++;
            //} 
            
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
             * ArrayList y de la escena. Cuando todos los enemigos son 
             * eliminados, se generan mas enemigos dando paso asi a 
             * otra oleada.
             */
            for (int contador = 0; contador < enemigos.size(); contador++) {
                for (int contador2 = 0; contador2 < balas.size(); contador2++) {
                    if (balas.get(contador2).controlarSiImpactaContraEnemigo(enemigos.get(contador))) {
                        contenedor.getChildren().remove(enemigos.get(contador));
                        enemigos.remove(enemigos.get(contador));
                        contadorDeEnemigos--;
                        contador--;
                        contenedor.getChildren().remove(balas.get(contador2));
                        balas.remove(balas.get(contador2));
                        contadorDeBalas--;
                        contador2--;
                        //Meto a la escena un enemigo mas por cada numero de enemigos eliminados.
                        enemigosEliminados++;
                        if (enemigos.size() == 0) {
                            if (oleada >= NUMERO_MAXIMO_DE_ENEMIGOS_EN_OLEADA) {
                                for (int contador3 = 0; contador3 < NUMERO_MAXIMO_DE_ENEMIGOS_EN_OLEADA; contador3++) {
                                    Enemigo enemigoGeneradoPorTerminarOleada = new Enemigo(ANCHO_ESCENA, ALTO_ESCENA);
                                    enemigos.add(enemigoGeneradoPorTerminarOleada);
                                    contenedor.getChildren().add(enemigos.get(contadorDeEnemigos));
                                    contadorDeEnemigos++;
                                }
                            }
                            else {
                                for (int contador4 = 0; contador4 < oleada; contador4++) {
                                    Enemigo enemigoGeneradoPorTerminarOleada = new Enemigo(ANCHO_ESCENA, ALTO_ESCENA);
                                    enemigos.add(enemigoGeneradoPorTerminarOleada);
                                    contenedor.getChildren().add(enemigos.get(contadorDeEnemigos));
                                    contadorDeEnemigos++;
                                }
                                oleada++;
                            }
                        }
                    }
                }
            }
            
            /**
             * Compruebo si algun enemigo colisiona con la nabe, 
             * en caso de no colisionar, no pasa nada. En caso 
             * de colisionar con la nave, crea un mensaje de game 
             * over junto con la puntuacion y el juego termina.
             */
            for (Enemigo enemigoAComprobar : enemigos) {
                if (nave.controlarSiChocaContraEnemigo(enemigoAComprobar)) {
                    Label mensajeGameOver = new Label("GAME OVER...\n" + tiempoPasado.getText() + "\nHas eliminado a " + enemigosEliminados + " enemigo(s).");
                    mensajeGameOver.setTranslateX(escena.getWidth() / 2 - CONTROLADOR_MENSAJE_ERROR_X);
                    mensajeGameOver.setTranslateY(escena.getHeight() / 2 - CONTROLADOR_MENSAJE_ERROR_Y);
                    mensajeGameOver.setTextFill(Color.web("#0A12AC"));
                    mensajeGameOver.setFont(Font.font("Cambria", 30));
                    mensajeGameOver.setLineSpacing(5);
                    mensajeGameOver.setTextAlignment(TextAlignment.CENTER);
                    mensajeGameOver.setScaleX(0.8);
                    mensajeGameOver.setScaleY(0.8);
                    contenedor.getChildren().add(mensajeGameOver);
                    timeline.stop();
                }
            }
            
            
            tiempoPasado.setText("Tiempo transcurrido: " + minutos + ":" + segundos);
            
        }); 
        
        
        timeline.getKeyFrames().add(keyframe);

        timeline.setCycleCount(Timeline.INDEFINITE);

        Button boton = new Button("Empezar a jugar");
        boton.setPrefSize(150, 50);
        boton.setLayoutX(450);
        boton.setLayoutY(0);
        boton.setOnAction(event -> {
            tiempoEnSegundos = 0;
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








