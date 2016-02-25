/*
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Irvel Nduva, Jorge Vazquez
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package contraataca;

import java.awt.*;


/**
 * Malo
 *
 * Clase derivada de <code>Base</code> que modela un objeto gráfico y
 * contiene métodos dedicados para regresar una velocidad aleatoria con
 * límites inferior y superior preestablecidos.
 *
 * Al actualizar su posición mediante el método avanza(), el objeto se moverá
 * hacia abajo.
 *
 * @author Irvel
 * @author Jorge
 * @version 0.2
 */
public class Malo extends Base {
    // El factor de aumento que se utiliza para aumentar la velocidad del malo
    private final int iFACTORAUMENTO = 2;

    /**
     * Malo
     *
     * Metodo constructor usado para crear el objeto personaje con imagen
     * estática y límites de velocidad predefinidos.
     *
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     *
     */
    public Malo(int iX, int iY, Image imaImagen) {
        super(iX, iY, imaImagen);
    }


    /**
     * Malo
     *
     * Metodo constructor usado para crear el objeto personaje con una
     * animación de la clase <code>Animacion</code> y límites de velocidad
     * predefinidos.
     *
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param anim es la <code>Animacion</code> del objeto.
     *
     */
    public Malo(int iX, int iY, int iVX, int iVY,  Animacion anim) {
        super(iX, iY, iVX, iVY, anim);
    }


    /**
     * aumentaVel()
     *
     * Metodo que aumenta la velocidad actual del objeto malo.
     *
     */
    public void aumentaVel(){
        this.setVelY(this.getVelY() + iFACTORAUMENTO);
    }


    /**
     * 
     * avanza()
     * 
     * Metodo que avanza a un malo hacia abajo
     * 
     */
    public void avanza(int iVelocidad){
        this.setY(this.getY() + iVelocidad);
    }
}