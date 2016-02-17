/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primerexamen;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author Irvel
 */
public class PrimerExamen extends Applet implements Runnable, KeyListener {

    private Base basJugador;
    private ArrayList<Personaje> arrMalos;
    private ArrayList<Personaje> arrBuenos;
    private Image imaImagenFondo;         // para dibujar la imagen de fondo
    private Image imaGameOver;         // para dibujar la imagen final
    private Image arrImaMalo[];         // Arr para las frames del personaje malo
    private final int iMALOFRAMES = 31;    // El # de frames del malo
    private int iFrameActual;             // La frame actual en la animacion ast
    private int iVidas;                   // El # de vidas del jugador
    private int iScore;   // El score del jugador
    private int iContGolpe;
    private int iTeclaActual;            // Almacena la ultima tecla presionada
    private boolean bFinJuego;
    private Image imaImagenApplet;    // Imagen a proyectar en Applet
    private Graphics graGraficaApplet;   // Objeto grafico de la Imagen
    private AudioClip sBueno;        // Sonido colision con un bueno
    private AudioClip sMalo;        // Sonido colision con un malo


    /**
     * init
     *
     * Metodo sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos
     * a usarse en el <code>Applet</code> y se definen funcionalidades.
     *
     */
    public void init() {
        // Establece el applet de tamaño 800,480
        setSize(800,480);
        iVidas = 5;
        iScore = 0;
        iFrameActual = 0;
        iTeclaActual = 0;
        bFinJuego = false;
        // Define la imagen del jugador principal
        URL urlJugador = this.getClass().getResource("Sjugador.gif");

        // Calcula el centro tomando en cuenta las dimensiones del jugador
        int iMitadX = (getWidth() / 2) - (90 / 2);
        int iMitadY = (getHeight() / 2) - (121 / 2);

        // Crea el objeto del jugador principal en el centro del mapa
        basJugador = new Base(iMitadX,
                              iMitadY,
                              10,
                              10,
                              Toolkit.getDefaultToolkit().getImage(urlJugador));

        // Carga la animación del personaje malo
        cargarImaMalo();

        /* Crea un número de personajes malos y buenos; y los almacena en
         * sus respectivos arreglos */
        // Genera de 8 a 10 malos de forma aleatoria
        crearMalos((int)(Math.random() * 3) + 8);

        // Genera de 10 a 13 buenos de forma aleatoria
        crearBuenos((int)(Math.random() * 4) + 10);

        // Crea la imagen de fondo.
        URL urlImagenFondo = this.getClass().getResource("sTatooine.jpg");
        imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);

        // Crea la imagen para el fin del juego
        URL urlGameOver = this.getClass().getResource("gameOver.png");
        imaGameOver = Toolkit.getDefaultToolkit().getImage(urlGameOver);

