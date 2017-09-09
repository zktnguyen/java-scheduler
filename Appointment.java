
import java.lang.Comparable;
/*
This class represents an appointment by its Time and ApptType.
Appointment implements Comparable in order to sort the ArrayList of Appointment objects in Scheduler class.
*/
public class Appointment implements Comparable<Appointment> {
  /*
  This enum indicates what type the appointment is.
  You can either have a HAIRCUT or HAIRCUT_PLUS_SHAMPOO as your appointment type.
  The parameters construct the enum type by assigning the duration of the appointment type.
  In this case, a HAIRCUT is 30 (minutes) long and a HAIRCUT_PLUS_SHAMPOO is 60 (minutes) long.
  */
  public static enum ApptType {
    HAIRCUT (30),
    HAIRCUT_PLUS_SHAMPOO (60);

    private final int duration;
    ApptType(int duration){
      this.duration = duration;
    }

    public int getDuration(){
      return this.duration;
    }

    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
  }

  /* Private final member variables
    They do not need to be changed after their first assignment in the constructor.
    The time object refers to the starting time of the appointment.
  */
  private final Time time;
  private final ApptType type;

  public Appointment(Time time, ApptType type){
    this.time = time;
    this.type = type;
  }

  /*
  We use Time.compareTo(Time time) in order to compare our appointment objects.
  In this case, appointment equivalence or differences depends on what time it begins.
  */
  @Override
  public int compareTo(Appointment appt1){
    return this.time.compareTo(appt1.time);
  }

  public Time getTime(){
    return this.time;
  }

  public ApptType getType(){
    return this.type;
  }

  // This is for printing purposes
  public String toString(){

    return String.format("%-20s", this.time) + String.format("%-20s", this.type);
  }
}
