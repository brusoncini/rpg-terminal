package servicos;

import entidades.Inimigo;
import entidades.Jogador;
import entidades.RaridadeDoInimigo;

import java.util.Random;
import java.util.Scanner;

public class Combate {

    private final Jogador jogador;
    private final Scanner leitor;
    private final Random aleatorio;
    private boolean jogadorMorreu;
    private boolean chefeDerrotado;


    public Combate(Jogador jogador, Scanner leitor, Random aleatorio) {
        this.jogador = jogador;
        this.leitor = leitor;
        this.aleatorio = aleatorio;
        this.jogadorMorreu = false;
        this.chefeDerrotado = false;
    }

    public boolean jogadorMorreu() {
        return jogadorMorreu;
    }

    public boolean chefeDerrotado() {
        return chefeDerrotado;
    }

    public int lerOpcao() {
        while (!leitor.hasNextInt()) {
            System.out.println("Digite apenas números.");
            leitor.nextLine();
            System.out.print("Escolha: ");
        }

        int opcao = leitor.nextInt();
        leitor.nextLine();
        return opcao;
    }

    public void mostrarSeparador() {
        System.out.println("==============================");
    }

    public void mostrarTitulo(String titulo) {
        System.out.println();
        mostrarSeparador();
        System.out.println(titulo);
        mostrarSeparador();
    }

    public void mostrarRecompensas(int experiencia, int ouro) {
        mostrarTitulo("=== RECOMPENSAS ===");
        System.out.println("Experiência ganha: " + experiencia);
        System.out.println("Ouro ganho: " + ouro);
    }


    public void lutar(Inimigo inimigo) {
        mostrarTitulo("=== BATALHA ===");
        System.out.println("A batalha começou!");

        while (jogador.getVida() > 0 && inimigo.getVida() > 0) {
            System.out.println();
            System.out.println("=== COMBATE ===");
            System.out.println(jogador.getNome() + " - Vida: " + jogador.getVida() + "/" + jogador.getVidaMaxima());
            System.out.println(inimigo.getNome() + " - Vida: " + inimigo.getVida());
            System.out.println("1 - Atacar");
            System.out.println("2 - Fugir");
            System.out.println("3 - Usar item");
            System.out.print("Escolha: ");

            int opcaoCombate = lerOpcao();

            if (opcaoCombate == 1) {
                atacarInimigo(inimigo);
            } else if (opcaoCombate == 2) {
                boolean fugiu = tentarFugir(inimigo);
                if (fugiu) {
                    return;
                }
            } else if (opcaoCombate == 3) {
                abrirMenuDeItens(inimigo);
            } else {
                System.out.println("Opção inválida no combate.");
            }
        }
    }


    public void atacarInimigo(Inimigo inimigo) {

        System.out.println(jogador.getNome() + " atacou o " + inimigo.getNome() + "!");
        inimigo.tomarDano(jogador.getAtaque());

        if (inimigo.getVida() < 0) {
            inimigo.setVida(0);
        }

        System.out.println("O " + inimigo.getNome() + " ficou com " + inimigo.getVida() + " de vida.");

        if (inimigo.getVida() <= 0) {
            derrotarInimigo(inimigo);
        } else {
            ataqueDoInimigo(inimigo);
        }
    }

    public void derrotarInimigo(Inimigo inimigo) {

        mostrarTitulo("=== BATALHA ENCERRADA ===");
        System.out.println(jogador.getNome() + " venceu a batalha!");

        if (inimigo.getRaridade() == RaridadeDoInimigo.CHEFE) {
            System.out.println("O chefão foi derrotado!");
            System.out.println("Parabéns! " + jogador.getNome() + " venceu o jogo!");
            chefeDerrotado = true;

        } else if (inimigo.getRaridade() == RaridadeDoInimigo.ELITE) {
            System.out.println("Um inimigo de elite foi derrotado!");
            jogador.ganharExperiencia(30);
            jogador.ganharOuro(30);
            mostrarRecompensas(30, 30);

        } else {
            jogador.ganharExperiencia(10);
            jogador.ganharOuro(10);
            mostrarRecompensas(10, 10);
        }
    }

    public void ataqueDoInimigo(Inimigo inimigo) {
        int danoRecebido = inimigo.getAtaque() - jogador.getDefesa();
        boolean golpeDevastador = false;

        if (inimigo.getRaridade() == RaridadeDoInimigo.CHEFE) {
            int sorteioGolpe = aleatorio.nextInt(4);

            if (sorteioGolpe == 0) {
                danoRecebido = danoRecebido + 8;
                golpeDevastador = true;
            }
        }

        if (jogador.isEscudoAtivo()) {
            danoRecebido = danoRecebido - 5;
            System.out.println("O escudo de " + jogador.getNome() + " reduziu ainda mais o dano!");
            jogador.setEscudoAtivo(false);
        }

        if (danoRecebido < 0) {
            danoRecebido = 0;
        }

        if (golpeDevastador) {
            System.out.println("O " + inimigo.getNome() + " usou o GOLPE DEVASTADOR!");
        }

        System.out.println("O " + inimigo.getNome() + " atacou " + jogador.getNome() + "!");
        System.out.println("A defesa de " + jogador.getNome() + " reduziu o dano.");

        jogador.tomarDano(danoRecebido);

        if (jogador.getVida() < 0) {
            jogador.setVida(0);
        }

        System.out.println(jogador.getNome() + " recebeu " + danoRecebido + " de dano.");
        System.out.println(jogador.getNome() + " ficou com " + jogador.getVida() + " de vida.");

        if (jogador.getVida() <= 0) {
            mostrarTitulo("=== FIM DE JOGO ===");
            System.out.println(jogador.getNome() + " foi derrotado.");
            System.out.println("Seu progresso terminou aqui.");
            jogadorMorreu = true;
        }
    }

