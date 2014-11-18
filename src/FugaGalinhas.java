/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package fugagalinhas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author 11112375
 */
public class FugaGalinhas {

    private static int numMovimentos = 10;
    private static int printLevel = 2;
    private static Stack<Estado> caminhoSucesso;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int lendo = 0;
        int nCasas = 8;
        int kLobos = 3;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String input;

            Estado estado = new Estado();
            while ((input = br.readLine()) != null) {
                if (!input.equals("")) {
                    //System.out.println(input);
                    if (lendo == 0) {
                        nCasas = Integer.parseInt(input);
                        lendo++;
                    } else if (lendo == 1) {
                        String[] pos = input.split(" ");
                        estado.Galinha = new Ponto(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]));
                        lendo++;
                    } else if (lendo == 2) {
                        kLobos = Integer.parseInt(input);
                        estado.Lobos = new ArrayList<>(kLobos);
                        lendo++;
                    } else if (lendo >= 3) {
                        if (estado.Lobos.size() < kLobos) {
                            String[] pos = input.split(" ");
                            estado.Lobos.add(new Ponto(Integer.parseInt(pos[0]), Integer.parseInt(pos[1])));
                        }
                        lendo++;
                    }
                } else {
                    break;
                }
            }

            if (lendo > 3) {
                System.out.println("galinha começa em: " + estado.Galinha.toString() + ", deve fugir em " + numMovimentos + " movimentos");
                caminhoSucesso = new Stack<>();
                printLevel = 0;

                boolean fugiu = rotaEscapatoria(nCasas, estado);
                if (fugiu) {
                    System.out.println("Galinha fugiu, movimentos:");
                    caminhoSucesso.add(estado);
                    printLevel = 2;
                    while (!caminhoSucesso.isEmpty()) {
                        printTabuleiro(nCasas, caminhoSucesso.pop());
                    }
                } else {
                    System.out.println("Por nenhum caminho a galinha consegue fugir em " + numMovimentos + " movimentos");
                }
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private static boolean rotaEscapatoria(int n, Estado estado) {
        if (capturada(estado.Galinha, estado.Lobos)) {
            if (printLevel >= 1) {
                System.out.println("galinha capturada! em: " + estado.Galinha.toString() + ", restando " + numMovimentos + " movimentos");
            }
            return false;
        }
        if (numMovimentos <= 0) {
            System.out.println("fim dos movimentos, galinha em: " + estado.Galinha.toString());
            return true;
        }

        numMovimentos--;
        //*
        Estado newEstado = new Estado();
        boolean fugiu = false;
        //mov esquerda
        if (estado.Galinha.getX() > 1) {
            newEstado.Galinha = new Ponto(estado.Galinha.getX() - 1, estado.Galinha.getY());
            newEstado.Lobos = movimentarLobos(newEstado.Galinha, estado.Lobos);
            if (printLevel >= 1) {
                printTabuleiro(n, estado);
                System.out.println("galinha movimenta de " + estado.Galinha.toString() + " para << " + newEstado.Galinha.toString());
                printTabuleiro(n, newEstado);
            }
            fugiu = rotaEscapatoria(n, newEstado);
            if (fugiu) {
                caminhoSucesso.add(newEstado);
                return true;
            }
        }
        //mov direita
        if (estado.Galinha.getX() < n) {
            newEstado.Galinha = new Ponto(estado.Galinha.getX() + 1, estado.Galinha.getY());
            newEstado.Lobos = movimentarLobos(newEstado.Galinha, estado.Lobos);
            if (printLevel >= 1) {
                printTabuleiro(n, estado);
                System.out.println("galinha movimenta de " + estado.Galinha.toString() + " para >> " + newEstado.Galinha.toString());
                printTabuleiro(n, newEstado);
            }
            fugiu = rotaEscapatoria(n, newEstado);
            if (fugiu) {
                caminhoSucesso.add(newEstado);
                return true;
            }
        }
        //mov cima
        if (estado.Galinha.getY() > 1) {
            newEstado.Galinha = new Ponto(estado.Galinha.getX(), estado.Galinha.getY() - 1);
            newEstado.Lobos = movimentarLobos(newEstado.Galinha, estado.Lobos);
            if (printLevel >= 1) {
                printTabuleiro(n, estado);
                System.out.println("galinha movimenta de " + estado.Galinha.toString() + " para /\\ " + newEstado.Galinha.toString());
                printTabuleiro(n, newEstado);
            }
            fugiu = rotaEscapatoria(n, newEstado);
            if (fugiu) {
                caminhoSucesso.add(newEstado);
                return true;
            }
        }
        //mov baixo
        if (estado.Galinha.getY() < n) {
            newEstado.Galinha = new Ponto(estado.Galinha.getX(), estado.Galinha.getY() + 1);
            newEstado.Lobos = movimentarLobos(newEstado.Galinha, estado.Lobos);
            if (printLevel >= 1) {
                printTabuleiro(n, estado);
                System.out.println("galinha movimenta de " + estado.Galinha.toString() + " para \\/ " + newEstado.Galinha.toString());
                printTabuleiro(n, newEstado);
            }
            fugiu = rotaEscapatoria(n, newEstado);
            if (fugiu) {
                caminhoSucesso.add(newEstado);
                return true;
            }
        }
        //*/
        numMovimentos++;
        return false;
    }

    private static boolean capturada(Ponto posGalinha, List<Ponto> pLobos) {
        for (int i = 0; i < pLobos.size(); i++) {
            if (pLobos.get(i).equals(posGalinha)) {
                return true;
            }
        }
        return false;
    }

    private static List<Ponto> movimentarLobos(Ponto g, List<Ponto> pLobos) {
        List<Ponto> ret = new ArrayList<>(pLobos.size());

        for (Ponto l : pLobos) {
            Ponto newL = new Ponto(l.x, l.y);
            if (!l.equals(g)) {
                if (l.y == g.y && l.x != g.x) {
                    newL.x = l.x + (g.x < l.x ? -1 : 1);
                } else if (l.y != g.y && l.x == g.x) {
                    newL.y = l.y + (g.y > l.y ? 1 : -1);
                } else {
                    newL.x = l.x + (g.x < l.x ? -1 : 1);
                    newL.y = l.y + (g.y > l.y ? 1 : -1);
                }
            }
            ret.add(newL);
        }

        return ret;
    }

    private static void printTabuleiro(int nCasas, Estado estadoTabuleiro) {
        if (printLevel >= 2 && estadoTabuleiro != null) {
            String hr = "  ";
            for (int x = 1; x <= nCasas; x++) {
                hr += " " + x;
            }
            System.out.println(hr);

            for (int y = 1; y <= nCasas; y++) {
                String linha = y + " |";
                for (int x = 1; x <= nCasas; x++) {
                    Ponto casa = new Ponto(x, y);
                    if (estadoTabuleiro.Lobos.contains(casa)) {
                        linha += ("L|");
                    } else if (casa.equals(estadoTabuleiro.Galinha)) {
                        linha += ("G|");
                    } else {
                        linha += (" |");
                    }
                }
                System.out.println(linha);
            }
            System.out.println("------------------------------------------------------");
        }
    }

    private static class Ponto {

        private int x, y;

        public Ponto() {
        }

        private Ponto(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private int getX() {
            return x;
        }

        private int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Ponto)) {
                return false;
            }

            Ponto p = (Ponto) o;
            return (p.x == this.x && p.y == this.y);

        }

        @Override
        public String toString() {
            return "x=" + x + ",y=" + y;
        }

    }

    private static class Estado {

        private List<Ponto> Lobos;
        private Ponto Galinha;

        public Estado() {
        }
    }

}
