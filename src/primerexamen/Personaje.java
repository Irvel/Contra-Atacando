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

package primerexamen;

import java.awt.*;


/**
 * Personaje
 *
 * Clase derivada de <code>Base</code> que modela un objeto gráfico y
 * contiene métodos dedicados para regresar una velocidad aleatoria con
 * límites inferior y superior preestablecidos.
 *
 * @author Irvel
 * @version 0.1
 */
public class Personaje extends Base {
    private int iLowerbound;
    private int iUpperbound;


    /**
     * Personaje
     *
     * Metodo constructor usado para crear el objeto personaje con imagen
     * estática y límites de velocidad predefinidos.
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


    /**
     * Personaje
     *
     * Metodo constructor usado para crear el objeto personaje con una
     * animación de la clase <code>Animacion</code> y límites de velocidad
     * predefinidos.
     *
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param iLowerbound es el <code>ancho</code> del objeto.
     * @param iUpperbound es el <code>Largo</code> del objeto.
     * @param anim es la <code>Animacion</code> del objeto.
     *
     */
    public Personaje(int iX, int iY, Animacion anim, int iLowerbound, int
            iUpperbound) {
        super(iX, iY, anim);
        this.iLowerbound = iLowerbound;
        this.iUpperbound = iUpperbound;
    }


    /**
     * getVel()
     *
     * Metodo de acceso que regresa una velocidad aleatoria dentro del rango
     * iUpperbound - iLowerbound.
     *
     * @return un valor entero de velocidad generado de forma
     * aleatoria.
     *
     */
    public int getVel(){
        // Elige al azar una velocidad entre el rango
        return (int)(Math.random() *
                ((iUpperbound + 1  - iLowerbound) + 1) + iLowerbound);
    }
}