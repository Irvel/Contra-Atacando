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


/**
 * MaloPersigue
 *
 * Clase derivada de <code>Malo</code> que modela un objeto gráfico del Malo
 * que persigue al jugador.
 *
 * Al mandar llamar al método avanza(), el objeto se moverá hacia el jugador
 * principal, para esto se le proporcionan sus coordenadas cómo parámetros en
 * avanza().
 *
 * @author Irvel
 * @author Jorge
 * @version 0.1
 */
public class MaloPersigue extends Malo{

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
    public MaloPersigue(int iX, int iY, int iVX, int iVY,  Animacion anim) {
        super(iX, iY, iVX, iVY, anim);
    }

    /**
     *
     * avanza()
     *
     * Metodo que avanza a un malo hacia el jugador en las coordenadas recibidas
     *
     */
    public void avanza(int iX, int iY, int iVel){
        if(this.getX() < iX){
            this.setX(this.getX() + iVel);
        }
        else if(this.getX() > iX){
            this.setX(this.getX() - iVel);
        }
        if(this.getX() < iY){
            this.setY(this.getY() + iVel);
        }
        else if(this.getY() > iY){
            this.setY(this.getY() - iVel);
        }
    }
}
