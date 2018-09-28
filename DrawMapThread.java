package com.company;

import javax.swing.*;
import java.util.Vector;

public class DrawMapThread implements Runnable
{
    int [][] MapFieldArray;                 // Tablica reprezentująca planszę
    int [][] MapPawnsArray;                 // Tablica reprezentująca pionki
    int MapWidth;                           // Szerokość tablicy
    int MapHeight;                          // Wysokość tablicy
    int xOffset;                            // Przesunięcie planszy na ekranie w X
    int yOffset;                            // Przesunięcie planszy na ekranie w Y
    static int tileSize = 80;               // Rozmiar boku kafelka
    JFrame mainWindow;                      // Okno do rysowania
    Vector<Pawn>pawns;                      // Wektor przechowujący pionki

    DrawMapThread(int [][] MapField, int[][] MapPawns, int x,int xO, int yO, JFrame window, Vector<Pawn> Pawns)
    {
        MapFieldArray = MapField;       // Przypisanie
        MapPawnsArray = MapPawns;       //
        MapWidth = x;                   //
        MapHeight = x;                  //
        xOffset = xO;                   //
        yOffset = yO;                   //
        mainWindow = window;            //
        pawns = Pawns;                  //

    }

    @Override
    public void run() {

        for(int i = 0; i < MapWidth; i++)                                                               // Rysowanie pionów
        {
            for (int j = 0; j < MapHeight; j++)
            {
                if(MapPawnsArray[i][j] != 0 && MapPawnsArray[i][j] != -1) {                                  // Jeżeli w tablicy na danej pozycji nie ma 0 (nie ma piona) lub -1(poza planszą) to rysujemy piona
                    Pawn pawn = new Pawn((i*tileSize)+xOffset, (j*tileSize)+yOffset,mainWindow);      // Tworzymy obiekt piona
                    pawns.add(pawn);                                                                        // Dodajemy pionek do wektora
                }
            }

        }

        for(int i = 0; i < MapWidth; i++)                                                       // Rysowanie planszy
        {
            for (int j = 0; j < MapHeight; j++)
            {
                if(MapFieldArray[i][j] == 1) {                                                               // Jeżeli w tablicy na danej pozycji jest 1(pole planszy) to rysujemy
                    MapField mp = new MapField((i*tileSize)+xOffset, (j*tileSize)+yOffset,mainWindow); // Tworzymy obiekt pola planszy
                }
            }
        }

        mainWindow.repaint();                       // Przerysowujemy okno

    }
}
