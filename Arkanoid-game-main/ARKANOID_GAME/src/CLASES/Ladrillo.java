package CLASES;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ladrillo {
    public Rectangle forma;
    public boolean destruido;
    public int golpesRestantes;
    public Color colorBase;
    public Color colorActual;

    public Ladrillo(int x, int y, int ancho, int alto, Color color, int resistencia) {
        this.forma = new Rectangle(x, y, ancho, alto);
        this.destruido = false;
        this.golpesRestantes = resistencia;
        this.colorBase = color;
        this.colorActual = color;
    }

    public void golpear() {
        if (destruido || golpesRestantes == -1) return; // indestructible

        golpesRestantes--;
        if (golpesRestantes <= 0) {
            destruido = true;
        } else if (golpesRestantes == 1) {
            colorActual = Color.WHITE;
        } else {
            int r = Math.min(255, colorBase.getRed() + 80);
            int g = Math.min(255, colorBase.getGreen() + 80);
            int b = Math.min(255, colorBase.getBlue() + 80);
            colorActual = new Color(r, g, b);
        }
    }

    public void dibujar(Graphics g) {
        if (!destruido) {
            g.setColor(colorActual);
            g.fillRect(forma.x, forma.y, forma.width, forma.height);
            g.setColor(Color.BLACK);
            g.drawRect(forma.x, forma.y, forma.width, forma.height);
        }
    }
}
