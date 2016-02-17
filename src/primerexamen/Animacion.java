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
import java.util.ArrayList;

/**
	La clase Animacion maneja una serie de imágenes (arrCuadros)
	y la cantidad de tiempo que se muestra cada cuadro.
*/

public class Animacion{
	
	private ArrayList<CuadroDeAnimacion> arrCuadros;
	private int iCuadroActual;
	private long iDuracionAnimacion;
	private long iDuracionTotal;
	
	/**
		Crea una nueva Animacion vacía
	*/
	public Animacion(){
        this.iDuracionTotal = 0;
        iniciar();
	}

    /**
     Crea una nueva Animacion en base a un arraylist de cuadros recibido
     */
    public Animacion(ArrayList<CuadroDeAnimacion> arrCuadros){
        this.arrCuadros = arrCuadros;
        this.iDuracionTotal = 0;
        for (CuadroDeAnimacion cuaAct: arrCuadros) {
            iDuracionTotal += cuaAct.getTiempoFinal();
        }
        iniciar();
    }
	
	/**
		Añade una cuadro a la animación con la duración
		indicada (tiempo que se muestra la imagen).
	*/	
	public synchronized void sumaCuadro(Image imagen, long duracion){
        iDuracionTotal += duracion;
		arrCuadros.add(new CuadroDeAnimacion(imagen, iDuracionTotal));
	}
	
	// Inicializa la animación desde el principio. 
	public synchronized void iniciar(){
		iDuracionAnimacion = 0;
        iCuadroActual = 0;
	}
	
	/**
		Actualiza la imagen (cuadro) actual de la animación,
		si es necesario.
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
		Captura la imagen actual de la animación. Regeresa null
		si la animación no tiene imágenes.
	*/
	public synchronized Image getImagen(){
		if (arrCuadros.size() == 0){
			return null;
		}
		else {
			return getCuadro(iCuadroActual).getImagen();
		}
	}


	
	private CuadroDeAnimacion getCuadro(int i){
		return arrCuadros.get(i);
	}
		
}