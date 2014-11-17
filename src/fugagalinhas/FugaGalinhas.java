/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fugagalinhas;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Queue;
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
        int nCasas = 8;
        Estado estado = new Estado();
        estado.Galinha = new Ponto(4, 2);
        int kLobos = 3;
        estado.Lobos = new ArrayList<>(kLobos);
        estado.Lobos.add(new Ponto(8, 1));
        estado.Lobos.add(new Ponto(1, 7));
        estado.Lobos.add(new Ponto(4, 7));

        System.out.println("galinha começa em: " + estado.Galinha.toString() + ", deve fugir em " + numMovimentos + " movimentos");
        caminhoSucesso = new Stack<>();
        printLevel = 1;

        boolean fugiu = rotaEscapatoria(nCasas, estado);
        if (fugiu && !caminhoSucesso.isEmpty()) {
            System.out.println("Galinha fugiu, movimentos:");
            caminhoSucesso.add(estado);
            printLevel = 2;
            while (!caminhoSucesso.isEmpty()) {
                printTabuleiro(nCasas, caminhoSucesso.pop());
            }
        }
    }

    private static boolean rotaEscapatoria(int n, Estado estado) {
        if (capturada(estado.Galinha, estado.Lobos)) {
            System.out.println("galinha capturada! em: " + estado.Galinha.toString() + ", restando " + numMovimentos + " movimentos");
            return false;
        }
        if (numMovimentos <= 0) {
            System.out.println("fim dos movimentos, galinha em: " + estado.Galinha.toString());
            return true;
        }
        //*
        Estado newEstado = new Estado();
        boolean fugiu = false;
        //mov esquerda
        if (estado.Galinha.getX() > 1) {
            newEstado.Galinha = new Ponto(estado.Galinha.getX() - 1, estado.Galinha.getY());
            newEstado.Lobos = movimentarLobos(newEstado.Galinha, estado.Lobos);
            if (printLevel >= 1) {
                printTabuleiro(n, estado);
                System.out.println(numMovimentos + " movimentos, galinha movimenta de " + estado.Galinha.toString() + " para << " + newEstado.Galinha.toString());
                printTabuleiro(n, newEstado);
            }
            numMovimentos--;
            fugiu = rotaEscapatoria(n, newEstado);
            numMovimentos++;
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
                System.out.println(numMovimentos + " movimentos, galinha movimenta de " + estado.Galinha.toString() + " para >> " + newEstado.Galinha.toString());
                printTabuleiro(n, newEstado);
            }
            numMovimentos--;
            fugiu = rotaEscapatoria(n, newEstado);
            numMovimentos++;
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
                System.out.println(numMovimentos + " movimentos, galinha movimenta de " + estado.Galinha.toString() + " para /\\ " + newEstado.Galinha.toString());
                printTabuleiro(n, newEstado);
            }
            numMovimentos--;
            fugiu = rotaEscapatoria(n, newEstado);
            numMovimentos++;
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
                System.out.println(numMovimentos + " movimentos, galinha movimenta de " + estado.Galinha.toString() + " para \\/ " + newEstado.Galinha.toString());
                printTabuleiro(n, newEstado);
            }
            numMovimentos--;
            fugiu = rotaEscapatoria(n, newEstado);
            numMovimentos++;
            if (fugiu) {
                caminhoSucesso.add(newEstado);
                return true;
            }
        }
        //*/
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
            if (!l.equals(g)) {
                if (l.y == g.y && l.x != g.x) {
                    l.x = l.x + (g.x < l.x ? -1 : 1);
                } else if (l.y != g.y && l.x == g.x) {
                    l.y = l.y + (g.y > l.y ? 1 : -1);
                } else {
                    l.x = l.x + (g.x < l.x ? -1 : 1);
                    l.y = l.y + (g.y > l.y ? 1 : -1);
                }
            }
            ret.add(new Ponto(l.x, l.y));
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
