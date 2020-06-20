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
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FinalView extends JFrame {
	class HardwareCapability {
		public HardwareCapability(int guesses, String specs) {
			this.maxGuessesPerSecond = guesses;
			this.hardwareSpecs = specs;
		}
		public int maxGuessesPerSecond;
		public String hardwareSpecs;
	}
	class SliderListener implements ChangeListener {
		private JLabel sliderVal;
		private FinalView origin;
		
		public SliderListener(JLabel sliderVal, FinalView origin) {
			this.sliderVal = sliderVal;
			this.origin = origin;
		}
		
		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			this.sliderVal.setText("Guesses per second: " + origin.getValueCorrespondingTo(source.getValue()));
		}
	}
	
	private HardwareCapability[] CAPABILITIES = {
		new HardwareCapability(1, "test"),
		new HardwareCapability(10, "test2"),
		new HardwareCapability(5027, "Nvidia TITAN X (3584 cores, 1.417GHz core clock)")
	};
	
	public FinalView(List<BigInteger> allValues, List<String> allAttacks) {
		this.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {  
                dispose();  
            }
        });
		
		initialize();
	}
	
	private int getValueCorrespondingTo(double val) {
		double index = (val / 100 * (this.CAPABILITIES.length - 1));
		
		int returnVal = this.CAPABILITIES[(int)index].maxGuessesPerSecond;
		if ((int)index != this.CAPABILITIES.length - 1) {
			returnVal += (int)((double)this.CAPABILITIES[(int)index + 1].maxGuessesPerSecond * (index%1));
		}
		
		return returnVal;
	}
	
	private void initialize() {
		this.setSize(400, 250);
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		this.setTitle("Final Results");
	    this.setResizable(false);
		
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
		JPanel tempHolder1 = new JPanel();
		tempHolder1.setVisible(true);
		JLabel sliderVal = new JLabel("Guesses per second: " + this.getValueCorrespondingTo(slider.getValue()));
		tempHolder1.add(sliderVal);
		slider.addChangeListener(new SliderListener(sliderVal, this));
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        for (int i = 0; i < this.CAPABILITIES.length - 1; i++) {
	        labels.put((int)((double)(/*i == this.CAPABILITIES.length - 1 ? i + 1 : */i) / (this.CAPABILITIES.length - 1) * 100), new JLabel(Integer.toString(this.CAPABILITIES[i].maxGuessesPerSecond)));
        }
        labels.put(100, new JLabel(Integer.toString(this.CAPABILITIES[this.CAPABILITIES.length - 1].maxGuessesPerSecond)));
        
        slider.setLabelTable(labels);
        slider.setPaintLabels(true);
		
		sliderContainer.add(tempHolder1);
		sliderContainer.add(slider);
		
		JPanel tempHolder2 = new JPanel();
		tempHolder2.setVisible(true);
		JLabel totalTime = new JLabel("Your password would be safe for X years");
		totalTime.setAlignmentX(Component.LEFT_ALIGNMENT);
		tempHolder2.add(totalTime);
		JTextArea hardwareSpecDescription = new JTextArea("An attacker who can guess X number of times per second would require Y hardware.");
		hardwareSpecDescription.setEditable(false);
		hardwareSpecDescription.setLineWrap(true);
		hardwareSpecDescription.setWrapStyleWord(true);
		hardwareSpecDescription.setOpaque(false);
		hardwareSpecDescription.setBorder(BorderFactory.createEmptyBorder(4,8,4,8));
		sliderContainer.add(tempHolder2);
		sliderContainer.add(hardwareSpecDescription);
		return sliderContainer;
	}
}
