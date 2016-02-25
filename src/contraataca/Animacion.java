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
import java.util.ArrayList;


/**
 * Animacion
 *
 * La clase Animacion maneja una serie de imágenes (arrCuadros)
 * y la cantidad de tiempo que se muestra cada cuadro.
 *
 * @author Irvel
 * @author Jorge
 * @version 0.2
 */
public class Animacion{

	private ArrayList<CuadroDeAnimacion> arrCuadros;
	private int iCuadroActual;
	private long iDuracionAnimacion;
	private long iDuracionTotal;


    /**
     * Animacion
     *
     * Metodo constructor usado para crear crear una nueva Animacion vacía
     *
     */
	public Animacion(){
        this.iDuracionTotal = 0;
        inicia();
	}


    /**
     * Animacion
     *
     * Metodo constructor usado para crear una nueva Animacion en base a un
     * arreglo de cuadros. Después de crear, inicializa la animación.
     *
     * @param arrCuadros es el <code>Arraylist</code> de cuadros de tipo
     *                   <code>CuadroDeAnimacion</code> para la animación.
     *
     */
    public Animacion(ArrayList<CuadroDeAnimacion> arrCuadros){
        this.arrCuadros = arrCuadros;
        this.iDuracionTotal = 0;
        for (CuadroDeAnimacion cuaAct: arrCuadros) {
            iDuracionTotal += cuaAct.getTiempoFinal();
        }
        inicia();
    }


    /**
     * sumaCuadro()
     *
     * Metodo que añade un cuadro adicional a la animación con la
     * duración indicada (tiempo que se muestra la imagen).
     *
     * @param imagen es la imagen nueva de tipo <code>Image</code> que será
     *               agregada a la animación.
     * @param duracion es la cantidad de tiempo en milisegundos que la
     *                 imagen a agregar será desplegada.
     *
     */
	public synchronized void sumaCuadro(Image imagen, long duracion){
        iDuracionTotal += duracion;
		arrCuadros.add(new CuadroDeAnimacion(imagen, iDuracionTotal));
	}


    /**
     * inicia()
     *
     * Metodo que inicializa la animación desde el primer cuadro.
     *
     */
	public synchronized void inicia(){
		iDuracionAnimacion = 0;
        iCuadroActual = 0;
	}


    /**
     * actualiza()
     *
     * Metodo que actualiza la imagen (cuadro) actual de la animación, si es
     * necesario de acuerdo al tiempo transcurrido.
     *
     * @param tiempoTranscurrido es la cantidad de tiempo que ha transcurrido
     *                           desde la última vez que se mandó llamar
     *                           actualiza().
     *
     */
	public synchronized void actualiza(long tiempoTranscurrido){
		if (arrCuadros.size() > 1){
			iDuracionAnimacion += tiempoTranscurrido;
			if (iDuracionAnimacion > getCuadro(iCuadroActual).tiempoFinal){
				if(iCuadroActual == arrCuadros.size() - 1){
                    iCuadroActual = 0;
                }
                else{
                    iCuadroActual++;
                }
                iDuracionAnimacion = 0;
			}
		}
	}
	

    /**
     * getImagen()
     *
     * Metodo que captura la imagen actual de la animación. Regresa null
     * si la animación no tiene imágenes.
     *
     * @return Regresa el cuadro actual en el que se encuentra la animacíon
     *         En caso de que la animación no contenga imágenes, se regresa
     *         null.
     *
     */
	public synchronized Image getImagen(){
		if (arrCuadros.size() == 0){
			return null;
		}
		else {
			return getCuadro(iCuadroActual).getImagen();
		}
	}


    /**
     * getCuadro()
     *
     * Metodo que regresa un objeto de tipo <code>CuadroDeAnimacion</code>
     * en la posición especificada.
     *
     *
     * @param i es la posición en el arreglo de cuadros que se quiere accesar.
     * @return el cuadro de animación en la posición i
     *
     */
	private CuadroDeAnimacion getCuadro(int i){
		return arrCuadros.get(i);
	}
		
}