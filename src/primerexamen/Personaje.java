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
     * @param iLowerbound es el <code>ancho</code> del objeto.
     * @param iUpperbound es el <code>Largo</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     *
     */
    public Personaje(int iX, int iY, Image imaImagen, int iLowerbound, int iUpperbound) {
        super(iX, iY, imaImagen);
        this.iLowerbound = iLowerbound;
        this.iUpperbound = iUpperbound;
    }

    public Personaje(int iX, int iY, Animacion anim, int iLowerbound, int
            iUpperbound) {
        super(iX, iY, anim);
        this.iLowerbound = iLowerbound;
        this.iUpperbound = iUpperbound;
    }

    public int getVel(){
        // Elige al azar una velocidad entre el rango
        return (int)(Math.random() *
                ((iUpperbound + 1  - iLowerbound) + 1) + iLowerbound);
    }
}