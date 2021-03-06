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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;


/**
 * JuegoPrincipal
 *
 * Juego en <code>JFrame</code> que presenta un jugador principal que puede
 * ser controlado con las teclas Q, A, P, L. El objetivo del juego es esquivar
 * los asteroides que entran por el lado derecho y atrapar las galletas que
 * entran por el lado izquierdo; ambos de forma aleatoria.
 *
 * Recursos utilizados:
 * Low Poly Rocks por "para" (http://opengameart.org/content/low-poly-rocks)
 * Platformer Art Complete Pack por "Kenney" (http://opengameart.org/content/platformer-art-complete-pack-often-updated)
 * 63 Digital sound effects por "Kenney" (http://opengameart.org/content/63-digital-sound-effects-lasers-phasers-space-etc)
 *
 * @author Irvel
 * @author Jorge
 * @version 0.4
 * @date 17/02/2016
 */

public class JuegoPrincipal extends JFrame implements Runnable, KeyListener {

    private Jugador jugPrincipal;               // El objeto del jugador
    private ArrayList<Bala> arrBalas;        // Lista de balas
    private ArrayList<Malo> arrMalos;        // Lista de personajes malos
    private ArrayList<MaloPersigue> arrMalosSiguen;  // Lista de malos que persiguen
    private ArrayList<ImageIcon> arrVidas;   // Lista de vidas
    private Image imaImagenFondo;            // Para dibujar la imagen de fondo
    private Image imaGameOver;               // Para dibujar la imagen final
    private int iVidas;                      // El # de vidas del jugador
    private int iScore;                      // El score del jugador

    // El número de veces que el jugador ha sido golpeado por un malo
    private int iContGolpe;
    private int iCantidadMalos;               // Cantidad al azar de malos
    private int iTeclaActual;                 // La ultima tecla presionada
    private long lTiempoActual;               // El tiempo desde la ultima
    private Image imaImagenApplet;            // Imagen a proyectar en JFrame
    private Graphics graGraficaApplet;        // Objeto grafico de la Imagen
    private Image imaPaused;                  // Imagen a proyectar cuando este pausado
    private SoundClip sMalo;                  // Sonido colision con un malo
    private static final int IWIDTH = 800;    // El ancho del JFrame
    private static final int IHEIGHT = 600;   // El alto del JFrame
    private String sNombreArchivo;            // Nombre del archivo
    private String sNombreArchivoInit;        // Nombre del archivo Inicial
    private boolean bMovBala;                 // Checar si hay que mover la bala
    private boolean bPressedBala;             // Checar si haber más de una bala
    private boolean bPintarBala;
    private boolean bReleased;                // Checar si la tecla esta siendo oprimida
    private boolean bPaused;                  // Checar si el juego esta pausado
    private boolean bGameOver;                // Checar si el juego termino
    private boolean bFirstTime;               // Checar si es la primera vez
    private char cDireccion;                  // Direccion a mover la bala
    private int iVelocidad;                   // Velocidad de los malos 

    /**
     * init()
     *
     * Método que inicializa las variables miembro de la clase JuegoPrincipal.
     * También crea los objetos a ser utilizados en el <code>JFrame</code>.
     *
     */
    public void init(){
        sNombreArchivo = "Puntaje.txt";
        sNombreArchivoInit = "First.txt";
        iVidas = 5;
        iScore = 0;
        iTeclaActual = 0;
        bMovBala = false;
        bPintarBala = false;
        bPressedBala = false;
        bReleased = true;
        bFirstTime = true;
        cDireccion = 'X';
        iVelocidad = 3;
        
        arrBalas = new ArrayList<>();

        // Genera de 8 a 10 malos de forma aleatoria
        iCantidadMalos = (int)(Math.random() * 3) + 8;
        cargarMalos(iCantidadMalos);


        // Crea la imagen de fondo.
        URL urlImagenFondo = this.getClass().getResource("sTatooine.jpg");
        imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);

        // Creo la imagen de pausa
        URL urlImagenPausa = this.getClass().getResource("paused.png");
        imaPaused = Toolkit.getDefaultToolkit().getImage(urlImagenPausa);

