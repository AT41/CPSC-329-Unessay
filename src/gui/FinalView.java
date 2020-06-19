package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigInteger;
import java.util.Hashtable;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FinalView extends JFrame {
	
	class SliderListener implements ChangeListener {
		private JLabel sliderVal;
		
		public SliderListener(JLabel sliderVal) {
			this.sliderVal = sliderVal;
		}
		
		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			this.sliderVal.setText("Guesses per second: " + Integer.toString(source.getValue()));
		}
	}
	
	public FinalView(List<BigInteger> allValues, List<String> allAttacks) {
		this.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {  
                dispose();  
            }
        });
		
		initialize();
	}
	
	private void initialize() {
		this.setSize(400, 200);
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		this.setTitle("Final Results");
		
		JLabel topMsg = new JLabel("It took a minimum of X " + " guesses to reach your password.");
		topMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
		topMsg.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
		this.add(topMsg);
		
		this.add(makeSlider());
	}
	
	private Component makeSlider() {
		JPanel sliderContainer = new JPanel();
		sliderContainer.setLayout(new BoxLayout(sliderContainer, BoxLayout.PAGE_AXIS));

		JSlider slider = new JSlider(0, 100);
		JLabel sliderVal = new JLabel("Guesses per second: " + slider.getValue());
		slider.addChangeListener(new SliderListener(sliderVal));
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        labels.put(0, new JLabel("1E1"));
        labels.put(20, new JLabel("1E2"));
        labels.put(40, new JLabel("1E3"));
        labels.put(60, new JLabel("1E4"));
        slider.setLabelTable(labels);
        slider.setPaintLabels(true);
		
		sliderContainer.add(sliderVal);
		sliderContainer.add(slider);
		
		JLabel totalTime = new JLabel("Your password would be safe for X years");
		JLabel hardwareSpecDescription = new JLabel("An attacker who can guess X number of times per second would require Y hardware.");
		sliderContainer.add(totalTime);
		sliderContainer.add(hardwareSpecDescription);
		return sliderContainer;
	}
}
