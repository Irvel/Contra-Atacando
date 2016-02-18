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

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Base {

    private int iX;                 // Posicion en x.
    private int iY;                 // Posicion en y.
    private int iVelX;              // Velocidad en x
    private int iVelY;              // Velocidad en y
    private int iWidth;             // Ancho del objeto
    private int iHeight;            // Alto del objeto
    private Image imaImagen;        // Imagen actual del objeto
    private Animacion aniPrincipal; // Animación del objeto

    /**
     * Base
     * 
     * Metodo constructor usado para crear el objeto animal
     * creando el icono a partir de una imagen
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param iVX es la <code>velocidad en x</code> del objeto.
     * @param iVY es la <code>velocidad en y</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     * 
     */
    public Base(int iX, int iY, int iVX, int iVY,  Image imaImagen) {
        this.iX = iX;
        this.iY = iY;
        this.iVelX = iVX;
        this.iVelY = iVY;

        // Extrae el ancho y alto de la imagen para las variables miembro
        this.imaImagen = imaImagen;
        ImageIcon imiImagen = new ImageIcon(imaImagen);
        this.iWidth = imiImagen.getIconWidth();
        this.iHeight = imiImagen.getIconHeight();
    }


    /**
     * Base
     *
     * Método constructor usado para crear un objeto Base con imagen estática
     * y velocidad cero.
     *
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     *
     */
    public Base(int iX, int iY,  Image imaImagen) {
        this.iX = iX;
        this.iY = iY;
        this.iVelX = 0;
        this.iVelY = 0;

        // Extrae el ancho y alto de la imagen para las variables miembro
        this.imaImagen = imaImagen;
        ImageIcon imiImagen = new ImageIcon(imaImagen);
        this.iWidth = imiImagen.getIconWidth();
        this.iHeight = imiImagen.getIconHeight();
    }


    /**
     * Base
     *
     * Metodo constructor usado para crear un objeto Base con una animación
     * de la clase <code>Animación</code> y velocidad cero.
     *
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param anim es la <code>Animacion</code> del objeto.
     *
     */
    public Base(int iX, int iY,  Animacion anim) {
        this.iX = iX;
        this.iY = iY;
        this.iVelX = 0;
        this.iVelY = 0;
        this.imaImagen = anim.getImagen();

        // Extrae el ancho y alto de la animacion para las variables miembro
        ImageIcon imiImagen = new ImageIcon(anim.getImagen());
        this.iWidth = imiImagen.getIconWidth();
        this.iHeight = imiImagen.getIconHeight();
        aniPrincipal = anim;
    }

    
    /**
     * setX
     * 
     * Metodo modificador usado para cambiar la posicion en x del objeto
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * 
     */
    public void setX(int iX) {
        this.iX = iX;
    }

    /**
     * getX
     * 
     * Metodo de acceso que regresa la posicion en x del objeto 
     * 
     * @return iX es la <code>posicion en x</code> del objeto.
     * 
     */
    public int getX() {
            return iX;
    }

    /**
     * setY
     * 
     * Metodo modificador usado para cambiar la posicion en y del objeto 
     * 
     * @param iY es la <code>posicion en y</code> del objeto.
     * 
     */
    public void setY(int iY) {
            this.iY = iY;
    }

    /**
     * getY
     *
     * Metodo de acceso que regresa la posicion en y del objeto
     *
     * @return posY es la <code>posicion en y</code> del objeto.
     *
     */
    public int getY() {
        return iY;
    }

    public int getVelX() {
        return iVelX;
    }

    public void setVelX(int iVelX) {
        this.iVelX = iVelX;
    }

    public int getVelY() {
        return iVelY;
    }

    public void setVelY(int iVelY) {
        this.iVelY = iVelY;
    }

    /**
     * setImagen
     * 
     * Metodo modificador usado para cambiar el icono de imagen del objeto
     * tomandolo de un objeto imagen
     * 
     * @param imaImagen es la <code>imagen</code> del objeto.
     * 
     */
    public void setImagen(Image imaImagen) {
        this.imaImagen = imaImagen;
        ImageIcon imiImagen = new ImageIcon(imaImagen);
        this.iAncho = imiImagen.getIconWidth();
        this.iAlto = imiImagen.getIconHeight();
    }

    /**
     * getImagen
     * 
     * Metodo de acceso que regresa la imagen que representa el icono del objeto
     * 
     * @return la imagen a partide del <code>icono</code> del objeto.
     * 
     */
    public Image getImagen() {
        if(aniPrincipal != null){
            return aniPrincipal.getImagen();
        }
        return imaImagen;
    }

    /**
     * getAncho
     * 
     * Metodo de acceso que regresa el ancho del icono 
     * 
     * @return un <code>entero</code> que es el ancho de la imagen.
     * 
     */
    public int getAncho() {
        return iAncho;
    }

    /**
     * getAlto
     * 
     * Metodo que  da el alto del icono 
     * 
     * @return un <code>entero</code> que es el alto de la imagen.
     * 
     */
    public int getAlto() {
        return iAlto;
    }
    
    /**
     * paint
     * 
     * Metodo para pintar el animal
     * 
     * @param graGrafico    objeto de la clase <code>Graphics</code> para graficar
     * @param imoObserver  objeto de la clase <code>ImageObserver</code> es el 
     *    Applet donde se pintara
     * 
     */
    public void paint(Graphics graGrafico, ImageObserver imoObserver) {
        graGrafico.drawImage(getImagen(), getX(), getY(), getAncho(), getAlto(), imoObserver);
    }

    /**
     * equals
     * 
     * Metodo para checar igualdad con otro objeto
     * 
     * @param objObjeto    objeto de la clase <code>Object</code> para comparar
     * @return un valor <code>boleano</code> que sera verdadero si el objeto
     *   que invoca es igual al objeto recibido como parámetro
     * 
     */
    public boolean equals(Object objObjeto) {
        // si el objeto parametro es una instancia de la clase Base
        if (objObjeto instanceof Base) {
            // se regresa la comparación entre este objeto que invoca y el
            // objeto recibido como parametro
            Base basParam = (Base) objObjeto;
            return this.getX() ==  basParam.getX() && 
                    this.getY() == basParam.getY() &&
                    this.getAncho() == basParam.getAncho() &&
                    this.getAlto() == basParam.getAlto() &&
                    this.getImagen() == basParam.getImagen();
        }
        else {
            // se regresa un falso porque el objeto recibido no es tipo Base
            return false;
        }
    }

    /**
     * getAnimacion
     *
     * Metodo de acceso que regresa la animacion del objeto
     *
     * @return la animacion del objeto.
     *
     */
    public Animacion getAnimacion() {
        return aniPrincipal;
    }

    /**
     * toString
     * 
     * Metodo para obtener la interfaz del objeto
     * 
      * @return un valor <code>String</code> que representa al objeto
     * 
     */
    public String toString() {
        return " x: " + this.getX() + " y: "+ this.getY() +
                " ancho: " + this.getAncho() + " alto: " + this.getAlto();
    }
}