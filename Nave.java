import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

/**
 * Write a description of class Jugador here.
 * 
 * @author (Alejandro Expósito) 
 * @version (a version number or a date)
 */
public class Nave extends ImageView
{
    
    private int velocidadJugador;
    
    private int limiteIzquierdo;
    
    private int limiteDerecho;
    
    private static final int ANCHO = 20;
    
    private static final int ALTO = 40;
    
    public Nave(int limiteIzquierdo, int limiteDerecho)
    {
        velocidadJugador = 1;
        this.limiteIzquierdo = limiteIzquierdo;
        this.limiteDerecho = limiteDerecho;
        setFitWidth(ANCHO);
        setFitHeight(ALTO);
        setTranslateX(300);
        setTranslateY(550);
        Image image = new Image("nave.png");
        setImage(image);
    }
    
    public void moverDerecha() 
    {
        if (getBoundsInParent().getMaxX() != limiteDerecho) {
            velocidadJugador = 1;
        }
    }
    
    public void moverIzquierda() 
    {
        if (getBoundsInParent().getMinX() != limiteIzquierdo) {
            velocidadJugador = -1;
        }
    }
    
    public void hacerAvanzarNave()
    {
        setTranslateX(getTranslateX() + velocidadJugador);
        if (getBoundsInParent().getMinX() == limiteIzquierdo || getBoundsInParent().getMaxX() == limiteDerecho) {
            velocidadJugador = 0;
        }
    }
    
}




