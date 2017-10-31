
/**
 * @author Michael Kolling and David J. Barnes -> MOD: Ivan
 * @version 2008.03.30 -> 0.1
 */
public class ClockDisplay
{
    private NumberDisplay hours;
    private NumberDisplay minutes;
    //private boolean isAM;
    private String displayString;    // simulates the actual display
    
    /**
     * Constructor for ClockDisplay objects. This constructor 
     * creates a new clock set at 00:00.
     */
    public ClockDisplay()
    {
        hours = new NumberDisplay(13);
        minutes = new NumberDisplay(60);
        isAM = true;
        updateDisplay();
    }

    /**
     * Constructor for ClockDisplay objects. This constructor
     * creates a new clock set at the time specified by the 
     * parameters.
     */
    public ClockDisplay(int hour, int minute)//, boolean isAM)
    {
        
        minutes = new NumberDisplay(60);
        if (isAM){    
            hours = new NumberDisplay(13);
        } else {
             hours = new NumberDisplay(12);
        }
        setTime(hour, minute);
    }

    /**
     * This method should get called once every minute - it makes
     * the clock display go one minute forward.
     */
    public void timeTick()
    {
        minutes.increment();
        if(minutes.getValue() == 0) {  // it just rolled over!
            hours.increment();
       //     if(hours.getValue() > 12){
       //         isAM = false;
       // }
        updateDisplay();
    }

    /**
     * Set the time of the display to the specified hour and
     * minute.
     */
    public void setTime(int hour, int minute)
    {
        hours.setValue(hour);
        minutes.setValue(minute);
        updateDisplay();
    }

    /**
     * Return the current time of this display in the format HH:MM.
     */
    public String getTime()
    {
        return displayString;
    }
    
    /**
     * Update the internal string that represents the display.
     */
    private void updateDisplay()
    {
        displayString = hours.getDisplayValue() + ":" + 
                        minutes.getDisplayValue();
    }
}
