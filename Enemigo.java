import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.Random;

/**
 * Write a description of class Enemigo here.
 * 
 * @author (Alejandro Expósito) 
 * @version (a version number or a date)
 */
public class Enemigo extends ImageView
{
    
    private int velocidadEnXEnemigo;
    
    private int velocidadEnYEnemigo;
    
    private int velocidadAleatoria;
    
    private int limiteIzquierdo;
    
    private int limiteDerecho;
    
    private int anchoEscena;
    
    private int altoEscena;
    
    private static final int ANCHO = 30;
    
    private static final int ALTO = 30;
    
    private static final int CONTROLADOR_DE_ESCENA = 40;
    
    public Enemigo(int anchoEscena, int altoEscena)
    {
        Random aleatorio = new Random();
        this.anchoEscena = anchoEscena;
        this.altoEscena = altoEscena;
        velocidadAleatoria = 1 + aleatorio.nextInt(2);
        velocidadEnXEnemigo = velocidadAleatoria;
        velocidadEnYEnemigo = velocidadAleatoria;
        setFitWidth(ANCHO);
        setFitHeight(ALTO);
        setTranslateX(aleatorio.nextInt(anchoEscena - CONTROLADOR_DE_ESCENA));
        setTranslateY(aleatorio.nextInt(30));
        Image image = new Image("enemigo.png");
        setImage(image);
    }
    
    /**
     * Metodo para hacer avanzar la nave en la escena y
     * hacer que rebote en caso de llegar a los limites 
     * de la escena.
     */
    public void moverEnemigo()
    {
        setTranslateX(getTranslateX() + velocidadEnXEnemigo);
        setTranslateY(getTranslateY() + velocidadEnYEnemigo);
                        
        // Controlamos si el enemigo rebota a ziquierda o derecha.
        if (getBoundsInParent().getMinX() <= 0 || getBoundsInParent().getMaxX() >= anchoEscena) {
               velocidadEnXEnemigo = -velocidadEnXEnemigo;                              
        }

        // Conrolamos si el enemigo rebota arriba y abajo.
        if (getBoundsInParent().getMinY() <= 0 || getBoundsInParent().getMaxY() >= altoEscena) {
            velocidadEnYEnemigo = -velocidadEnYEnemigo;
        }
        
    }

}
