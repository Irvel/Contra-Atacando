import java.awt.*;

/**
 * Created by Irvel on 2/7/16.
 */
public class CuadroDeAnimacion {
    Image imagen;
    long tiempoFinal;

    public CuadroDeAnimacion(){
        this.imagen = null;
        this.tiempoFinal = 0;
    }

    public CuadroDeAnimacion(Image imagen, long tiempoFinal){
        this.imagen = imagen;
        this.tiempoFinal = tiempoFinal;
    }

    public Image getImagen(){
        return imagen;
    }

    public long getTiempoFinal(){
        return tiempoFinal;
    }

    public void setImagen (Image imagen){
        this.imagen = imagen;
    }

    public void setTiempoFinal(long tiempoFinal){
        this.tiempoFinal = tiempoFinal;
    }
}
