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
 * CuadroDeAnimacion
 *
 * La clase CuadroDeAnimacion maneja objetos que almacenan una imágen
 * estática de la clase <code>Image</code> y un valor numérico en una
 * variable de tipo <code>long</code>. El valor numérico representa  la
 * duración por la que será desplegada la imagen en la animación
 *
 * @author Irvel
 * @version 0.1
 */
public class CuadroDeAnimacion {
    Image imagen;
    long tiempoFinal;


    /**
     * CuadroDeAnimacion
     *
     * Metodo constructor usado para crear un objeto CuadroDeAnimacion vacío
     * con duración de cero e imagen nula.
     *
     */
    public CuadroDeAnimacion(){
        this.imagen = null;
        this.tiempoFinal = 0;
    }


    /**
     * CuadroDeAnimacion
     *
     * Metodo constructor usado para crear un objeto CuadroDeAnimacion con
     * una imagen y tiempoFinal predefinidos.
     *
     * @param imagen es la imágen estática de la clase <code>Image</code> del
     *               objeto.
     * @param tiempoFinal es la duración en milisegundos para ese cuadro
     *
     */
    public CuadroDeAnimacion(Image imagen, long tiempoFinal){
        this.imagen = imagen;
        this.tiempoFinal = tiempoFinal;
    }


    /**
     * getImagen()
     *
     * Metodo de acceso que regresa la imagen almacenada en el objeto
     *
     * @return imagen es la <code>Image</code> del objeto.
     *
     */
    public Image getImagen(){
        return imagen;
    }


    /**
     * getTiempoFinal()
     *
     * Metodo de acceso que regresa el tiempo final almacenado en el objeto
     *
     * @return tiempoFinal, el cual es la duración en milisegundos del cuadro.
     *
     */
    public long getTiempoFinal(){
        return tiempoFinal;
    }


    /**
     * setImagen()
     *
     * Metodo modificador usado para cambiar la imagen del objeto
     *
     * @param imagen es la imagen nueva de tipo <code>Image</code> para
     *               almacenar en el cuadro
     *
     */
    public void setImagen (Image imagen){
        this.imagen = imagen;
    }


    /**
     * setTiempoFinal()
     *
     * Metodo modificador usado para cambiar la duración en milisegundos del
     * objeto
     *
     * @param tiempoFinal es la nueva duración para el cuadro.
     *
     */
    public void setTiempoFinal(long tiempoFinal){
        this.tiempoFinal = tiempoFinal;
    }
}
