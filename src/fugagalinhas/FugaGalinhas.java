/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fugagalinhas;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 11112375
 */
public class FugaGalinhas {

    
        private static int numMovimentos = 10;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int nCasas = 8;
        Point posGalinha = new Point(4, 2);
        int kLobos = 3;
        List<Point> pLobos = new ArrayList<Point>(kLobos);
        pLobos.add(new Point(8, 1));
        pLobos.add(new Point(1, 7));
        pLobos.add(new Point(4, 7));

        rotaEscapatoria(nCasas, posGalinha, pLobos);
    }

    private static boolean rotaEscapatoria(int nCasas, Point posGalinha, List<Point> pLobos) {
        printTabuleiro(nCasas, posGalinha, pLobos);
        if(numMovimentos<=0){
            System.out.println("qtd movimentos... fim");
            return false;
        }
        if (capturada(posGalinha, pLobos)) {
            System.out.println("galinha capturada! em: " + posGalinha.toString());
            return false;
        }
        //*
        Point newPos = null;
        if (posGalinha.getX() > 1) {
            newPos = new Point(posGalinha.getX() - 1, posGalinha.getY());
            List<Point> newLobos = movimentarLobos(newPos, pLobos);
            numMovimentos--;
            if (rotaEscapatoria(nCasas, newPos, newLobos)) {
                return true;
            }
            numMovimentos++;
        }
        if (posGalinha.getX() < nCasas) {
            newPos = new Point(posGalinha.getX() + 1, posGalinha.getY());
            List<Point> newLobos = movimentarLobos(newPos, pLobos);
            numMovimentos--;
            if (rotaEscapatoria(nCasas, newPos, newLobos)) {
                return true;
            }
            numMovimentos++;
        }
        
        //*/
        return false;
    }

    private static boolean capturada(Point posGalinha, List<Point> pLobos) {
        for (int i = 0; i < pLobos.size(); i++) {
            if (pLobos.get(i).equals(posGalinha)) {
                return true;
            }
        }
        return false;
    }

    private static List<Point> movimentarLobos(Point g, List<Point> pLobos) {
        List<Point> ret = new ArrayList<>(pLobos.size());

        for (Point l : pLobos) {
            if (l.y == g.y) {
                l.x = l.x + (g.x < l.x ? -1 : 1);
            } else if (l.x == g.x) {
                l.y = l.y + (g.y > l.y ? 1 : -1);
            } else {
                l.x = l.x + (g.x < l.x ? -1 : 1);
                l.y = l.y + (g.y > l.y ? 1 : -1);
            }
            ret.add(l);
        }

        return ret;
    }

    private static void printTabuleiro(int nCasas, Point posGalinha, List<Point> pLobos) {

        String hr = "  ";
        for (int x = 1; x <= nCasas; x++) {
            hr+=" "+x;
        }
        System.out.println(hr);
        
        for (int y = 1; y <= nCasas; y++) {
            String linha = y+" |";
            for (int x = 1; x <= nCasas; x++) {
                Point casa = new Point(x, y);
                if (pLobos.contains(casa)) {
                    linha += ("L|");
                } else if (casa.equals(posGalinha)) {
                    linha += ("G|");
                } else {
                    linha += (" |");
                }
            }
            System.out.println(linha);
        }
        System.out.println("------------------------------------------------------");
    }

    private static class Point {

        private int x, y;

        public Point() {
        }

        private Point(int x, int y) {
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
            if (!(o instanceof Point)) {
                return false;
            }

            Point p = (Point) o;
            return (p.x == this.x && p.y == this.y);

        }

        @Override
        public String toString() {
            return "x=" + x + ",y=" + y;
        }

    }

}
