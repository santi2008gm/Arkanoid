package CLASES;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Paleta {
    public Rectangle forma;
    private boolean moverIzquierda = false;
    private boolean moverDerecha = false;
    private final int VELOCIDAD = 8;

    public Paleta(int x, int y, int ancho, int alto) {
        forma = new Rectangle(x, y, ancho, alto);
    }

    public void setMoverIzquierda(boolean mover) { this.moverIzquierda = mover; }
    public void setMoverDerecha(boolean mover) { this.moverDerecha = mover; }

    public void actualizar(int limite) {
        if (moverIzquierda) {
            forma.x -= VELOCIDAD;
            if (forma.x < 0) forma.x = 0;
        }
        if (moverDerecha) {
            forma.x += VELOCIDAD;
            if (forma.x > limite - forma.width) forma.x = limite - forma.width;
        }
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(forma.x, forma.y, forma.width, forma.height);
        g.setColor(Color.WHITE);
        g.drawRect(forma.x, forma.y, forma.width, forma.height);
    }
}
