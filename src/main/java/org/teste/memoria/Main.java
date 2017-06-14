package org.teste.memoria;

import javax.swing.UIManager;

public class Main {
    
	public static void main( String[] args ) throws Exception {
    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	JFMonitorMemoria frame = new JFMonitorMemoria();
    	frame.setVisible(true);
    	frame.start();
	}
	
}
