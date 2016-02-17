/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primerexamen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author Irvel
 */
public class PrimerExamen extends JFrame implements Runnable, KeyListener {
  
    private Base basJugador;
    private ArrayList<Personaje> arrMalos;
    private ArrayList<Personaje> arrBuenos;
    private Image imaImagenFondo;        // Para dibujar la imagen de fondo
    private Image imaGameOver;           // Para dibujar la imagen final
    private int iVidas;                  // El # de vidas del jugador
    private int iScore;                  // El score del jugador

    // El numero de veces que le jugador ha golpeado un malo
    private int iContGolpe;
    private int iTeclaActual;            // Almacena la ultima tecla presionada
    private long lTiempoActual;          // El tiempo desde la ultima
    private Image imaImagenApplet;       // Imagen a proyectar en Applet
    private Graphics graGraficaApplet;   // Objeto grafico de la Imagen
    private SoundClip sBueno;            // Sonido colision con un bueno
    private SoundClip sMalo;             // Sonido colision con un malo
    private static final int IWIDTH = 800;
    private static final int IHEIGHT = 600;
    private String sNombreArchivo;       // Nombre del archivo
    private String[] arr;                // Arreglo del archivo divido.
    private int iCantidadMalos;         //Cantidad al azar de malos
    private int iCantidadBuenos;        //Cantidad al azar de buenos



    /**
     * PrimerExamen()
     *
     * Metodo sobrescrito de la clase <code>JFrame</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos
     * a usarse en el <code>Applet</code> y se definen funcionalidades.
     *
     */

     /*
     *Constructor default
     */
    
