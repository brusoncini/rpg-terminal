package jogo;

import entidades.Inimigo;
import entidades.RaridadeDoInimigo;

import java.util.Random;

public class FabricaDeInimigos {
    private static final Random aleatorio = new Random();


    public static Inimigo criarInimigoAleatorio() {
        int tipoInimigo = aleatorio.nextInt(3);

        if (tipoInimigo == 0) {
            return new Inimigo("Goblin", RaridadeDoInimigo.COMUM, 30, 9);
        } else if (tipoInimigo == 1) {
            return new Inimigo("Lobo", RaridadeDoInimigo.COMUM, 40, 11);
        } else {
            return new Inimigo("Esqueleto", RaridadeDoInimigo.COMUM, 25, 10);
        }
    }

    public static Inimigo criarElite() {
        int tipoInimigo = aleatorio.nextInt(2);

        if (tipoInimigo == 0) {
            return new Inimigo("Capitão Orc", RaridadeDoInimigo.ELITE, 90, 13);
        } else {
            return new Inimigo("Cavaleiro Sombrio", RaridadeDoInimigo.ELITE, 70, 17);
        }
    }

    public static Inimigo criarChefe() {
        return new Inimigo("Rei Dragão", RaridadeDoInimigo.CHEFE, 95, 20);
    }
}
