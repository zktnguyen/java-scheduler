import java.lang.Comparable;
/*
This class represents time in the following format (Hour:minute AM or PM).
Time implements Comparable to use the compareTo() method to compare times (earlier, later or same time).
*/
public class Time implements Comparable<Time> {
  private int hour;
  private int minute;
  private String AMPM;

  public Time(int hour, int minute, String AMPM){
    this.hour = hour;
    this.minute = minute;
    this.AMPM = AMPM;
  }
  /* The time object uses a String to indicate whether the time is in AM or PM.
  */
  public static boolean checkAMPM(String AMPM){
    if (AMPM.equals("AM") || AMPM.equals("PM")){
      return true;
    }
    return false;
  }
  /* The time object uses hours from 1~12
  */
  public static boolean checkHour(int hour){
    if (hour >= 1 && hour <= 12){
      return true;
    }
    return false;
  }
  /* The time object uses minutes 0, 15, 30, or 45 in this project, specified in the instructions.
  */
  public static boolean checkMinute(int minute){
    if (minute == 0 || minute == 15 || minute == 30 || minute == 45){
      return true;
    }
    return false;
  }

  public int getHour(){
    return this.hour;
  }

  public int getMinute(){
    return this.minute;
  }

  public String getAMPM(){
    return this.AMPM;
  }

  /*
  This compareTo method returns 1 if the current object is at a later time than the compared object.
                        returns 0 if the current object is at the same time as the compared object.
                        returns -1 if the current object is at an earlier time than the compared object.
  */
  @Override
  public int compareTo(Time time){
    if (this.equals(time)){
      return 0;
    } // if they are equal.... then returns 0
    int thisHour = this.hour;
    int timeHour = time.getHour();

    if (thisHour == 12){
      thisHour = 0;
    }
    if (timeHour == 12){
      timeHour = 0;
    }

    if (this.AMPM.equals(time.getAMPM())){
      if (thisHour > timeHour){
        return 1; // current object is greater than parameter object
      }
      else if (thisHour < timeHour){
        return -1; // current object is less than parameter object
      }
      else {
        if (this.minute > time.getMinute()){
          return 1; // current object is greater than parameter object
        }
        else {
          return -1; // only other case is that it isn't equal and this.minute < time.getMinute()
          // current object less than parameter object
        }
      }
    }

    else if (this.AMPM.equals("PM")) {
      return 1;
    }

    else {
      return -1;
    }
  }

  /* This checks if the compared object is not null and is an instance of Time.
  If it is, then we compare the values of the hour, minute and if they are both AM or PM.
  If it essentially the same time (1:00 PM object is equal to 1:00 PM object), then it returns true.
  */
  @Override
  public boolean equals(Object object)
  {
      boolean isEqual= false;

      if (object != null && object instanceof Time){
          isEqual = (((Time)object).getHour() == this.hour) && (((Time)object).getMinute() == this.minute) && ((this.AMPM).equals(((Time)object).getAMPM()));
      }

      return isEqual;
  }

  @Override
  public int hashCode() {
      return this.minute + this.hour;
  }

  public String toString(){
    String minuteString = String.format("%02d", this.minute);
    String hourString = String.format("%2d", this.hour);
    return hourString + ":" + minuteString + " " + this.AMPM;
  }
}
