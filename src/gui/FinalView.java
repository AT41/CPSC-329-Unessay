package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
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
		public HardwareCapability(BigInteger guesses, String specs) {
			this.maxGuessesPerSecond = guesses;
			this.hardwareSpecs = specs;
		}
		public BigInteger maxGuessesPerSecond;
		public String hardwareSpecs;
	}
	class SliderListener implements ChangeListener {
		private JLabel sliderVal;
		private JLabel totaltime;
		private FinalView origin;
		private BigInteger totalGuesses;
		
		public SliderListener(JLabel sliderVal, JLabel totalTime, FinalView origin, BigInteger totalGuesses) {
			this.sliderVal = sliderVal;
			this.origin = origin;
			this.totaltime = totalTime;
			this.totalGuesses = totalGuesses;
		}
		
		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			this.updateLabels(source.getValue());
		}
		
		public void updateLabels(int valueOfSlider) {
		    NumberFormat formatter = new DecimalFormat("0.##E0", DecimalFormatSymbols.getInstance(Locale.ROOT));
			this.sliderVal.setText("MD5 Hashes per second: " + formatter.format(origin.getValueCorrespondingTo(valueOfSlider)));
			this.totaltime.setText("This would roughly take " + convert(this.totalGuesses.divide(origin.getValueCorrespondingTo(valueOfSlider))));
		}
		
		private String convert(BigInteger totalSeconds) {
	        String result = "";
	        BigInteger hours = totalSeconds.divide(BigInteger.valueOf(3600));
	        BigInteger days = hours.divide(BigInteger.valueOf(24));
	        BigInteger years = days.divide(BigInteger.valueOf(365));
	        
	        if(years.equals(BigInteger.ZERO)) {
	            if(days.equals(BigInteger.ZERO)) {
	                result = "Hours: " + hours.toString() + ", Seconds: " + totalSeconds.toString();
	            }
	            else {
	                result = "Days: " + days.toString() + ", Hours: " + hours.toString();
	            }
	        }
	        else {
	            result = "Years: " + years.toString() + ", Days: " + days.toString() + "\n";
	        }
	        
	        return result;
	    }
	}
	
	private HardwareCapability[] CAPABILITIES = {
			new HardwareCapability(new BigInteger("207145879"), "NVIDIA GeForce GTX 470, released March 2010, priced around $85 today. GIPS = (448 Cores / 8) * (1.214GHz base clock * 2) = 136"),
			new HardwareCapability(new BigInteger("666094174"), "MSI Geforce GTX 970 Gaming Lite Edition, released Sept 2014, priced around $300 today. GIPS = (1664 Cores / 8) * (2.102GHz shader clock) = 437"),
			new HardwareCapability(new BigInteger("2236483221"), "NVIDIA GeForce RTX 2080 Ti, released Sept 2018, priced around $1200 today. GIPS = (4352 Cores / 8) * (1.350GHz base clock * 2) = 1468"),
			new HardwareCapability(new BigInteger("2887074362"), "NVIDIA GeForce RTX 3090, releases in 2020, will be sold at around $2k each, GIPS = (5376 Cores / 8) * (1.410GHz base clock * 2) = 1895"),
			new HardwareCapability(new BigInteger("144353718100"), "50 NVIDIA GeForce RTX 3090 working simultaneously, $100k in total")
	};
	private Hashtable<String, BigInteger> allGuesses;
	private BigInteger minimumTotalGuesses;
	private String fastestAttackName;
	private JLabel totalTime;
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
	
	private BigInteger getValueCorrespondingTo(double val) {
		double index = (val / 100 * (this.CAPABILITIES.length - 1));
		
		BigInteger returnVal = this.CAPABILITIES[(int)index].maxGuessesPerSecond;
		if ((int)index != this.CAPABILITIES.length - 1) {
			BigDecimal dec = new BigDecimal(this.CAPABILITIES[(int)index + 1].maxGuessesPerSecond.subtract(this.CAPABILITIES[(int)index].maxGuessesPerSecond));
			dec = dec.multiply(new BigDecimal((index%1)));
			returnVal = returnVal.add(dec.toBigInteger());
		}
		
		return returnVal;
	}
	
	private void initialize() {
		this.setSize(500, 250);
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
	    NumberFormat formatter = new DecimalFormat("0.##E0", DecimalFormatSymbols.getInstance(Locale.ROOT));
		JLabel sliderVal = new JLabel(/*"MD5 Hashes per second: " + formatter.format(this.getValueCorrespondingTo(slider.getValue()))*/);
		tempHolder1.add(sliderVal);
		this.totalTime = new JLabel();
		SliderListener sliderListener = new SliderListener(sliderVal, this.totalTime, this, this.minimumTotalGuesses);
		slider.addChangeListener(sliderListener);
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        for (int i = 0; i < this.CAPABILITIES.length - 1; i++) {
	        labels.put((int)((double)(i) / (this.CAPABILITIES.length - 1) * 100), new JLabel(formatter.format(this.CAPABILITIES[i].maxGuessesPerSecond).replaceAll("E", "e")));
        }
        labels.put(100, new JLabel(formatter.format(this.CAPABILITIES[this.CAPABILITIES.length - 1].maxGuessesPerSecond).replaceAll("E", "e")));
        
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
		JTextArea hardwareSpecDescription = new JTextArea();
		hardwareSpecDescription.setEditable(false);
		hardwareSpecDescription.setLineWrap(true);
		hardwareSpecDescription.setWrapStyleWord(true);
		hardwareSpecDescription.setOpaque(false);
		hardwareSpecDescription.setBorder(BorderFactory.createEmptyBorder(4,8,4,8));
		sliderContainer.add(tempHolder2);
		sliderContainer.add(hardwareSpecDescription);
		
		JTextArea hardwareSpecs = new JTextArea("");
		hardwareSpecs.setEditable(false);
		hardwareSpecs.setLineWrap(true);
		hardwareSpecs.setWrapStyleWord(true);
		hardwareSpecs.setOpaque(false);
		hardwareSpecs.setSize(367, 200);
		
		JPanel temp3 = new JPanel();
		temp3.setVisible(true);
		temp3.add(this.totalTime);
		sliderContainer.add(temp3);
		
		sliderListener.updateLabels(50);
		
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