    public void init(){
        sNombreArchivo = "Puntaje.txt";
        iVidas = 5;
        iScore = 0;
        iTeclaActual = 0;
        // Define la imagen del jugador principal
        URL urlJugador = this.getClass().getResource("sJugador.gif");

        // Calcula el centro tomando en cuenta las dimensiones del jugador
        int iMitadX = (800 / 2) - (90 / 2);
        int iMitadY = (600 / 2) - (121 / 2);

        // Crea el objeto del jugador principal en el centro del mapa
        
        basJugador = new Base(iMitadX,
                              iMitadY,
                              10,
                              10,
                              Toolkit.getDefaultToolkit().getImage(urlJugador));

        /* Crea un número de personajes malos y buenos; y los almacena en
         * sus respectivos arreglos */

        // Genera de 8 a 10 malos de forma aleatoria
        iCantidadMalos = (int)(Math.random() * 3) + 8;
        cargarMalos(iCantidadMalos);

        // Genera de 10 a 13 buenos de forma aleatoria
        iCantidadBuenos = (int)(Math.random() * 4) + 10;
        crearBuenos(iCantidadBuenos);

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
    
    public PrimerExamen() {
        init();
        Thread th = new Thread(this);
        th.start();
    }
    
    
    /**
     * cargarMalos
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
     * crearMalos
     *
     * Método que crea una cantidad pre-determinada de personajes malos con
     * coordenadas aleatorias fuera del applet de la parte derecha. Utiliza
     * las imagenes ya cargadas de los en arrImaMalo
     *
     * @param iCantidad es el <code>número</code> de objetos malos a crear.
     *
     */

    private void crearMalos(int iCantidad, ArrayList<CuadroDeAnimacion> arrCuadros) {
        arrMalos = new ArrayList<>();
        for (int i = 0; i < iCantidad; i++) {
            //Genera una posición aleatoria fuera del applet por la derecha
            int iPosX = getXRandom() + 800;
            int iPosY = getYRandom();

            // Agregar un objeto nuevo de personaje con velocidad de 3 o 5
            arrMalos.add(new Personaje(iPosX,
                                       iPosY,
                                       new Animacion(arrCuadros),
                                       3,
                                       5));
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
        Image imaBueno = Toolkit.getDefaultToolkit()
                                .getImage(urlImaBueno)
                                .getScaledInstance(50,50, Image.SCALE_SMOOTH);

        for (int i = 0; i < iCantidad; i++) {
            // Genera una posición aleatoria fuera del applet por la izquierda
            int iPosX = getXRandom() - 800;
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
        sBueno = new SoundClip("aPuntosGanados.wav");
        sMalo = new SoundClip("aVidaPerdida.wav");
    }


    /**
     * getXRandom
     *
     * Método que regresa una coordenada aleatoria para el eje X que se
     * encuentra dentro del applet actual.
     *
     */
    private int getXRandom(){
        return (int)(Math.random() * (800));
    }


    /**
     * getYRandom
     *
     * Método que regresa una coordenada aleatoria para el eje Y que se
     * encuentra dentro del applet actual.
     *
     */
    private int getYRandom(){
        int iYRandom = (int)(Math.random() * (551) + 50);
        // Evitar que llegue a aparecer debajo del eje Y
        iYRandom = iYRandom > 600 - 100 ? 600 - 100 : iYRandom;
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
    /*
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
        moverPersonajes();
        animarMalos();

        if(iContGolpe >= 5){
            iVidas--;
            sMalo.play();
            iContGolpe = 0;
        }
    }

    private void moverPersonajes() {
        // Mueve a los personajes malos
        for(Personaje perMalo : arrMalos){
            perMalo.setX(perMalo.getX() - perMalo.getVel());
        }
        // Mueve a los personajes buenos
        for(Personaje perBueno : arrBuenos){
            perBueno.setX(perBueno.getX() + perBueno.getVel());
        }
    }

    private void animarMalos() {
        // Calcula el tiempo transcurrido desde la iteracion previa
        long tiempoTranscurrido =
                System.currentTimeMillis() - lTiempoActual;
        lTiempoActual += tiempoTranscurrido;

        for(Personaje perMalo : arrMalos){
            // Actualiza los cuadros en la animacion
            perMalo.getAnimacion().actualiza(tiempoTranscurrido);
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
     * paint
     *
     * Metodo sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor y
     * define cuando usar ahora el paint
     *
     * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
     *
     */
  
    public void paint (Graphics graGrafico){
        // Inicializan el DoubleBuffer
        if (imaImagenApplet == null){
            imaImagenApplet = createImage (this.getSize().width,
                                           this.getSize().height);
            graGraficaApplet = imaImagenApplet.getGraphics ();
        }
        // Actualiza graficos
        paint1(graGraficaApplet);
        // Dibuja la imagen actualizada
        graGrafico.drawImage (imaImagenApplet, 0, 0, this);
    }


    /**
     * paint1
     *
     * Metodo sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada,
     * ademas que cuando la imagen es cargada te despliega una advertencia.
     *
     * @param graDibujo es el objeto de <code>Graphics</code> usado para dibujar.
     *
     */
    public void paint1(Graphics graDibujo) {
        // si la imagen ya se cargo
        if (basJugador != null && imaImagenFondo != null) {
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
                                     50, 50);
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
    
    public void leeArchivo() throws IOException{
        BufferedReader fileIn;
        try{
            fileIn = new BufferedReader(new FileReader(sNombreArchivo));
        }catch (FileNotFoundException e){
            File primerexamen = new File(sNombreArchivo);
            PrintWriter fileOut = new PrintWriter(primerexamen);
            fileOut.println("100,demo");
            fileOut.close();
            fileIn = new BufferedReader(new FileReader(sNombreArchivo));
        }
        String sDato = fileIn.readLine();
        int num = (Integer.parseInt(sDato));
        iVidas = num;
        leerPersonajesArchivo(fileIn);
        fileIn.close();
    }

    private void leerPersonajesArchivo(BufferedReader fileIn) throws
                                                              IOException {
        String sDato;
        int num;
        sDato = fileIn.readLine();
        num = (Integer.parseInt(sDato));
        iScore = num;
        sDato = fileIn.readLine();
        num = (Integer.parseInt(sDato));
        iCantidadMalos = num;
        leeMalosArchivo(fileIn);
        sDato = fileIn.readLine();
        num = (Integer.parseInt(sDato));
        iCantidadBuenos = num;
        leerBuenosArchivo(fileIn);
        sDato = fileIn.readLine();
        num = (Integer.parseInt(sDato));
        basJugador.setX(num);
        sDato = fileIn.readLine();
        num = (Integer.parseInt(sDato));
        basJugador.setY(num);
    }

    private void leerBuenosArchivo(BufferedReader fileIn) throws IOException {
        String sDato;
        int num;
        for (int iC = 0; iC < iCantidadBuenos; ++iC){
            sDato = fileIn.readLine();
            num = (Integer.parseInt(sDato));
            arrBuenos.get(iC).setX(num);
            sDato = fileIn.readLine();
            num = (Integer.parseInt(sDato));
            arrBuenos.get(iC).setY(num);
        }
    }

    private void leeMalosArchivo(BufferedReader fileIn) throws IOException {
        String sDato;
        int num;
        for (int iC = 0; iC < iCantidadMalos; ++iC){
            sDato = fileIn.readLine();
            num = (Integer.parseInt(sDato));
            arrMalos.get(iC).setX(num);
            sDato = fileIn.readLine();
            num = (Integer.parseInt(sDato));
            arrMalos.get(iC).setY(num);
        }
    }

    public void grabaArchivo() throws IOException{
        PrintWriter fileOut = new PrintWriter(new FileWriter(sNombreArchivo));
        fileOut.println(iVidas);
        fileOut.println(iScore);
        fileOut.println(iCantidadMalos);
        for (int iC = 0; iC < iCantidadMalos;++iC){
            fileOut.println(arrMalos.get(iC).getX());
            fileOut.println(arrMalos.get(iC).getY());
        }
        fileOut.println(iCantidadBuenos);
        for (int iC = 0; iC < iCantidadBuenos;++iC){
            fileOut.println(arrBuenos.get(iC).getX());
            fileOut.println(arrBuenos.get(iC).getY());
        }
        fileOut.println(basJugador.getX());
        fileOut.println(basJugador.getY());
        
        fileOut.close();
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent key) {
        iTeclaActual = key.getKeyCode();
        if (iTeclaActual == KeyEvent.VK_G){
            try{
                grabaArchivo();
            }catch(IOException e){
                
            }
        }else if (iTeclaActual == KeyEvent.VK_C){
            try{
                leeArchivo();
            }catch (IOException e){
                
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent key) {

    }
    
    
    public static void main(String [] args){
        PrimerExamen hola = new PrimerExamen();
        hola.setSize(IWIDTH,IHEIGHT);
        hola.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hola.setVisible(true);
    }
 
}
