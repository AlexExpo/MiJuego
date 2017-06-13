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
        velocidadBala = -5;
        setWidth(ANCHO);
        setHeight(ALTO);
        setFill(Color.RED);
    }
    
    /**
     * Metodo para hacer avanzar la bala en la escena.
     */
    public void hacerAvanzarBala()
    {
        setTranslateY(getTranslateY() + velocidadBala);
    }
    
    /**
     * Metodo que comprueba si una bala impacta contra 
     * un enemigo, es decir, se intercepta. Si una bala 
     * impacta contra un enemigo el metodo devulve true, 
     * en caso contrario devuelve false.
     */
    public boolean controlarSiImpactaContraEnemigo(Enemigo enemigoAComprobar)
    {
        boolean colision = false;
        if (enemigoAComprobar.getBoundsInParent().intersects(getBoundsInParent())) {
            colision = true;
        }
        return colision;
    }
    
}




