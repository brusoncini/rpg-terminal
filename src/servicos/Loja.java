package servicos;

import entidades.Jogador;

import java.util.Scanner;

public record Loja(Jogador jogador, Scanner leitor) {

    public void mostrarSeparador() {
        System.out.println("==============================");
    }

    public void mostrarTitulo(String titulo) {
        System.out.println();
        mostrarSeparador();
        System.out.println(titulo);
        mostrarSeparador();
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

    public void abrirLoja() {
        if (jogador == null) {
            System.out.println("Crie um personagem antes de entrar na loja.");
            return;
        }

        boolean lojaAberta = true;

        while (lojaAberta) {
            mostrarTitulo("=== LOJA ===");
            System.out.println("Ouro atual: " + jogador.getOuro());
            System.out.println("1 - Comprar poção (10 ouro)");
            System.out.println("2 - Comprar bomba (15 ouro)");
            System.out.println("3 - Comprar escudo (20 ouro)");
            System.out.println("0 - Sair da loja");
            System.out.print("Escolha: ");

            int opcaoLoja = lerOpcao();

            if (opcaoLoja == 1) {
                comprarPocao();

            } else if (opcaoLoja == 2) {
                comprarBomba();

            } else if (opcaoLoja == 3) {
                comprarEscudo();

            } else if (opcaoLoja == 0) {
                System.out.println("Saindo da loja...");
                lojaAberta = false;

            } else {
                System.out.println("Opção inválida na loja.");
            }
        }
    }

    public void comprarPocao() {
        int preco = 10;

        if (jogador.getOuro() < preco) {
            System.out.println("Ouro insuficiente para comprar poção.");
            return;
        }
        System.out.println(jogador.getNome() + " comprou uma poção!");

        jogador.gastarOuro(10);
        jogador.getInventario().adicionarPocao();
    }

    public void comprarBomba() {
        int preco = 15;

        if (jogador.getOuro() < preco) {
            System.out.println("Ouro insuficiente para comprar bomba.");
            return;
        }

        System.out.println(jogador.getNome() + " comprou uma bomba!");

        jogador.gastarOuro(preco);
        jogador.getInventario().adicionarBomba();

    }

    public void comprarEscudo() {
        int preco = 20;

        if (jogador.getOuro() < preco) {
            System.out.println("Ouro insuficiente para comprar escudo.");
            return;
        }

        System.out.println(jogador.getNome() + " comprou um escudo!");

        jogador.gastarOuro(preco);
        jogador.getInventario().adicionarEscudo();
    }
}
