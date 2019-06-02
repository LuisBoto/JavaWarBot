import javax.swing.*;
/**
 * Allows user to select year on slider 
 */
@SuppressWarnings("serial")
class DateSlider extends JSlider{
	private int yearMax=2017;
	private int yearMin=1800;
		
	public DateSlider(int yearMin,int yearMax){
		super(JSlider.HORIZONTAL,yearMin,yearMax,yearMin);
		this.yearMin=yearMin;
		this.yearMax=yearMax;
		setMajorTickSpacing(100);
		setMinorTickSpacing(10);
		setPaintTicks(true);
		setPaintLabels(true);
		}

	public void setYearRange(int min,int max){
		yearMin=min;
		yearMax=max;
		setMinimum(yearMin);
		setMaximum(yearMax);
		if(getValue()<yearMin){
			setValue(yearMin);
		} else if(getValue()>yearMax){
			setValue(yearMax);
		}
	}
	public int getYearMin() {
		return yearMin;
	}
	
	public int getYearMax() {
		return yearMax;
	}
}
