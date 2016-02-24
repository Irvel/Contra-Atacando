package primerexamen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JorgeVazquez
 */


import java.awt.*;
public class Bala extends Base {
    public Bala (int iX, int iY,int iVelocidadX,int iVelocidadY, Image imagen, char cTipo){
        super(iX,iY,imagen);
        iVelocidadX = 0;
        iVelocidadY = 1;
        if (cTipo == 'A'){
            iVelocidadX = -1;
            iVelocidadY = -1;
        }else if (cTipo == 'S'){
            iVelocidadX = 1;
            iVelocidadY = -1;
        }
    }
    public void avanza(){
        
    }
}
