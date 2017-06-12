import javafx.scene.shape.Rectangle;
import javafx.scene.paint.*;

/**
 * Write a description of class Jugador here.
 * 
 * @author (Alejandro Expósito) 
 * @version (a version number or a date)
 */
public class Bala extends Rectangle
{
    
    private int velocidadBala;
    
     private static final int ANCHO = 5;
    
    private static final int ALTO = 20;
    
    public Bala()
    {
        velocidadBala = -3;
        setWidth(ANCHO);
        setHeight(ALTO);
        setFill(Color.RED);
    }
    
    public void hacerAvanzarBala()
    {
        setTranslateY(getTranslateY() + velocidadBala);
    }
    
}




