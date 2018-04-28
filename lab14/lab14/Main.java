package lab14;

import lab14lib.Generator;
import lab14lib.GeneratorAudioVisualizer;
import java.util.ArrayList;

import lab14lib.GeneratorPlayer;
import lab14lib.MultiGenerator;
import lab14lib.GeneratorDrawer;

public class Main {
	public static void main(String[] args) {
        Generator g1 = new SawToothGenerator(512);
        GeneratorDrawer player = new GeneratorDrawer(g1);

        player.draw(4096);


	}
} 