    public boolean tentarFugir(Inimigo inimigo) {
        int fuga = aleatorio.nextInt(2);

        if (fuga == 0) {
            System.out.println(jogador.getNome() + " conseguiu fugir da batalha!");
            return true;
        } else {
            System.out.println(jogador.getNome() + " tentou fugir, mas não conseguiu!");
            System.out.println("O " + inimigo.getNome() + " aproveitou para atacar!");
            ataqueDoInimigo(inimigo);
            return false;
        }
    }

    public void abrirMenuDeItens(Inimigo inimigo) {
        System.out.println();
        System.out.println("=== ITENS ===");
        System.out.println("1 - Poção (" + jogador.getInventario().getQuantidadePocoes() + ")");
        System.out.println("2 - Bomba (" + jogador.getInventario().getQuantidadeBombas() + ")");
        System.out.println("3 - Escudo (" + jogador.getInventario().getQuantidadeEscudos() + ")");
        System.out.println("0 - Voltar");
        System.out.print("Escolha: ");

        int opcaoItem = lerOpcao();

        if (opcaoItem == 1) {
            usarPocao(inimigo);
        } else if (opcaoItem == 2) {
            usarBomba(inimigo);
        } else if (opcaoItem == 3) {
            usarEscudo(inimigo);
        } else if (opcaoItem == 0) {
            System.out.println("Voltando ao combate...");
        } else {
            System.out.println("Opção inválida no menu de itens.");
        }
    }

    public void usarPocao(Inimigo inimigo) {
        if (!jogador.getInventario().temPocao()) {
            System.out.println(jogador.getNome() + " não tem poções.");
            return;
        }

        if (jogador.getVida() == jogador.getVidaMaxima()) {
            System.out.println(jogador.getNome() + " já está com a vida cheia.");
            return;
        }

        int cura = jogador.getVidaMaxima() / 4;

        if (cura < 20) {
            cura = 20;
        }

        jogador.getInventario().usarPocao();
        jogador.curar(cura);

        if (jogador.getVida() > jogador.getVidaMaxima()) {
            jogador.setVida(jogador.getVidaMaxima());
        }

        System.out.println(jogador.getNome() + " usou uma poção!");
        System.out.println("Recuperou " + cura + " de vida.");
        System.out.println("Vida atual: " + jogador.getVida() + "/" + jogador.getVidaMaxima());
        System.out.println("Poções restantes: " + jogador.getInventario().getQuantidadePocoes());

        if (inimigo.getVida() > 0) {
            System.out.println("Enquanto " + jogador.getNome() + " usava a poção, o " + inimigo.getNome() + " atacou!");
            ataqueDoInimigo(inimigo);
        }
    }

    public void usarBomba(Inimigo inimigo) {
        if (!jogador.getInventario().temBomba()) {
            System.out.println(jogador.getNome() + " não tem bombas.");
            return;
        }

        jogador.getInventario().usarBomba();

        int danoDaBomba = 15;
        inimigo.tomarDano(danoDaBomba);

        if (inimigo.getVida() < 0) {
            inimigo.setVida(0);
        }

        System.out.println(jogador.getNome() + " usou uma bomba!");
        System.out.println("A bomba causou " + danoDaBomba + " de dano no " + inimigo.getNome() + "!");
        System.out.println("O " + inimigo.getNome() + " ficou com " + inimigo.getVida() + " de vida.");
        System.out.println("Bombas restantes: " + jogador.getInventario().getQuantidadeBombas());

        if (inimigo.getVida() <= 0) {
            derrotarInimigo(inimigo);
        } else {
            System.out.println("O " + inimigo.getNome() + " sobreviveu e contra-atacou!");
            ataqueDoInimigo(inimigo);
        }
    }

    public void usarEscudo(Inimigo inimigo) {
        if (!jogador.getInventario().temEscudo()) {
            System.out.println(jogador.getNome() + " não tem escudos.");
            return;
        }

        if (jogador.isEscudoAtivo()) {
            System.out.println(jogador.getNome() + " já está com um escudo ativo.");
            return;
        }

        jogador.getInventario().usarEscudo();
        jogador.setEscudoAtivo(true);

        System.out.println(jogador.getNome() + " usou um escudo!");
        System.out.println("O próximo ataque recebido será reduzido.");
        System.out.println("Escudos restantes: " + jogador.getInventario().getQuantidadeEscudos());

        if (inimigo.getVida() > 0) {
            System.out.println("O " + inimigo.getNome() + " atacou mesmo assim!");
            ataqueDoInimigo(inimigo);
        }
    }
}
