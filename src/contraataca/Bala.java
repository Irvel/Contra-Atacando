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
 * Bala
 *
 * Clase derivada de <code>Base</code> que modela un objeto gráfico del una
 * bala que dispara el jugador.
 *
 * @author Irvel
 * @author Jorge
 * @version 0.1
 */
public class Bala extends Base {

    /**
     * Malo
     *
     * Metodo constructor usado para crear un objeto Bala con coordenadas,
     * imagen y orientación predefinidos.
     * La orientación se indica con el tipo de bala.
     *  - Si el tipo de bala es A, la orientación se establece como 135º
     *  - Si el tipo de bala es S, la orientación se establece como 45º
     *  - Si el tipo de bala es N, la orientación se establece como 90º
     *
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     *
     */
    public Bala (int iX, int iY, Image imaImagen, char cTipo){
        super(iX,iY,imaImagen);
        this.setVelX(0);
        this.setVelY(1);

        if (cTipo == 'A'){
            this.setVelX(-1);
            this.setVelY(-1);
        }
        else if (cTipo == 'S'){
            this.setVelX(1);
            this.setVelY(-1);
        }
        else if (cTipo == 'N'){
                this.setVelX(0);
                this.setVelY(-1);
            }
    }

}