        // Carga los sonidos de colisiones
        cargarSonidos();
        addKeyListener(this);
    }


    /**
     * cargarImaMalo
     *
     * Método que carga las imágenes individuales que componen la animación
     * del asteroide
     *
     */
    private void cargarImaMalo() {
        arrImaMalo = new Image[iMALOFRAMES];
        String sNomArchivo;
        for (int i = 0; i < iMALOFRAMES; i++){
            // Modificar el nombre de archivo de acuerdo al contador
            sNomArchivo = "sMalo/crop_as-" + i + ".png";
            URL urlImagen = this.getClass().getResource(sNomArchivo);
            // Obtener la imagen a partir del url y encogerla
            arrImaMalo[i] = Toolkit
                    .getDefaultToolkit()
                    .getImage(urlImagen)
                    .getScaledInstance(80,80, Image.SCALE_SMOOTH);
        }
    }


    /**
     * crearMalos
     *
     * Método que crea una cantidad pre-determinada de personajes malos con
     * coordenadas aleatorias fuera del applet de la parte derecha. Utiliza
     * las imagenes ya cargadas de los en arrImaMalo
     *
     * @param iCantidad es el <code>número</code> de objetos malos a crear.
     *
     */
    private void crearMalos(int iCantidad) {
        arrMalos = new ArrayList<>();
        for (int i = 0; i < iCantidad; i++) {
            // Genera una posición aleatoria fuera del applet por la izquierda
            int iPosX = getXRandom() + getWidth();
            int iPosY = getYRandom();

            // Agregar un objeto nuevo de personaje con velocidad de 3 o 5
            arrMalos.add(new Personaje(iPosX, iPosY, arrImaMalo[iFrameActual], 3, 5));
        }
    }


    /**
     * crearBuenos
     *
     * Método que carga la imagen de los personajes buenos y genera una
     * cantidad pre-determinada de personajes buenos con coordenadas
     * aleatorias fuera del applet de la parte izquierda.
     *
     * @param iCantidad es el <code>número</code> de objetos buenos a crear.
     *
     */
    private void crearBuenos(int iCantidad) {
        arrBuenos = new ArrayList<>();

        URL urlImaBueno = this.getClass().getResource("sCookie.png");
        Image imaBueno = Toolkit.getDefaultToolkit().getImage(urlImaBueno);

        for (int i = 0; i < iCantidad; i++) {
            // Genera una posición aleatoria fuera del applet por la derecha
            int iPosX = getXRandom() - getWidth();
            int iPosY = getYRandom();

            // Agregar un objeto nuevo de personaje con velocidad de 1 o 3
            arrBuenos.add(new Personaje(iPosX, iPosY, imaBueno, 1, 3));
        }
    }


    /**
     * cargarSonidos
     *
     * Método que carga los sonidos de las colisiones con el bueno y con el malo
     *
     */
    private void cargarSonidos() {
        URL urlColision1 = this.getClass().getResource("aPuntosGanados.wav");
        URL urlColision2 = this.getClass().getResource("aVidaPerdida.wav");
        sBueno = getAudioClip (urlColision1);
        sMalo = getAudioClip (urlColision2);
    }


    /**
     * getXRandom
     *
     * Método que regresa una coordenada aleatoria para el eje X que se
     * encuentra dentro del applet actual.
     *
     */
    private int getXRandom(){
        return (int)(Math.random() * (getWidth()));
    }


    /**
     * getYRandom
     *
     * Método que regresa una coordenada aleatoria para el eje Y que se
     * encuentra dentro del applet actual.
     *
     */
    private int getYRandom(){
        int iYRandom = (int)(Math.random() * (getHeight()));
        // Evitar que llegue a aparecer debajo del eje Y
        iYRandom = iYRandom > getHeight() - 100 ? getHeight() - 100 : iYRandom;
        return iYRandom;
    }



    /**
     * start
     *
     * Metodo sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo
     * para la animacion este metodo es llamado despues del init o
     * cuando el usuario visita otra pagina y luego regresa a la pagina
     * en donde esta este <code>Applet</code>
     *
     */
    @Override
    public void start () {
        // Declara un hilo
        Thread th = new Thread (this);
        // Empieza el hilo
        th.start ();
    }

    /**
     * run
     *
     * Metodo sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, que contendrá las instrucciones
     * de nuestro juego.
     *
     */
    @Override
    public void run () {
        /* mientras dure el juego, se actualizan posiciones de jugadores
           se checa si hubo colisiones para desaparecer jugadores o corregir
           movimientos y se vuelve a pintar todo
        */
        while (true) {
            if(iVidas > 0){
                actualiza();
                checaColision();
            }
            repaint();
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
     * actualiza
     *
     * Metodo que actualiza la posicion de los objetos
     *
     */
    public void actualiza(){
        // Mueve el jugador principal si es que se presionó una tecla
        switch(iTeclaActual) {
            case KeyEvent.VK_Q:
                basJugador.setX(basJugador.getX() - basJugador.getVelX());
                basJugador.setY(basJugador.getY() - basJugador.getVelY());
                break;
            case KeyEvent.VK_P:
                basJugador.setX(basJugador.getX() + basJugador.getVelX());
                basJugador.setY(basJugador.getY() - basJugador.getVelY());
                break;
            case KeyEvent.VK_L:
                basJugador.setX(basJugador.getX() + basJugador.getVelX());
                basJugador.setY(basJugador.getY() + basJugador.getVelY());
                break;
            case KeyEvent.VK_A:
                basJugador.setX(basJugador.getX() - basJugador.getVelX());
                basJugador.setY(basJugador.getY() + basJugador.getVelY());
                break;
        }
        iTeclaActual = 0;  // Reestablecer la útima tecla presionada a ninguna

        if(iContGolpe >= 5){
            iVidas--;
            sMalo.play();
            iContGolpe = 0;
        }
    }

        // Mueve a los personajes malos
        for(Personaje perMalo : arrMalos){
            perMalo.setX(perMalo.getX() - perMalo.getVel());
        }
        // Mueve a los personajes buenos
        for(Personaje perBueno : arrBuenos){
            perBueno.setX(perBueno.getX() + perBueno.getVel());
        }
        animarMalos();

        if(iContGolpe >= 5){
            iVidas--;
            iContGolpe = 0;
        }

    }

    private void animarMalos() {
        // Cambiar de frame para animar a los personajes malos
        for(Personaje perMalo : arrMalos){
            if(iFrameActual < iMALOFRAMES){
                perMalo.setImagen(arrImaMalo[iFrameActual]);
                iFrameActual++;
            }
            else{
                iFrameActual = 0;
            }
        }
    }

    /**
     * checaColision
     *
     * Metodo usado para checar la colision entre objetos y la colision
     * de los objetos con los límites de la pantalla
     *
     */
    public void checaColision(){
        Rectangle recJugador = new Rectangle(basJugador.getX(),
                                             basJugador.getY(),
                                             basJugador.getAncho(),
                                             basJugador.getAlto());
        checaColisionMalos(recJugador);
        checaColisionBuenos(recJugador);

        // Maneja las colisiones del jugador con los bordes del Applet
        if (basJugador.getY() >= getHeight() - basJugador.getAlto()) {
            basJugador.setY(getHeight() - basJugador.getAlto());
        }
        if (basJugador.getY() < 0) {
            basJugador.setY(0);
        }
        if (basJugador.getX() >= getWidth() - basJugador.getAncho()) {
            basJugador.setX(getWidth() - basJugador.getAncho());
        }
        if (basJugador.getX() < 0) {
            basJugador.setX(0);
        }

    }

    private void checaColisionBuenos(Rectangle recJugador) {
        Rectangle recBueno;

        // Revisar si el jugador está colisionando con un malo
        for(Personaje perBueno : arrBuenos){
            recBueno = new Rectangle(perBueno.getX(),
                                     perBueno.getY(),
                                     perBueno.getAncho(),
                                     perBueno.getAlto());
            if(recJugador.intersects(recBueno)){
                iScore += 10;
                sBueno.play();
                reposicionarBueno(perBueno);
            }

            // Revisar si el personaje llegó al la orilla del applet
            if(perBueno.getX() > getWidth()){
                reposicionarBueno(perBueno);
            }
        }
    }

    private void checaColisionMalos(Rectangle recJugador) {
        Rectangle recMalo;
        // Revisar si el jugador está colisionando con un malo
        for(Personaje perMalo: arrMalos){
            recMalo = new Rectangle(perMalo.getX(),
                                    perMalo.getY(),
                                    perMalo.getAncho(),
                                    perMalo.getAlto());
            if(recJugador.intersects(recMalo)){
                iContGolpe++;
                reposicionarMalo(perMalo);
            }

            // Revisar si el personaje llegó al la orilla del applet
            if(perMalo.getX() < 0){
                reposicionarMalo(perMalo);
            }
        }
    }

    private void reposicionarMalo(Personaje perMalo) {
        int iPosX = getXRandom() + getWidth();
        int iPosY = getYRandom();
        perMalo.setX(iPosX);
        perMalo.setY(iPosY);
    }

    private void reposicionarBueno(Personaje perBueno) {
        int iPosX = getXRandom() - getWidth();
        int iPosY = getYRandom();
        perBueno.setX(iPosX);
        perBueno.setY(iPosY);
    }


    /**
     * update
     *
     * Metodo sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor y
     * define cuando usar ahora el paint
     *
     * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
     *
     */
    public void update (Graphics graGrafico){
        // Inicializan el DoubleBuffer
        if (imaImagenApplet == null){
            imaImagenApplet = createImage (this.getSize().width,
                                           this.getSize().height);
            graGraficaApplet = imaImagenApplet.getGraphics ();
        }
        // Actualiza graficos
        paint(graGraficaApplet);
        // Dibuja la imagen actualizada
        graGrafico.drawImage (imaImagenApplet, 0, 0, this);
    }


    /**
     * paint
     *
     * Metodo sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada,
     * ademas que cuando la imagen es cargada te despliega una advertencia.
     *
     * @param graDibujo es el objeto de <code>Graphics</code> usado para dibujar.
     *
     */
    @Override
    public void paint(Graphics graDibujo) {
        // si la imagen ya se cargo
        if (basJugador != null && imaImagenFondo != null && !bFinJuego) {
            if(iVidas > 0) {
                // Dibuja la imagen de fondo
                graDibujo.drawImage(imaImagenFondo,
                                    0,
                                    0,
                                    getWidth(),
                                    getHeight(),
                                    this);
                //Dibuja los objetos principales del Applet
                basJugador.paint(graDibujo, this);
                for (Personaje perMalo : arrMalos) {
                    perMalo.paint(graDibujo, this);
                }
                for (Personaje perBueno : arrBuenos) {
                    perBueno.paint(graDibujo, this);
                }
                graDibujo.drawString("Score: " + iScore + "   Vidas:" + iVidas,
                                     20, 20);
            }
            else{
                graDibujo.drawImage(imaGameOver,
                                    0,
                                    0,
                                    getWidth(),
                                    getHeight(),
                                    this);
            }
        }
        // sino se ha cargado se dibuja un mensaje
        else {
            //Da un mensaje mientras se carga el dibujo
            graDibujo.drawString("No se cargo la imagen..", 20, 20);
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent key) {
        iTeclaActual = key.getKeyCode();
    }

    @Override
    public void keyReleased(KeyEvent key) {

    }
    
}