        // Crea la imagen para el fin del juego
        URL urlGameOver = this.getClass().getResource("gameOver.png");
        imaGameOver = Toolkit.getDefaultToolkit().getImage(urlGameOver);

        // Crea el jugador principal
        cargarJugador();
        
        // Crea la imagen de vidas
        cargaVidas();

        // Carga los sonidos de colisiones
        cargarSonidos();
        try{
            grabaArchivoInicial();
        }
        catch (IOException e){
        }
        addKeyListener(this);
    }
    
    /**
     * cargaVidas()
     *
     * Método carga la imagen de las vidas, 
     *
     */
    private void cargaVidas(){
            arrVidas = new ArrayList<ImageIcon>();
            
            URL urlVida = this.getClass().getResource("heart.png");
            
            for (int iC = 0; iC < 5;++iC){
                arrVidas.add(new ImageIcon(Toolkit.getDefaultToolkit().getImage(urlVida)));
            }
    }


    /**
     * cargarJugador()
     *
     * Método carga la imagen del jugador de disco, inicializa su posición en
     * el centro del JFrame y crea el objeto <code>Base</code> del jugado.
     *
     */
    private void cargarJugador() {
        // Define la imagen del jugador principal
        URL urlJugador = this.getClass().getResource("sJugador.gif");

        // Calcula el centro tomando en cuenta las dimensiones del jugador
        int iPosX = (IWIDTH / 2) - (90 / 2);
        int iPosY = (IHEIGHT) - (121);

        // Crea el objeto del jugador principal en la parte inferior de la pantalla
        jugPrincipal = new Jugador(iPosX,
                                   iPosY,
                                   10,
                                   10,
                                   Toolkit.getDefaultToolkit().getImage(urlJugador));
    }


    /**
     * JuegoPrincipal()
     *
     * Método constructor del JFrame JuegoPrincipal que manda inicializar las
     * variables miembro y crea el <code>Thread</code> principal del Juego.
     *
     */
    public JuegoPrincipal() {
        init();
        Thread th = new Thread(this);
        th.start();
    }


    /**
     * cargarMalos()
     *
     * Método que carga las imágenes individuales que componen la animación
     * del asteroide
     *
     */
    private void cargarMalos(int iCant) {
        String sNomArchivo;
        Image imaCuadro;
        ArrayList<CuadroDeAnimacion> arrCuadros = new ArrayList<>();
        for (int i = 0; i < 31; i++){
            // Modificar el nombre de archivo de acuerdo al contador
            sNomArchivo = "sMalo/crop_as-" + i + ".png";
            URL urlImagen = this.getClass().getResource(sNomArchivo);

            // Obtener la imagen a partir del url y encogerla
            imaCuadro = Toolkit
                    .getDefaultToolkit()
                    .getImage(urlImagen)
                    .getScaledInstance(50,50, Image.SCALE_SMOOTH);
            arrCuadros.add(new CuadroDeAnimacion(imaCuadro, 50));
        }
        crearMalos(iCant, arrCuadros);
    }


    /**
     * crearMalos()
     *
     * Método que crea una cantidad pre-determinada de personajes malos con
     * coordenadas aleatorias fuera del applet de la parte derecha. Utiliza
     * las imagenes ya cargadas de los en arrImaMalo
     *
     * @param iCantidad es el <code>número</code> de objetos malos a crear.
     * @param arrCuadros es un <code>ArrayList</code> de CuadroDeAnimación
     *                   para crear la animación de cada personaje.
     *
     */
    private void crearMalos(int iCantidad, ArrayList<CuadroDeAnimacion> arrCuadros) {
        arrMalos = new ArrayList<>();
        arrMalosSiguen = new ArrayList<>();
        int iMalosPersiguen = iCantidad / 10;
        int iMalosNormales = iCantidad - iMalosPersiguen;
        iCantidadMalos -= iMalosPersiguen;
        for (int i = 0; i < iMalosNormales; i++) {
            //Genera una posición aleatoria fuera del applet por la derecha
            int iPosX = getXRandom();
            int iPosY = getYRandom();

            // Agregar un objeto nuevo malo con velocidad inicial de
            // -3 en el eje Y y velocidad 0 en el eje X
            arrMalos.add(new Malo(iPosX,
                                  iPosY,
                                  0,
                                  3,
                                  new Animacion(arrCuadros)));
        }
        for (int i = 0; i < iMalosPersiguen; i++) {
            //Genera una posición aleatoria fuera del applet por la derecha
            int iPosX = getXRandom();
            int iPosY = getYRandom();

            // Agregar un objeto nuevo malo con velocidad inicial de
            // 3 en el eje Y y velocidad 0 en el eje X
            arrMalosSiguen.add(new MaloPersigue(iPosX,
                                          iPosY,
                                          0,
                                          3,
                                          new Animacion(arrCuadros)));
        }
    }



    /**
     * cargarSonidos()
     *
     * Método que carga el sonido de las colisiones con el malo
     *
     */
    private void cargarSonidos() {
        sMalo = new SoundClip("aVidaPerdida.wav");
    }


    /**
     * getXRandom()
     *
     * Método que regresa una coordenada aleatoria para el eje X que se
     * encuentra dentro del JFrame.
     *
     */
    private int getXRandom(){

        return (int)(Math.random() * (IWIDTH));
    }


    /**
     * getYRandom()
     *
     * Método que regresa una coordenada aleatoria para el eje Y que se
     * encuentra dentro del JFrame.
     *
     */
    private int getYRandom(){
        // Generar coordenada tomando en cuenta la barra superior del JFrame
        int iYRandom = (int)(Math.random() * (500) - 500 );

        return iYRandom;
    }


    /**
     * run()
     *
     * Metodo sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, que contendrá las instrucciones
     * del juego.
     *
     */
    @Override
    public void run () {
        /* mientras dure el juego, se actualizan posiciones de jugadores
           se checa si hubo colisiones para desaparecer jugadores o corregir
           movimientos y se vuelve a pintar todo
        */
        while (true) {
            if (!bPaused){
                if(iVidas > 0){
                    actualiza();
                    checarColision();
                    repaint();
                }
            }
            try	{
                // El hilo del juego se duerme.
                Thread.sleep (20);
            }
            catch (InterruptedException iexError) {
                System.out.println("Hubo un error en el juego " +
                                           iexError.toString());
            }
        }
    }

    /**
     * actualiza()
     *
     * Metodo que actualiza la posicion de los objetos
     *
     */
    public void actualiza(){
        mueveJugador();     // Mueve el jugador en la dirección presionada
        dispara();          // Dispara una bala de acuerdo a la tecla presionada
        guardaOCarga();     // Carga un juego previo o almacena el juego actual
        mueveBala();        // Mueve a la bala si existe
        iTeclaActual = 0;   // Reestablece la útima tecla presionada a ninguna
        mueveMalos();       // Mueve a los personajes malos
        animaMalos();       // Actualiza la animacion de cada malo
        checaVidas();       // Resta vidas de acuerdo al valor de iContGolpe
    }

    private void dispara() {
        // Control para que sólo dispare una bala cuando se deja
        // presionada una tecla
        if (bReleased){
            if (iTeclaActual == KeyEvent.VK_SPACE) {
                creaBala('N');
            }
            if (iTeclaActual == KeyEvent.VK_S){
                creaBala('S');
            }
            if (iTeclaActual == KeyEvent.VK_A){
                creaBala('A');
            }
        }
    }


    /**
     * guardaOCarga()
     *
     * Metodo que manda a llamar la función para grabar al archivo los
     * ajustes o leer del archivo los ajustes dependiendo de la última tecla
     * presionada.
     *
     */
    private void guardaOCarga() {
        switch(iTeclaActual) {
            case KeyEvent.VK_G:
                try{
                    grabaArchivo();
                }
                catch(IOException e){

                }
                break;
            case KeyEvent.VK_C:
                try{
                    leeArchivo();
                }
                catch(IOException e){

                }
                break;
        }
    }


    /**
     * mueveJugador()
     *
     * Metodo actualiza la posición del Jugador principal de acuerdo a la
     * última tecla presionada.
     *
     */
    private void mueveJugador() {
        switch(iTeclaActual) {
            case KeyEvent.VK_LEFT:
                jugPrincipal.avanzaIzquierda();
                break;
            case KeyEvent.VK_RIGHT:
                jugPrincipal.avanzaDerecha();
                break;
        }
    }


    /**
     * mueveBala()
     *
     * Metodo actualiza la posición de las balas generadas por el jugador
     * principal.
     *
     */
    private void mueveBala(){
        arrBalas.forEach(Bala::avanza);
    }


    /**
     * checaVidas()
     *
     * Metodo que decrementa las vidas si se ha llegado a una cantidad de
     * golpes predeterminada. Al decrementarse una vida, se reproduce un
     * sonido y se reestablece el contador de golpes.
     *
     */
    private void checaVidas() {
        if(iContGolpe >= 5){
            iVidas--;
            sMalo.play();
            iContGolpe = 0;
            iVelocidad += 2;
            int iPosition = arrVidas.size() - 1;
            arrVidas.remove(iPosition);
        }
    }


    /**
     * mueveMalos()
     *
     * Metodo que actualiza la posicion de los personajes malos
     *
     */
    private void mueveMalos() {
        // Mueve a los personajes malos
        for(Malo perMalo : arrMalos){
            perMalo.avanza(iVelocidad);
        }
        for(MaloPersigue perMalo : arrMalosSiguen){
            perMalo.avanza(jugPrincipal.getX(), jugPrincipal.getY(),
                           iVelocidad);
        }
    }


    /**
     * animaMalos()
     *
     * Metodo que manda el tiempo transcurrido desde el último ciclo al
     * método actualiza de cada personaje Malo para actualizar su cuadro actual.
     *
     */
    private void animaMalos() {
        // Calcula el tiempo transcurrido desde la iteracion previa
        long tiempoTranscurrido =
                System.currentTimeMillis() - lTiempoActual;
        lTiempoActual += tiempoTranscurrido;

        for(Malo perMalo : arrMalos){
            // Actualiza los cuadros en la animacion
            perMalo.getAnimacion().actualiza(tiempoTranscurrido);
        }
        for(MaloPersigue perMalo : arrMalosSiguen){
            // Actualiza los cuadros en la animacion
            perMalo.getAnimacion().actualiza(tiempoTranscurrido);
        }
    }


    /**
     * checarColision()
     *
     * Metodo usado para checar la colision entre objetos y la colision
     * de los objetos con los límites de la pantalla
     *
     */
    public void checarColision(){
        Rectangle recJugador = new Rectangle(jugPrincipal.getX(),
                                             jugPrincipal.getY(),
                                             jugPrincipal.getWidth(),
                                             jugPrincipal.getHeight());
        // Maneja las colisiones del jugador con los personajes
        checarColisionMalos(recJugador);
        if (bMovBala){
            checarColisionBala();
        }

        // Maneja las colisiones del jugador con los bordes del Applet
        if (jugPrincipal.getX() >= getWidth() - jugPrincipal.getWidth()) {
            jugPrincipal.setX(getWidth() - jugPrincipal.getWidth());
        }
        if (jugPrincipal.getX() < 0) {
            jugPrincipal.setX(0);
        }

    }

    public void checarColisionBala(){
        Rectangle recMalo;
        // Revisar la bala está colisionando con un malo
        for(Malo perMalo: arrMalos){
            recMalo = new Rectangle(perMalo.getX(),
                                    perMalo.getY(),
                                    perMalo.getWidth(),
                                    perMalo.getHeight());
            for (int iC = 0; iC < arrBalas.size();++iC){
                Bala balBala = arrBalas.get(iC);
                Rectangle recBala;
                recBala = new Rectangle(balBala.getX(),
                                       balBala.getY(),
                                       balBala.getWidth(),
                                       balBala.getHeight());
                if(recBala.intersects(recMalo)){
                    iScore += 10;
                    arrBalas.remove(balBala);
                    bPressedBala = true;
                    reposicionaMalo(perMalo);
                }
            }
        }
        for(Malo perMalo: arrMalosSiguen){
            recMalo = new Rectangle(perMalo.getX(),
                                    perMalo.getY(),
                                    perMalo.getWidth(),
                                    perMalo.getHeight());
            for (int iC = 0; iC < arrBalas.size();++iC){
                Bala balBala = arrBalas.get(iC);
                Rectangle recBala;
                recBala = new Rectangle(balBala.getX(),
                                        balBala.getY(),
                                        balBala.getWidth(),
                                        balBala.getHeight());
                if(recBala.intersects(recMalo)){
                    iScore += 10;
                    arrBalas.remove(balBala);
                    bPressedBala = true;
                    reposicionaMalo(perMalo);
                }
            }
        }
        // Revisar si las balas se salieron del applet
        for (int iC = 0; iC < arrBalas.size();++iC){
            Bala basBala = (Bala) arrBalas.get(iC);
            if (basBala.getY() <= 0){
                arrBalas.remove(basBala);
            //System.out.println(arrBalas.size());
            
           }
        }
    }
       


    /**
     * checarColisionMalos()
     *
     * Metodo usado para checar la colision entre el jugador y los personajes
     * malos.
     *
     */
    private void checarColisionMalos(Rectangle recJugador) {
        Rectangle recMalo;
        // Revisar si el jugador está colisionando con un malo
        for(Malo perMalo: arrMalos){
            recMalo = new Rectangle(perMalo.getX(),
                                    perMalo.getY(),
                                    perMalo.getWidth(),
                                    perMalo.getHeight());
            if(recJugador.intersects(recMalo)){
                iContGolpe++;
                reposicionaMalo(perMalo);
            }

            // Revisar si el personaje llegó al la orilla del applet
            if(perMalo.getY() >= getHeight()){
                reposicionaMalo(perMalo);
            }
        }
        for(MaloPersigue perMalo: arrMalosSiguen){
            recMalo = new Rectangle(perMalo.getX(),
                                    perMalo.getY(),
                                    perMalo.getWidth(),
                                    perMalo.getHeight());
            if(recJugador.intersects(recMalo)){
                iContGolpe++;
                reposicionaMalo(perMalo);
            }

            // Revisar si el personaje llegó al la orilla del applet
            if(perMalo.getY() >= getHeight()){
                reposicionaMalo(perMalo);
            }
        }
    }


    /**
     * reposicionaMalo()
     *
     * Metodo usado para reinicializar la posición de un malo fuera del
     * applet por la derecha.
     *
     */
    private void reposicionaMalo(Malo perMalo) {
        int iPosX = getXRandom();
        int iPosY = getYRandom();
        perMalo.setX(iPosX);
        perMalo.setY(iPosY);
    }


    /**
     * paint(Graphics graGrafico)
     *
     * Metodo sobrescrito de la clase <code>JFrame</code>,heredado de la
     * clase Container.
     * Actualiza el contenedor y define cuando usar ahora el paint.
     *
     * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
     *
     */
    @Override
    public void paint (Graphics graGrafico){
        // Inicializa el DoubleBuffer
        if (imaImagenApplet == null){
            imaImagenApplet = createImage (this.getSize().width,
                                           this.getSize().height);
            graGraficaApplet = imaImagenApplet.getGraphics ();
        }
        // Dibuja los gráficos del juego sobre el JFrame
        paintGame(graGraficaApplet);

        // Dibuja la imagen actualizada
        graGrafico.drawImage (imaImagenApplet, 0, 0, this);
    }


    /**
     * paintGame(Graphics graDibujo)
     *
     * Método que dibuja todos los elementos del Juego en el JFrame
     * incluyendo el fondo, el jugador principal, los personajes malos, el
     * tablero de score y las vidas.
     *
     * @param graDibujo es el objeto de <code>Graphics</code> usado para dibujar.
     *
     */
    public void paintGame(Graphics graDibujo) {
        // Verifica que la imagen ya haya sido cargada
        if (jugPrincipal != null && imaImagenFondo != null) {
            if(iVidas > 0) {
                // Dibuja la imagen de fondo
                graDibujo.drawImage(imaImagenFondo,
                                    0,
                                    0,
                                    getWidth(),
                                    getHeight(),
                                    this);

                // Dibuja los objetos principales del Applet
                jugPrincipal.paint(graDibujo, this);
                for (Malo perMalo : arrMalos) {
                    perMalo.paint(graDibujo, this);
                }
                for (MaloPersigue perMalo : arrMalosSiguen) {
                    perMalo.paint(graDibujo, this);
                }

                if (bPaused){
                    //Dibuja la imagen Pausada cuando se pausa el juego
                    graDibujo.drawImage(imaPaused,0,0,getWidth(),getHeight(), this);
                }

                graDibujo.drawString("Score: " + iScore, 50, 50);
            }
            else{
                bGameOver = true;
                // Dibuja la imagen de GameOver cuando se acaben las vidas
                arrBalas.clear();
                graDibujo.drawImage(imaGameOver,
                                    0,
                                    0,
                                    getWidth(),
                                    getHeight(),
                                    this);
            }
        }
        else {
            // Despliega un mensaje mientras se carga el dibujo
            graDibujo.drawString("No se cargo la imagen..", 20, 20);
        }
        if (arrBalas.size() > 0 && bPintarBala){
            dibujaBalas(graDibujo);
        }
        if (arrVidas.size() > 0){
            dibujaVidas(graDibujo);
        }
    }


    /**
     * 
     * dibujaBalas
     * 
     * Metodo que dibuja las balas en el Applet
     * 
     * @param graDibujo 
     */
    private void dibujaBalas(Graphics graDibujo){
        
        if (arrBalas.size() > 0){
            for (int iC = 0; iC < arrBalas.size();++iC){
                Bala basBala = (Bala) arrBalas.get(iC);
                basBala.paint(graDibujo,this);
            }
        }
    }
    /**
     * 
     * dibujaVidas(Graphics graDibujo)
     * 
     * Metodo que dibuja las vidas en el Applet
     * 
     * @param graDibujo 
     */
    private void dibujaVidas(Graphics graDibujo){
        int iX = 50;
        for (ImageIcon imaVida: arrVidas){
            imaVida.paintIcon(this, graDibujo, iX , 50);
            iX += 48;
        }
    }

    /**
     * leeArchivo()
     *
     * Método usado para leer un estado previo del juego y cargarlo en el
     * juego actual.
     *
     */
    public void leeArchivo() throws IOException{
        BufferedReader fileIn;
        try{
            fileIn = new BufferedReader(new FileReader(sNombreArchivo));
        }
        catch (FileNotFoundException e){
            File primerexamen = new File(sNombreArchivo);
            PrintWriter fileOut = new PrintWriter(primerexamen);
            fileOut.println("100,demo");
            fileOut.close();
            fileIn = new BufferedReader(new FileReader(sNombreArchivo));
        }
        leePersonajesArchivo(fileIn);
        fileIn.close();
    }


    /**
     * leePersonajesArchivo()
     *
     * Método usado para leer los personajes de un estado previo del juego y
     * cargarlo al juego actual. El estado previo lo lee de un archivo de
     * texto recibido en un BufferedReader.
     *
     * @param fileIn es el <code>archivo</code> de texto a leer con el estado
     *               previo del juego.
     *
     */
    private void leePersonajesArchivo(BufferedReader fileIn) throws
                                                             IOException {
        // Lee la cantidad de vidas y score pasado
        iVidas = (Integer.parseInt(fileIn.readLine()));
        iScore = (Integer.parseInt(fileIn.readLine()));

        // Lee la cantidad de personajes malos y posición de cada uno
        iCantidadMalos = (Integer.parseInt(fileIn.readLine()));
        leeMalosArchivo(fileIn);

        // Lee la posición previa del jugador
        jugPrincipal.setX(Integer.parseInt(fileIn.readLine()));
        jugPrincipal.setY(Integer.parseInt(fileIn.readLine()));
        
        //Lee la velocidad de los malos
        iVelocidad = (Integer.parseInt(fileIn.readLine()));
    }




    /**
     * leeMalosArchivo()
     *
     * Método usado para leer la posicion de cada uno de los personajes
     * malos de un estado previo y cargarlas al juego actual.
     *
     * @param fileIn es el <code>archivo</code> de texto a leer con el estado
     *               previo del juego.
     *
     */
    private void leeMalosArchivo(BufferedReader fileIn) throws IOException {
        // Lee las coordenadas X y Y de cada malo de acuerdo a iCantidadMalos
        for (int iC = 0; iC < iCantidadMalos; ++iC){
            arrMalos.get(iC).setX(Integer.parseInt(fileIn.readLine()));
            arrMalos.get(iC).setY(Integer.parseInt(fileIn.readLine()));
        }
    }


    /**
     * grabaArchivo()
     *
     * Método usado para guardar la posicion de cada uno de los personajes
     * malos del juego actual. También guarda el número de vidas, score y las
     * coordenadas del jugador en el JFrame.
     *
     */
    public void grabaArchivo() throws IOException{
        PrintWriter fileOut = new PrintWriter(new FileWriter(sNombreArchivo));
        // Guarda el número de vidas y score actuales
        fileOut.println(iVidas);
        fileOut.println(iScore);

        // Guarda la cantidad de malos actuales y la posición de cada uno
        fileOut.println(iCantidadMalos);
        for (int iC = 0; iC < iCantidadMalos;++iC){
            fileOut.println(arrMalos.get(iC).getX());
            fileOut.println(arrMalos.get(iC).getY());
        }

        // Guarda las coordenadas actuales del jugador
        fileOut.println(jugPrincipal.getX());
        fileOut.println(jugPrincipal.getY());

        fileOut.close();
    }

    public void grabaArchivoInicial() throws IOException {
        PrintWriter fileOut = new PrintWriter(new FileWriter(sNombreArchivoInit));
        // Guarda el número de vidas y score actuales
        fileOut.println(iVidas);
        fileOut.println(iScore);

        // Guarda la cantidad de malos actuales y la posición de cada uno
        fileOut.println(iCantidadMalos);
        for (int iC = 0; iC < iCantidadMalos;++iC){
            fileOut.println(arrMalos.get(iC).getX());
            fileOut.println(arrMalos.get(iC).getY());
        }

        // Guarda las coordenadas actuales del jugador
        fileOut.println(jugPrincipal.getX());
        fileOut.println(jugPrincipal.getY());
        
        // Guarda la velocidad inicial de los personajes
        fileOut.println(iVelocidad);

        fileOut.close();
    }

    public void leeArchivoInicial() throws IOException{
        BufferedReader fileIn;
        try{
            fileIn = new BufferedReader(new FileReader(sNombreArchivoInit));
        }
        catch (FileNotFoundException e){
            File primerexamen = new File(sNombreArchivoInit);
            PrintWriter fileOut = new PrintWriter(primerexamen);
            fileOut.println("100,demo");
            fileOut.close();
            fileIn = new BufferedReader(new FileReader(sNombreArchivoInit));
        }
        leePersonajesArchivo(fileIn);
        fileIn.close();
        cargaVidas();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * keyPressed()
     *
     * Metodo sobrescrito de la clase <code>KeyListener</code>. Almacena la
     * última tecla presionada en iTeclaActual
     *
     */
    @Override
    public void keyPressed(KeyEvent key) {
        iTeclaActual = key.getKeyCode();

        if (iTeclaActual == KeyEvent.VK_P){
            bPaused = !bPaused;
        }
        if (iTeclaActual == KeyEvent.VK_R){
            if (bGameOver){
                try{
                    leeArchivoInicial();
                }
                catch(IOException e){

                }
            }
        }
    }


    public void creaBala(char cTipo){
        bReleased = false;
        bMovBala = true;
        bPintarBala = true;
        URL urlBala = this.getClass().getResource("bullet.png");
        int iPosX = jugPrincipal.getX() + 90 / 2;
        int iPosY = jugPrincipal.getY();
        arrBalas.add(new Bala(iPosX,iPosY,
                           Toolkit.getDefaultToolkit().getImage(urlBala),cDireccion));

        Bala basBala = new Bala(iPosX,
                                iPosY,
                                Toolkit.getDefaultToolkit().getImage(urlBala),
                                cTipo);
        arrBalas.add(basBala);
    }


    @Override
    public void keyReleased(KeyEvent key) {
        iTeclaActual = key.getKeyCode();
        if (iTeclaActual == KeyEvent.VK_SPACE){
            bReleased = true;
        }
        if (iTeclaActual == KeyEvent.VK_A){
            bReleased = true;
        }
        if (iTeclaActual == KeyEvent.VK_S){
            bReleased = true;
        }
    }


    public static void main(String [] args){
        JuegoPrincipal hola = new JuegoPrincipal();
        hola.setSize(IWIDTH,IHEIGHT);
        hola.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hola.setVisible(true);
    }

}
