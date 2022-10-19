package com.algaworks.algafood.common;

public class ConsoleColors {

	// Regular Colors
	public static final String GREEN = "\033[0;32m";
	public static final String PURPLE = "\033[0;35m";
	public static final String CYAN = "\033[0;36m";

	// Reset
	public static final String RESET = "\033[0m";

	public static void printCyan(String msg) {
		System.out.println(CYAN + msg + RESET);
	}
}

//ANSI CODES (COLOR AND OTHERS)
//https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
//https://www.ti-enxame.com/pt/java/como-imprimir-cores-no-console-usando-system.out.println/972973875/