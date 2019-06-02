package nicholasJohnstone.MappingApp;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import javax.swing.event.*;
import java.util.*;
/**
 * Panel consisting of a date label and slider. Handles updating the date when the slider is moved and updating the year
 * range when triggered.
 */
@SuppressWarnings("serial")
class DatePanel extends JPanel implements ChangeListener{
	private static final int YEAR_MIN_DEFAULT=1800;
	private static final int YEAR_MAX_DEFAULT=2017;
	
	private static ArrayList<YearListener> yearListenerList = new ArrayList<YearListener>();	
	
	private int currentYear;
	private DateSlider dateSlider;
	private DateLabel label;
	private DateRangeEditor rangeEditor=new DateRangeEditor(this);

	public static void addYearListener(YearListener x){
		yearListenerList.add(x);
	}
	
	public DatePanel() {
	dateSlider=new DateSlider(YEAR_MIN_DEFAULT,YEAR_MAX_DEFAULT);
	currentYear=dateSlider.getValue();
	yearChanged();
	label = new DateLabel(currentYear);
	setLayout(new BorderLayout());
	add(dateSlider,BorderLayout.CENTER);
	add(label,BorderLayout.EAST);
	dateSlider.addChangeListener(this);
	setBorder(new EmptyBorder(20, 20, 20, 20));
	}

	public void editYearRange() {
		rangeEditor.getUserInput();
	}
	
	public void setYearRange(int min,int max) {
		dateSlider.setYearRange(min,max);
		yearChanged();
		
	}
	
	public int getYearMin() {
			return dateSlider.getYearMin();
	}
	
	public int getYearMax() {
		return dateSlider.getYearMax();
	}
	
	public void stateChanged(ChangeEvent e){
		currentYear=dateSlider.getValue();
		label.setYear(currentYear);
		yearChanged();
	}
		
	public void yearChanged(){
		Plot.setCurrentYear(currentYear);
		for(YearListener x: yearListenerList) {
			x.yearChanged();
		}
	}
}
