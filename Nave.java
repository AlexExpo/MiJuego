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
    
    private int velocidadNave;
    
    private int limiteIzquierdo;
    
    private int limiteDerecho;
    
    private static final int ANCHO = 20;
    
    private static final int ALTO = 40;
    
    public Nave(int limiteIzquierdo, int limiteDerecho)
    {
        velocidadNave = 2;
        this.limiteIzquierdo = limiteIzquierdo;
        this.limiteDerecho = limiteDerecho;
        setFitWidth(ANCHO);
        setFitHeight(ALTO);
        setTranslateX(300);
        setTranslateY(550);
        Image image = new Image("nave.png");
        setImage(image);
    }
    
    /**
     * Metodo para hacer avanzar la nave en la escena a la derecha
     * comprobando que no se salga de la escena.
     */
    public void moverDerecha() 
    {
        if (getBoundsInParent().getMaxX() != limiteDerecho) {
            velocidadNave = 2;
        }
    }
    
    /**
     * Metodo para hacer avanzar la nave en la escena a la izquierda
     * comprobando que no se salga de la escena.
     */
    public void moverIzquierda() 
    {
        if (getBoundsInParent().getMinX() != limiteIzquierdo) {
            velocidadNave = -2;
        }
    }
    
    /**
     * Metodo para hacer avanzar la nave en la escena y en caso 
     * de estar en los limites de la escena interrumpir la 
     * velocidad y parar la nave.
     */
    public void hacerAvanzarNave()
    {
        setTranslateX(getTranslateX() + velocidadNave);
        if (getBoundsInParent().getMinX() == limiteIzquierdo || getBoundsInParent().getMaxX() == limiteDerecho) {
            velocidadNave = 0;
        }
    }
    
    /**
     * Metodo que comprueba si una nave impacta contra 
     * un enemigo, es decir, se intercepta. Si una nave
     * impacta contra un enemigo el metodo devulve true, 
     * en caso contrario devuelve false.
     */
    public boolean controlarSiChocaContraEnemigo(Enemigo enemigoAComprobar)
    {
        boolean colision = false;
        if (enemigoAComprobar.getBoundsInParent().intersects(getBoundsInParent())) {
            colision = true;
        }
        return colision;
    }
    
}




