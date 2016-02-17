/**
 * Personaje
 *
 * Modela la definición de los personajes derivados de Base
 * <code>Base</code>
 *
 * @author XXXXXXX
 * @version 99 
 * @date 99/XXX/99
 */

package primerexamen;

import java.awt.*;

public class Personaje extends Base {
    private int iLowerbound;
    private int iUpperbound;

    /**
     * Personaje
     *
     * Metodo constructor usado para crear el objeto personaje
     * creando el icono a partir de una imagen
     *
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param iVelA es el <code>ancho</code> del objeto.
     * @param iVelB es el <code>Largo</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     *
     */
    public Personaje(int iX, int iY,  Image imaImagen, int iVelA, int iVelB) {
        super(iX, iY, imaImagen);
        this.iVelA = iVelA;
        this.iVelB = iVelB;
    }

    public int getVel(){
        // Elige al azar la velocidad A o la velocidad B
        if((int)(Math.random() * 2) == 0)
            return this.iVelA;
        else
            return this.iVelB;
    }
}