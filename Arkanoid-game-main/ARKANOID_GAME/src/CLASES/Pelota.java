package CLASES;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Pelota {
    public Rectangle forma;
    public int dirX;
    public int dirY;
    private Color color;

    public Pelota(int x, int y, int ancho, int alto, int dirX, int dirY) {
        forma = new Rectangle(x, y, ancho, alto);
        this.dirX = dirX;
        this.dirY = dirY;
        this.color = Color.RED;
    }

    public void mover(int anchoPanel, int altoPanel) {
        forma.x += dirX;
        forma.y += dirY;

        if (forma.x <= 0 || forma.x >= anchoPanel - forma.width) {
            dirX *= -1;
            if (forma.x <= 0) forma.x = 0;
            if (forma.x >= anchoPanel - forma.width) forma.x = anchoPanel - forma.width;
        }
        if (forma.y <= 0) {
            dirY *= -1;
            forma.y = 0;
        }
    }

    public boolean colisionConPaleta(Paleta paleta) {
        if (forma.intersects(paleta.forma)) {
            double puntoImpacto = (forma.x + forma.width / 2) - (paleta.forma.x + paleta.forma.width / 2);
            double porcentaje = puntoImpacto / (paleta.forma.width / 2); // -1 (izq) a 1 (der)
            
            // Ajustar dirX según zona de impacto
            int velocidadBase = Math.abs(dirY); // mantener velocidad general
            dirX = (int) (porcentaje * velocidadBase * 2); // lateralidad más marcada
            
            // Asegurar que no quede en cero
            if (Math.abs(dirX) < 1) dirX = dirX > 0 ? 1 : -1;
            
            // Siempre invertir dirY
            dirY *= -1;
            
            // Colocar la pelota justo encima de la paleta
            forma.y = paleta.forma.y - forma.height;
            
            return true;
        }
        return false;
    }

    public void rebotarVertical() { dirY *= -1; }

    public void dibujar(Graphics g) {
        g.setColor(color);
        g.fillOval(forma.x, forma.y, forma.width, forma.height);
        g.setColor(Color.WHITE);
        g.drawOval(forma.x, forma.y, forma.width, forma.height);
    }
}
