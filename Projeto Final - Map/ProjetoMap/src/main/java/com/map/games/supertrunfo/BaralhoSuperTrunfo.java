package com.map.games.supertrunfo;

import com.map.framework.card.Baralho;
import com.map.framework.card.Carta;

import java.util.ArrayList;
import java.util.List;

/**
 * Baralho específico para o jogo Super Trunfo de carros.
 * 
 * <p><strong>Padrão GoF: Factory Method (ConcreteCreator)</strong></p>
 * <p>Implementa o Factory Method {@link #criarCartas()} para produzir
 * um conjunto de 32 cartas de carros variados, incluindo 1 Super Trunfo.</p>
 */
public class BaralhoSuperTrunfo extends Baralho {

    public BaralhoSuperTrunfo() {
        super();
        inicializar();
    }

    @Override
    protected List<Carta> criarCartas() {
        List<Carta> cartas = new ArrayList<>();

        Object[][] dados = {
                {"Ferrari", "SF90 Stradale", 1000.0, 340.0, 2.5, 5500.0, 4000.0, false},
                {"Lamborghini", "Aventador SVJ", 770.0, 350.0, 2.8, 4800.0, 6500.0, false},
                {"Porsche", "911 Turbo S", 650.0, 330.0, 2.7, 2200.0, 3800.0, false},
                {"Bugatti", "Chiron Super Sport", 1600.0, 490.0, 2.4, 20000.0, 8000.0, true}, // SUPER TRUNFO
                {"McLaren", "765LT", 765.0, 330.0, 2.8, 3000.0, 4000.0, false},
                {"Aston Martin", "Valkyrie", 1160.0, 350.0, 2.5, 45000.0, 6500.0, false},
                {"Koenigsegg", "Jesko", 1600.0, 532.0, 2.5, 35000.0, 5000.0, false},
                {"Pagani", "Huayra BC", 791.0, 370.0, 2.8, 42000.0, 6000.0, false},
                {"BMW", "M5 Competition", 625.0, 305.0, 3.3, 1400.0, 4400.0, false},
                {"Mercedes", "AMG GT 63 S", 639.0, 315.0, 3.2, 1600.0, 4000.0, false},
                {"Audi", "RS e-tron GT", 646.0, 250.0, 3.3, 1500.0, 0.0, false},
                {"Tesla", "Model S Plaid", 1020.0, 322.0, 2.1, 1200.0, 0.0, false},
                {"Chevrolet", "Corvette Z06", 670.0, 315.0, 2.9, 1100.0, 5500.0, false},
                {"Ford", "Mustang Shelby GT500", 760.0, 290.0, 3.5, 850.0, 5200.0, false},
                {"Dodge", "Challenger SRT Demon", 840.0, 330.0, 2.3, 900.0, 6200.0, false},
                {"Nissan", "GT-R Nismo", 600.0, 315.0, 2.8, 950.0, 3800.0, false},
                {"Honda", "NSX Type S", 600.0, 308.0, 3.0, 1100.0, 3500.0, false},
                {"Toyota", "GR Supra", 387.0, 250.0, 4.1, 600.0, 3000.0, false},
                {"Mazda", "MX-5 Miata", 184.0, 220.0, 6.5, 300.0, 2000.0, false},
                {"Subaru", "WRX STI", 310.0, 255.0, 5.2, 450.0, 2500.0, false},
                {"Volkswagen", "Golf GTI", 245.0, 250.0, 6.0, 350.0, 2000.0, false},
                {"Hyundai", "i30 N", 280.0, 250.0, 5.6, 320.0, 2000.0, false},
                {"Fiat", "500 Abarth", 180.0, 211.0, 6.8, 220.0, 1400.0, false},
                {"Renault", "Clio RS", 220.0, 235.0, 6.5, 280.0, 1600.0, false},
                {"Peugeot", "308 GTi", 263.0, 250.0, 6.0, 300.0, 1600.0, false},
                {"Jeep", "Wrangler Rubicon", 285.0, 180.0, 7.6, 550.0, 3600.0, false},
                {"Land Rover", "Defender V8", 525.0, 191.0, 5.2, 1100.0, 5000.0, false},
                {"RAM", "1500 TRX", 702.0, 190.0, 4.5, 950.0, 6200.0, false},
                {"Porsche", "Taycan Turbo S", 761.0, 260.0, 2.8, 1800.0, 0.0, false},
                {"Rimac", "Nevera", 1914.0, 412.0, 1.85, 50000.0, 0.0, false},
                {"Lotus", "Evija", 2000.0, 350.0, 3.0, 30000.0, 0.0, false},
                {"Maserati", "MC20", 630.0, 325.0, 2.9, 3500.0, 3000.0, false}
        };

        for (Object[] d : dados) {
            cartas.add(new CartaSuperTrunfo(
                    (String) d[1], (String) d[0],
                    (Double) d[2], (Double) d[3], (Double) d[4],
                    (Double) d[5], (Double) d[6], (Boolean) d[7]
            ));
        }

        return cartas;
    }
}
