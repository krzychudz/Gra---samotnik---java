package com.company;

import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel
{

    StatsPanel()
    {
        setLayout(new FlowLayout(FlowLayout.LEFT));         // Ustawiamy flowlayout
        setBounds(840,100,500,300);     // Ustawiamy pozycje
        setOpaque(false);                                   // Przezroczystość
        setVisible(true);                                   // Widoczność


    }


}
