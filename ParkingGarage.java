//Jacob Gaylord
//jigaylord16@ole.augie.edu
//ParkingGarage.java
import java.util.Scanner;
import java.util.StringTokenizer;
import java.text.DecimalFormat;
/**
A data structure that stores integer values for hour (0..23) and minute (0..59) 
to represent the time of day in a 24-hour clock 
*/
class Time24
{
    private int hour;
    private int minute;
    //Post: Sets the hour value in the range 0 to 23 and the minute value in the range 0 to 59
    private void normalizeTime()
    {
        int extraHours = minute / 60;
        minute %= 60;
        hour = (hour + extraHours) % 24;
    }
    /**
    Initializes this Time24 object<p>
    <b>Post:</b><br>hour and minute of this Time24 object both initialized to 0<p>
    */
    public Time24()
    {
        this(0,0);		
    }
    /**
    Initializes this Time24 object<p>
    <b>Pre:</b><br>h and m cannot be negative<p>
    <b>Post:</b><br>hour and minute of this Time24 object initialized to h and m 
    respectively.  This operation will normalize the time if necessary (e.g. 
    9:75 is stored as 10:15).<p>
    <b>Throw:</b><br>IllegalArgumentException if h or m is negative<p>
    */
    public Time24(int h, int m)
    {
        setTime(h, m);
    }
    /**
    Sets the hour and minute of this Time24 object to a particular time<p>
    <b>Pre:</b><br>h and m cannot be negative<p>
    <b>Post:</b><br>hour and minute of this Time24 object set to h and m 
    respectively.  This operation will normalize the time if necessary (e.g. 
    9:75 is stored as 10:15).<p>
    <b>Throw:</b><br>IllegalArgumentException if h or m is negative<p>
    */
    public void setTime(int h, int m)
    {
        if (h < 0 || m < 0)
        throw new IllegalArgumentException("Time24.setTime: argument"
                                            + " must not be negative");
        this.hour = h;
        this.minute = m;
        normalizeTime();
    }
    /**
    Adds minutes to this Time24 object <p>
    <b>Pre:</b><br>m cannot be negative<p>
    <b>Post:</b><br>This Time24 object set to m minutes later.  This operation will 
    normalize the time if necessary (e.g. 9:75 is stored as 10:15).<p>
    <b>Throw:</b><br>IllegalArgumentException if m is negative<p>
    */
    public void addTime(int m)
    {
        if (m < 0)
        throw new IllegalArgumentException("Time24.addTime: argument"
                                            + " must not be negative");
        this.minute += m;
        normalizeTime();
    }
    /**
    Measures the interval from this Time24 object to another time<p>
    <b>Return:</b><br>The interval from this Time24 object to t as a Time24 
    */
    public Time24 interval(Time24 t)
    {
        int currTime=hour*60+minute;
        int tTime=t.hour*60+t.minute;
        if(tTime<currTime)
            tTime+=24*60;
        return new Time24(0,tTime-currTime);
    }
    /**
    Gets the hour value of this Time24 object<p>
    <b>Return:</b><br>The hour value of this Time24 object<p>
    */
    public int getHour()
    { 
        return hour; 
    }
    /**
    Gets the minute value of this Time24 object<p>
    <b>Return:</b><br>The minute value of this Time24 object<p>
    */
    public int getMinute()
    { 
        return minute; 
    }
    /**
    Converts this Time24 object to a string<p>
    <b>Return:</b><br>This Time24 object as a String in the form "hh:mm"<p>
    */
    public String toString()
    {
        DecimalFormat f= new DecimalFormat("#0");
        DecimalFormat f2= new DecimalFormat("00");
	return f.format(hour)+":"+f2.format(minute);
    }
    /**
    Convert a String to a Time24<p>
    <b>Pre:</b><br>s must be in the form "hh:mm" where hh and mm are positive integers 
    <p>
    <b>Return:</b><br>A Time24 object that corresponds to s<p>
    */
    public static Time24 parseTime(String s)				
    {
        StringTokenizer stk = new StringTokenizer(s,":");
        int hr = Integer.parseInt(stk.nextToken());
        int mn = Integer.parseInt(stk.nextToken());
        Time24 t= new Time24(hr, mn);
        return t;
    }
    //Usage: void readTime(Scanner f)
    //Desc:  Read from f the hour and minute for this Time24 object
    //Pre:   f is ready to be read, and the next line contains hh:mm where hh:mm
    //       gives a valid 24 hour time.
    //Post:  The entire line hh:mm read in from f.
    //       The hour and minute of this Time24 object set to hh and mm respectively.  
    //       The operation will normalize the time if necessary (e.g. 9:75 is 
    //       stored as 10:15).

    public void readTime(Scanner f)
    {
        String s = f.nextLine();
        Time24 t = new Time24();
        t = t.parseTime(s);
        this.setTime(t.getHour(), t.getMinute());
        this.normalizeTime();
    }
}
public class ParkingGarage
{
    //Desc:  	The program computes the cost of parking a car in a public 
    //          garage at the rate $5.00/hour.  The client will always be 
    //          charged for whole hours.  For example, if a car parked for 2 
    //          hours and 1 minute, the client will be charged for 3 hours.
    //Input: 	User inputs the entry time and exit time in 24-hr clock format 
    //          (hh:mm) 
    //Output:	The enter and exit times, the length of time the car is parked 
    //          and the total charges.
    public static void main(String[] args)
    {
        Scanner keyboard = new Scanner(System.in);
        Time24 entryTime = new Time24(0,0);
        Time24 exitTime = new Time24(0,0);
        Time24 parkingTime = new Time24(0,0);
        double charge = 0.0;
        System.out.print("Time enters the garage in 24-hr clock format <hh:mm> ");
        entryTime.readTime(keyboard);
        System.out.print("Time exits the garage in 24-hr clock format <hh:mm> ");
        exitTime.readTime(keyboard);
        System.out.println("\t Car enters at: "+entryTime.toString());
        System.out.println("\t Car exits at: "+exitTime.toString());
        parkingTime = entryTime.interval(exitTime);
        System.out.println("\t Parking time: "+parkingTime.toString());
        charge = (parkingTime.getMinute()>0) ? (parkingTime.getHour()+1)*5
                : parkingTime.getHour()*5;
        System.out.println("\t Charges: $"+charge+"0");
    }
}