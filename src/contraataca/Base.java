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

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;


/**
 * Base
 *
 * Clase modelo para un objeto gráfico que contiene información sobre su
 * posición, velocidad e información gráfica. El objeto puede tener una
 * imágen estática de la clase <code>Image</code> o una animación de la clase
 * <code>Animacion</code>.
 *
 * @author Irvel
 * @author Jorge
 * @version 0.4
 */
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
     * Metodo constructor usado para crear un objeto Base con imagen estática
     * y velocidad predefinida.
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
     * Base
     *
     * Metodo constructor usado para crear un objeto Base con una animación
     * de la clase <code>Animación</code> y velocidad predefinida.
     *
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param iVX es la <code>velocidad en x</code> del objeto.
     * @param iVY es la <code>velocidad en y</code> del objeto.
     * @param anim es la <code>Animacion</code> del objeto.
     *
     */
    public Base(int iX, int iY, int iVX, int iVY,  Animacion anim) {
        this.iX = iX;
        this.iY = iY;
        this.iVelX = iVX;
        this.iVelY = iVY;

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
     * Metodo modificador usado para cambiar la posicion en X del objeto
     * 
     * @param iX es la <code>posicion en X</code> del objeto.
     * 
     */
    public void setX(int iX) {
        this.iX = iX;
    }


    /**
     * getX
     * 
     * Metodo de acceso que regresa la posicion en X del objeto
     * 
     * @return iX es la <code>posicion en X</code> del objeto.
     * 
     */
    public int getX() {
            return iX;
    }


    /**
     * setY
     * 
     * Metodo modificador usado para cambiar la posicion en Y del objeto
     * 
     * @param iY es la <code>posicion en Y</code> del objeto.
     * 
     */
    public void setY(int iY) {
            this.iY = iY;
    }


    /**
     * getY
     *
     * Metodo de acceso que regresa la posicion en Y del objeto
     *
     * @return posY es la <code>posicion en Y</code> del objeto.
     *
     */
    public int getY() {
        return iY;
    }


    /**
     * getVelX
     *
     * Metodo de acceso que regresa la velocidad en X del objeto
     *
     * @return iVelX es la <code>velocidad en X</code> del objeto.
     *
     */
    public int getVelX() {
        return iVelX;
    }


    /**
     * setVelX
     *
     * Metodo modificador usado para cambiar la velocidad en X del objeto
     *
     * @param iVelX es la <code>velocidad en X</code> a cambiar del objeto.
     *
     */
    public void setVelX(int iVelX) {
        this.iVelX = iVelX;
    }


    /**
     * getVelY
     *
     * Metodo de acceso que regresa la velocidad en Y del objeto
     *
     * @return iVelY es la <code>velocidad en Y</code> del objeto.
     *
     */
    public int getVelY() {
        return iVelY;
    }


    /**
     * setVelY
     *
     * Metodo modificador usado para cambiar la velocidad en Y del objeto
     *
     * @param iVelY es la <code>velocidad en Y</code> a cambiar del objeto.
     *
     */
    public void setVelY(int iVelY) {
        this.iVelY = iVelY;
    }


    /**
     * setImagen
     * 
     * Metodo modificador usado para cambiar la imagen del objeto tomandolo
     * de un objeto parámetro imagen
     * 
     * @param imaImagen es la <code>imagen</code> del objeto.
     * 
     */
    public void setImagen(Image imaImagen) {
        this.imaImagen = imaImagen;
        // Extrae el ancho y alto de la nueva imagen para las variables miembro
        ImageIcon imiImagen = new ImageIcon(imaImagen);
        this.iWidth = imiImagen.getIconWidth();
        this.iHeight = imiImagen.getIconHeight();
    }


    /**
     * getImagen
     * 
     * Metodo de acceso que regresa la imagen del objeto
     *
     * @return la imagen actual del objeto.
     * 
     */
    public Image getImagen() {
        /* Si el objeto tiene una animación definida, utilizar la animación
        para obtener la imagen actual del objeto*/
        if(aniPrincipal != null){
            return aniPrincipal.getImagen();
        }
        return imaImagen;
    }


    /**
     * getWidth
     * 
     * Metodo de acceso que regresa el ancho del objeto
     * 
     * @return iWidth, un <code>entero</code> que representa el ancho del
     * objeto.
     * 
     */
    public int getWidth() {
        return iWidth;
    }


    /**
     * getHeight
     *
     * Metodo de acceso que regresa el ancho del objeto
     *
     * @return iHeight, un <code>entero</code> que representa el ancho del
     * objeto.
     *
     */
    public int getHeight() {
        return iHeight;
    }

    
    /**
     * paint
     * 
     * Metodo que dibuja la instancia del objeto actual en un gráfico de
     * <code>Graphics</code> recibido.
     *
     * @param graGrafico objeto de la clase <code>Graphics</code> para graficar
     * @param imoObserver objeto de la clase <code>ImageObserver</code>. Es el
     *                    JFrame donde se pintara la instancia actual del
     *                    objeto Base.
     * 
     */
    public void paint(Graphics graGrafico, ImageObserver imoObserver) {
        graGrafico.drawImage(getImagen(), getX(), getY(), getWidth(), getHeight(), imoObserver);
    }


    /**
     * getAnimacion
     *
     * Metodo de acceso que regresa la animacion del objeto
     *
     * @return la instancia de la clase <code>Animacion</code> que pertenece
     * al objeto.
     *
     */
    public Animacion getAnimacion() {
        return aniPrincipal;
    }
}