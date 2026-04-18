import java.util.Random;
import java.util.Scanner;

public class Jogo {
    Scanner leitor;
    boolean jogoRodando;
    Jogador jogador;
    Random aleatorio;

    public Jogo() {
        leitor = new Scanner(System.in);
        jogoRodando = true;
        jogador = null;
        aleatorio = new Random();
    }

    public void iniciar() {
        while (jogoRodando) {
            mostrarMenu();
            int opcao = leitor.nextInt();
            leitor.nextLine();

            switch (opcao) {
                case 0:
                    System.out.println("Saindo do jogo...");
                    jogoRodando = false;
                    break;

                case 1:
                    novoJogo();
                    break;

                case 2:
                    verStatus();
                    break;

                case 3:
                    explorar();
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

            System.out.println();
        }
    }

    public void mostrarMenu() {
        System.out.println("=== RPG EM TERMINAL ===");
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status");
        System.out.println("3 - Explorar");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
    }

    public void novoJogo() {
        System.out.print("Digite o nome do personagem: ");
        String nome = leitor.nextLine();

        jogador = new Jogador(nome);

        System.out.println("Novo jogo iniciado!");
        System.out.println("Personagem criado com sucesso.");
    }

    public void verStatus() {
        if (jogador == null) {
            System.out.println("Nenhum personagem foi criado ainda.");
        } else {
            jogador.mostrarStatus();
        }
    }

    public void explorar() {
        if (jogador == null) {
            System.out.println("Crie um personagem antes de explorar.");
            return;
        }

        System.out.println(jogador.nome + " saiu para explorar...");
        int evento = aleatorio.nextInt(5);

        if (evento == 0) {
            if (jogador.nivel >= 3) {
                int sorteioChefe = aleatorio.nextInt(4);

                if (sorteioChefe == 0) {
                    Inimigo chefe = criarChefe();
                    System.out.println("O CHEFÃO apareceu!");
                    System.out.println("Um " + chefe.nome + " surgiu diante de " + jogador.nome + "!");
                    lutar(chefe);
                    return;
                }
            }

            Inimigo inimigo = criarInimigoAleatorio();
            System.out.println("Um " + inimigo.nome + " apareceu no caminho!");
            lutar(inimigo);

        } else if (evento == 1) {
            System.out.println(jogador.nome + " encontrou uma poção!");
            jogador.inventario.adicionarPocao();
            System.out.println("Agora " + jogador.nome + " tem " + jogador.inventario.quantidadePocoes + " poção(ões).");

        } else if (evento == 2) {
            System.out.println(jogador.nome + " encontrou uma bomba!");
            jogador.inventario.adicionarBomba();
            System.out.println("Agora " + jogador.nome + " tem " + jogador.inventario.quantidadeBombas + " bomba(s).");

        } else if (evento == 3) {
            System.out.println(jogador.nome + " encontrou um escudo!");
            jogador.inventario.adicionarEscudo();
            System.out.println("Agora " + jogador.nome + " tem " + jogador.inventario.quantidadeEscudos + " escudo(s).");

        } else {
            System.out.println(jogador.nome + " andou bastante, mas não encontrou nada.");
        }
    }

    public void lutar(Inimigo inimigo) {
        System.out.println("A batalha começou!");

        while (jogador.vida > 0 && inimigo.vida > 0) {
            System.out.println();
            System.out.println("=== COMBATE ===");
            System.out.println(jogador.nome + " - Vida: " + jogador.vida);
            System.out.println(inimigo.nome + " - Vida: " + inimigo.vida);
            System.out.println("1 - Atacar");
            System.out.println("2 - Fugir");
            System.out.println("3 - Usar item");
            System.out.print("Escolha: ");

            int opcaoCombate = leitor.nextInt();
            leitor.nextLine();

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
        System.out.println(jogador.nome + " atacou o " + inimigo.nome + "!");
        inimigo.vida = inimigo.vida - jogador.ataque;

        if (inimigo.vida < 0) {
            inimigo.vida = 0;
        }

        System.out.println("O " + inimigo.nome + " ficou com " + inimigo.vida + " de vida.");

        if (inimigo.vida <= 0) {
            System.out.println(jogador.nome + " venceu a batalha!");

            if (inimigo.raridade == RaridadeDoInimigo.CHEFE) {
                System.out.println("O chefão foi derrotado!");
                jogador.ganharExperiencia(30);
            } else {
                jogador.ganharExperiencia(10);
            }

        } else {
            ataqueDoInimigo(inimigo);
        }
    }

    public void ataqueDoInimigo(Inimigo inimigo) {
        int danoRecebido = inimigo.ataque - jogador.defesa;

        if (jogador.escudoAtivo) {
            danoRecebido = danoRecebido - 5;
            System.out.println("O escudo de " + jogador.nome + " reduziu ainda mais o dano!");
            jogador.escudoAtivo = false;
        }

        if (danoRecebido < 0) {
            danoRecebido = 0;
        }

        System.out.println("O " + inimigo.nome + " atacou " + jogador.nome + "!");
        System.out.println("A defesa de " + jogador.nome + " reduziu o dano.");

        jogador.vida = jogador.vida - danoRecebido;

        if (jogador.vida < 0) {
            jogador.vida = 0;
        }

        System.out.println(jogador.nome + " recebeu " + danoRecebido + " de dano.");
        System.out.println(jogador.nome + " ficou com " + jogador.vida + " de vida.");

        if (jogador.vida <= 0) {
            System.out.println(jogador.nome + " foi derrotado.");
            jogoRodando = false;
        }
    }

    public boolean tentarFugir(Inimigo inimigo) {
        int fuga = aleatorio.nextInt(2);

        if (fuga == 0) {
            System.out.println(jogador.nome + " conseguiu fugir da batalha!");
            return true;
        } else {
            System.out.println(jogador.nome + " tentou fugir, mas não conseguiu!");
            System.out.println("O " + inimigo.nome + " aproveitou para atacar!");
            ataqueDoInimigo(inimigo);
            return false;
        }
    }

    public void abrirMenuDeItens(Inimigo inimigo) {
        System.out.println();
        System.out.println("=== ITENS ===");
        System.out.println("1 - Poção (" + jogador.inventario.quantidadePocoes + ")");
        System.out.println("2 - Bomba (" + jogador.inventario.quantidadeBombas + ")");
        System.out.println("3 - Escudo (" + jogador.inventario.quantidadeEscudos + ")");
        System.out.println("0 - Voltar");
        System.out.print("Escolha: ");

        int opcaoItem = leitor.nextInt();
        leitor.nextLine();

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
        if (!jogador.inventario.temPocao()) {
            System.out.println(jogador.nome + " não tem poções.");
            return;
        }

        if (jogador.vida == 100) {
            System.out.println(jogador.nome + " já está com a vida cheia.");
            return;
        }

        jogador.inventario.usarPocao();
        jogador.vida = jogador.vida + 20;

        if (jogador.vida > 100) {
            jogador.vida = 100;
        }

        System.out.println(jogador.nome + " usou uma poção!");
        System.out.println("A vida de " + jogador.nome + " foi recuperada.");
        System.out.println("Vida atual: " + jogador.vida);
        System.out.println("Poções restantes: " + jogador.inventario.quantidadePocoes);

        if (inimigo.vida > 0) {
            System.out.println("Enquanto " + jogador.nome + " usava a poção, o " + inimigo.nome + " atacou!");
            ataqueDoInimigo(inimigo);
        }
    }

    public void usarBomba(Inimigo inimigo) {
        if (!jogador.inventario.temBomba()) {
            System.out.println(jogador.nome + " não tem bombas.");
            return;
        }

        jogador.inventario.usarBomba();

        int danoDaBomba = 15;
        inimigo.vida = inimigo.vida - danoDaBomba;

        if (inimigo.vida < 0) {
            inimigo.vida = 0;
        }

        System.out.println(jogador.nome + " usou uma bomba!");
        System.out.println("A bomba causou " + danoDaBomba + " de dano no " + inimigo.nome + "!");
        System.out.println("O " + inimigo.nome + " ficou com " + inimigo.vida + " de vida.");
        System.out.println("Bombas restantes: " + jogador.inventario.quantidadeBombas);

        if (inimigo.vida <= 0) {
            System.out.println(jogador.nome + " venceu a batalha!");

            if (inimigo.raridade == RaridadeDoInimigo.CHEFE) {
                System.out.println("O chefão foi derrotado!");
                jogador.ganharExperiencia(30);
            } else {
                jogador.ganharExperiencia(10);
            }

        } else {
            System.out.println("O " + inimigo.nome + " sobreviveu e contra-atacou!");
            ataqueDoInimigo(inimigo);
        }
    }

    public void usarEscudo(Inimigo inimigo) {
        if (!jogador.inventario.temEscudo()) {
            System.out.println(jogador.nome + " não tem escudos.");
            return;
        }

        if (jogador.escudoAtivo) {
            System.out.println(jogador.nome + " já está com um escudo ativo.");
            return;
        }

        jogador.inventario.usarEscudo();
        jogador.escudoAtivo = true;

        System.out.println(jogador.nome + " usou um escudo!");
        System.out.println("O próximo ataque recebido será reduzido.");
        System.out.println("Escudos restantes: " + jogador.inventario.quantidadeEscudos);

        if (inimigo.vida > 0) {
            System.out.println("O " + inimigo.nome + " atacou mesmo assim!");
            ataqueDoInimigo(inimigo);
        }
    }

    public Inimigo criarInimigoAleatorio() {
        int tipoInimigo = aleatorio.nextInt(3);

        if (tipoInimigo == 0) {
            return new Inimigo("Goblin", RaridadeDoInimigo.COMUM, 30, 8);
        } else if (tipoInimigo == 1) {
            return new Inimigo("Lobo", RaridadeDoInimigo.COMUM, 40, 6);
        } else {
            return new Inimigo("Esqueleto", RaridadeDoInimigo.COMUM, 25, 10);
        }
    }

    public Inimigo criarChefe() {
        return new Inimigo("Dragão", RaridadeDoInimigo.CHEFE, 80, 15);
    }
}