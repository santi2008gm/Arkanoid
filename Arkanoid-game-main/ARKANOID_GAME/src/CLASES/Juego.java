package CLASES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Juego extends JPanel implements KeyListener, ActionListener {

    private Timer timer;
    private Timer timerPantallaNivel;
    private Paleta paleta;
    private Pelota pelota;
    private ArrayList<Ladrillo> ladrillos;
    private int filas = 5;
    private int columnas = 10;
    private int score;
    private boolean gameOver;
    private boolean ganado;
    private int nivelActual = 1;
    private int velocidadPelota = 5;
    private int golpesPaleta = 0;
    private boolean mostrandoPantallaNivel = false;
    private long tiempoInicioPantallaNivel;

    public Juego() {
        setFocusable(true);
        setRequestFocusEnabled(true);
        addKeyListener(this);
        ladrillos = new ArrayList<>();
        timer = new Timer(16, this);

        timerPantallaNivel = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mostrandoPantallaNivel) {
                    long tiempoActual = System.currentTimeMillis();
                    if (tiempoActual - tiempoInicioPantallaNivel >= 2000) { 
                        mostrandoPantallaNivel = false;
                        timerPantallaNivel.stop();
                        timer.start();
                    }
                    repaint();
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (paleta == null) {
                    iniciarPartida();
                }
            }
        });
    }

    public void iniciarPartida() {
        iniciarNivel(nivelActual);
    }

    public void setNivelActual(int nivel) {
        this.nivelActual = nivel;
    }

    private void iniciarNivel(int nivel) {
        gameOver = false;
        ganado = false;
        golpesPaleta = 0;

        int paletaX = (getWidth() - 100) / 2;
        paleta = new Paleta(paletaX, 500, 100, 20);

        int pelotaX = paleta.forma.x + paleta.forma.width / 2 - 10;
        int pelotaY = paleta.forma.y - 20;

        velocidadPelota = 3 + (nivel - 1);
        pelota = new Pelota(pelotaX, pelotaY, 20, 20, velocidadPelota, -velocidadPelota);

        if (nivel == 1 || nivel == 2) {
            inicializarLadrillos(nivel);
        } else if (nivel == 3) {
            inicializarLadrillosNivel3();
        }

        mostrarPantallaNivel();
        requestFocusInWindow();
    }
    

    private void mostrarPantallaNivel() {
        mostrandoPantallaNivel = true;
        tiempoInicioPantallaNivel = System.currentTimeMillis();
        timer.stop();
        timerPantallaNivel.start();
    }

    private void inicializarLadrillos(int nivel) {
        ladrillos.clear();
        if (getWidth() == 0) return;

        int margenSuperior = 50;
        int margenLateral = 50;
        int espacioEntreLadrillos = 10;
        int ladrilloAncho = (getWidth() - 2 * margenLateral - (columnas - 1) * espacioEntreLadrillos) / columnas;
        int ladrilloAlto = 20;

        Color[] colores = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE};

        for (int i = 0; i < filas; i++) {
            Color color = colores[i % colores.length];
            for (int j = 0; j < columnas; j++) {
                int x = margenLateral + j * (ladrilloAncho + espacioEntreLadrillos);
                int y = margenSuperior + i * (ladrilloAlto + espacioEntreLadrillos);
                int resistencia = nivel == 1 ? 1 : 2;
                ladrillos.add(new Ladrillo(x, y, ladrilloAncho, ladrilloAlto, color, resistencia));
            }
        }
    }

    private void inicializarLadrillosNivel3() {
        ladrillos.clear();
        if (getWidth() == 0) return;

        int margenSuperior = 50;
        int margenLateral = 50;
        int espacioEntreLadrillos = 10;
        int ladrilloAncho = (getWidth() - 2 * margenLateral - (columnas - 1) * espacioEntreLadrillos) / columnas;
        int ladrilloAlto = 20;

        Color[] colores = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE};

        for (int i = 0; i < filas; i++) {
            Color color = colores[i % colores.length];
            for (int j = 0; j < columnas; j++) {
                int x = margenLateral + j * (ladrilloAncho + espacioEntreLadrillos);
                int y = margenSuperior + i * (ladrilloAlto + espacioEntreLadrillos);

                int resistencia;
                double rand = Math.random();

                if (rand < 0.05) {
                    resistencia = -1;
                    color = Color.GRAY;
                } else if (rand < 0.15) {
                    resistencia = 1;
                } else if (rand < 0.7) {
                    resistencia = 2;
                } else {
                    resistencia = 3;
                }

                ladrillos.add(new Ladrillo(x, y, ladrilloAncho, ladrilloAlto, color, resistencia));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);

        if (mostrandoPantallaNivel) {
            dibujarPantallaNivel(g);
            return;
        }

        if (paleta != null) paleta.dibujar(g);
        if (pelota != null) pelota.dibujar(g);
        for (Ladrillo ladrillo : ladrillos) ladrillo.dibujar(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.drawString("Score: " + score, 10, 25);
        g.drawString("Nivel: " + nivelActual, 200, 25);

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Game Over", getWidth() / 2 - 120, getHeight() / 2);
            
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.drawString("Presiona R para reiniciar", getWidth() / 2 - 150, getHeight() / 2 + 50);
            requestFocusInWindow();
        }if (ganado) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Nivel " + nivelActual + " completado!", getWidth() / 2 - 200, getHeight() / 2);

            g.setFont(new Font("Arial", Font.PLAIN, 30));
            if (nivelActual < 3) {
                g.drawString("Presiona N para siguiente nivel", getWidth() / 2 - 180, getHeight() / 2 + 50);
            }
            g.drawString("Presiona ESPACIO para volver al menú", getWidth() / 2 - 200, getHeight() / 2 + 100);
            
            requestFocusInWindow();
        }

    }
    
    private void volverMenu() {
        JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
        ventana.dispose(); // cierra la ventana del juego
        PRINCIPAL.MenuNivel.main(null); // abre el menú principal
    }

    private void dibujarPantallaNivel(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 60));
        
        String textoNivel = "NIVEL " + nivelActual;
        int textoWidth = g.getFontMetrics().stringWidth(textoNivel);
        g.drawString(textoNivel, getWidth() / 2 - textoWidth / 2, getHeight() / 2);
        
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        String mensaje = nivelActual == 1 ? "Rompe todos los bloques!" : 
                         (nivelActual == 2 ? "¡Cuidado! Los bloques son más resistentes" : 
                         "¡Nivel 3 desafiante con bloques indestructibles!");
        int mensajeWidth = g.getFontMetrics().stringWidth(mensaje);
        g.drawString(mensaje, getWidth() / 2 - mensajeWidth / 2, getHeight() / 2 + 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (mostrandoPantallaNivel) return;

        if (!gameOver && !ganado) {
            if (paleta != null) paleta.actualizar(getWidth());
            if (pelota != null) {
                pelota.mover(getWidth(), getHeight());

                if (pelota.colisionConPaleta(paleta)) {
                    golpesPaleta++;
                    if (golpesPaleta % 3 == 0) {
                        int signoX = pelota.dirX > 0 ? 1 : -1;
                        int signoY = pelota.dirY > 0 ? 1 : -1;
                        pelota.dirX = signoX * (Math.abs(pelota.dirX) + 1);
                        pelota.dirY = signoY * (Math.abs(pelota.dirY) + 1);
                    }
                }

                for (Ladrillo ladrillo : ladrillos) {
                    if (!ladrillo.destruido && pelota.forma.intersects(ladrillo.forma)) {
                        ladrillo.golpear();
                        pelota.rebotarVertical();
                        if (ladrillo.destruido) score += 100;
                        break;
                    }
                }

                if (pelota.forma.y > getHeight()) {
                    gameOver = true;
                    timer.stop();
                }

                boolean todosDestruidos = true;
                for (Ladrillo ladrillo : ladrillos) {
                    if (!ladrillo.destruido && ladrillo.golpesRestantes != -1) {
                        todosDestruidos = false;
                        break;
                    }
                }
                
                if (todosDestruidos) {
                    ganado = true;
                    timer.stop();
                }
            }
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (mostrandoPantallaNivel) return;

        if (paleta != null) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) paleta.setMoverIzquierda(true);
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) paleta.setMoverDerecha(true);
        }

        if (gameOver && e.getKeyCode() == KeyEvent.VK_R) {
            reiniciarNivelActual(); // reinicia el nivel actual
        }

        if (ganado) {
            if (nivelActual < 3 && e.getKeyCode() == KeyEvent.VK_N) {
                siguienteNivel(); // ir al siguiente nivel
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                volverMenu(); // volver al menú principal
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (mostrandoPantallaNivel) return;

        if (paleta != null) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) paleta.setMoverIzquierda(false);
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) paleta.setMoverDerecha(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    
    private void reiniciarNivelActual() {
        score = 0;
        iniciarNivel(nivelActual);
    }

    private void reiniciarJuego() {
        nivelActual = 1;
        score = 0;
        iniciarPartida();
    }

    private void siguienteNivel() {
        nivelActual++;
        iniciarNivel(nivelActual);
    }
}
