package org.teste.memoria;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;

import org.teste.memoria.infos.InfoAvailableMemoryLinux;
import org.teste.memoria.infos.InfoTotalMemoryLinux;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class JFMonitorMemoria extends JFrame {

	private JLabel jlTotalMem = new JLabel();
	private JPProgressBars jpBars = new JPProgressBars();
	private JScrollPane scroll = new JScrollPane(jpBars);
	private JToggleButton tbPause = new JToggleButton("Pause");
	private JSpinner jsSleep = new JSpinner(new SpinnerNumberModel(1000, 1, 999999, 100));
	private JButton jbClear = new JButton("Limpar");
	
	public JFMonitorMemoria() {
		
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		tbPause.setFocusable(false);
		jbClear.setFocusable(false);
		jbClear.addActionListener(new ActionClear());
		
		setLayout(new MigLayout(new LC().noGrid()));
		add(jlTotalMem, new CC().wrap());
		add(scroll, new CC().width("400:100%:").height("200:100%:").wrap());
		add(jbClear, new CC());
		add(tbPause, new CC());
		add(new JLabel("Tempo (ms)"), new CC());
		add(jsSleep, new CC());
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setMinimumSize(new Dimension(getWidth()+15,getHeight()+50));
		setLocationRelativeTo(null);
		setTitle("Monitor de memória Linux");
	}
	
	private class ActionClear implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			jpBars.removeAll();
			validate();
			repaint();
		}
		
	}
	
	public void start(){
		new SwingWorker<Void, Integer>() {
			@Override
			protected Void doInBackground() throws Exception {
				jpBars.setMax(new InfoTotalMemoryLinux().loadInfo());
				jlTotalMem.setText("Memória Total: "+jpBars.totalMem);
				while (isVisible()) {
					Thread.sleep((Integer)jsSleep.getValue());
					if(tbPause.isSelected()){
						continue;
					}
					try {
						publish(new InfoAvailableMemoryLinux().loadInfo());
					} catch (Exception e) {
						System.err.println("Erro!");
					}
				}
				return null;
			}
			
			@Override
			protected void process(List<Integer> chunks) {
				for (Integer mem : chunks) {
					jpBars.add(mem);
				}
			}
			
			@Override
			protected void done() {
				try {
					get();
				} catch (Exception e) {
					jlTotalMem.setText("Erro: "+e.getMessage());
					e.printStackTrace();
				}
			}
		}.execute();
	}
	
	private class JPProgressBars extends JPanel{
	
		private int totalMem;
		private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		private DecimalFormat df = new DecimalFormat("###,##0.00");
		
		private JPProgressBars(){
			setLayout(new MigLayout(new LC().fillY()));
		}
		
		public void setMax(Integer maxMem) {
			this.totalMem = maxMem;
		}

		public void add(int memAvaliable){
			int memUsage = totalMem - memAvaliable;
			double p = (memUsage*100d)/totalMem;
			
			JProgressBar bar = new JProgressBar(JProgressBar.VERTICAL, 0, totalMem);
			if(memUsage>0){
				bar.setValue(memUsage);
			}
			bar.setToolTipText(""+memUsage);
			
			JLabel jlData = new JLabel();
			jlData.setText("<html><center>"+sdf.format(new Date())+"<br>"+df.format(p)+"%</center></html>");
			jlData.setFont(jlData.getFont().deriveFont(9f));
			
			
			JPanel jpValue = new JPanel(new BorderLayout());
			jpValue.add(jlData, BorderLayout.NORTH);
			jpValue.add(bar);
			jpValue.add(new JLabel(""+memUsage, JLabel.CENTER), BorderLayout.SOUTH);
			
			add(jpValue, new CC().growY(), 0);
			validate();
			repaint();
		}
	}
	
}
