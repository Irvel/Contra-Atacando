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
 * Jugador
 *
 * Clase derivada de <code>Base</code> que modela el objeto gráfico del
 * jugador principal. Esta posee métodos para avanzar a la derecha y avanzar
 * a la izquierda.
 *
 * TODO: implementar métodos para que el jugador avance hacia la derecha o
 *       hacia la izquierda
 *
 * @author Irvel
 * @author Jorge
 * @version 0.1
 */
public class Jugador extends Base {
    /**
     * Jugador
     *
     * Metodo constructor usado para crear un objeto Jugador con imagen estática
     * y velocidad predefinida.
     *
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param iVX es la <code>velocidad en x</code> del objeto.
     * @param iVY es la <code>velocidad en y</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     *
     */
    public Jugador(int iX, int iY, int iVX, int iVY,  Image imaImagen) {
        super(iX, iY, iVX, iVY, imaImagen);
    }

    public void avanzaDerecha(){
        this.setX(this.getX() + this.getVelX());
    }

    public void avanzaIzquierda(){
        this.setX(this.getX() - this.getVelX());
    }
}
