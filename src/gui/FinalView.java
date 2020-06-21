package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import types.RainbowTableAttack;

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
	private Hashtable<String, BigInteger> allGuesses;
	private BigInteger minimumTotalGuesses;
	private String fastestAttackName;
	private final int attacksThatDontCount = 1; // Really bad coding practice but I'm short on time... Only attack that fails instantly is rainbow table
	
	public FinalView(Hashtable<String, BigInteger> allGuesses) {
		this.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {  
                dispose();  
            }
        });
		
		this.allGuesses = allGuesses;
		this.calculateGuesses();
		
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

	    NumberFormat formatter = new DecimalFormat("0.######E0", DecimalFormatSymbols.getInstance(Locale.ROOT));
		JLabel topMsg = new JLabel("It took a minimum of " + formatter.format(this.minimumTotalGuesses).replaceAll("E", "e") + " guesses to reach your password.");
		topMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
		topMsg.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
		this.add(topMsg);
		
		this.add(makeSlider());
	}
	
	private void calculateGuesses() {
		Object[] attacks = this.allGuesses.keySet().toArray();
		BigInteger minimum = this.allGuesses.get(attacks[0]), negOne = BigInteger.ONE.negate();
		String attackName = (String)attacks[0];
		for (int i = 1; i < attacks.length; i++) {
			minimum = minimum.equals(negOne) ? 
					this.allGuesses.get(attacks[i]) : 
					this.allGuesses.get(attacks[i]).equals(negOne) ? 
							minimum : 
							minimum.min(this.allGuesses.get(attacks[i]));
			if (minimum.equals(this.allGuesses.get(attacks[i])) ) {
				attackName = (String)attacks[i];
			}
		}
		this.minimumTotalGuesses = minimum;
		this.fastestAttackName = attackName;
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
	        labels.put((int)((double)(i) / (this.CAPABILITIES.length - 1) * 100), new JLabel(Integer.toString(this.CAPABILITIES[i].maxGuessesPerSecond)));
        }
        labels.put(100, new JLabel(Integer.toString(this.CAPABILITIES[this.CAPABILITIES.length - 1].maxGuessesPerSecond)));
        
        slider.setLabelTable(labels);
        slider.setPaintLabels(true);
		
		sliderContainer.add(tempHolder1);
		sliderContainer.add(slider);
		
		JPanel tempHolder2 = new JPanel();
		tempHolder2.setVisible(true);
		JTextArea totalGuesses = new JTextArea(setAttackTimeText());
		totalGuesses.setEditable(false);
		totalGuesses.setLineWrap(true);
		totalGuesses.setWrapStyleWord(true);
		totalGuesses.setOpaque(false);
		totalGuesses.setSize(367, 200);
		tempHolder2.add(totalGuesses);
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
	
	private String setAttackTimeText() {
		if (this.fastestAttackName == RainbowTableAttack.hashName) {
			return "Your password was thwarted instantly by the Rainbow Table.";
		} else {
		    NumberFormat formatter = new DecimalFormat("0.####E0", DecimalFormatSymbols.getInstance(Locale.ROOT));
			return "If all attacks ran simultaneously, it would have taken " + 
		    		formatter.format(this.minimumTotalGuesses.multiply(new BigInteger(Integer.toString((this.allGuesses.size() - this.attacksThatDontCount))))).replaceAll("E", "e")
			+ " guesses to beat, and was eventually beaten by this algorithm: " + this.fastestAttackName;
		}
	}
}
