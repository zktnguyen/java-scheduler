import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.io.*;

/*
This class represents a scheduling program.
This "Scheduler" maintains the appointments and unavailable time slots for the hairdresser.
You can only schedule appointments after the local time of running the program (i.e. java Scheduler command).
*/
public class Scheduler {
  /*
  ArrayLists are chosen because there is no definite amount of appointments we expect to have.
  ArrayLists are also chosen because the amount of appointments and timeslots possible are not large.
  */
  static ArrayList<Appointment> appts = new ArrayList<Appointment>();
  static ArrayList<Time> unavailable = new ArrayList<Time>();
  static Time startingTime;

  /*
  This method obtains the time and type of appointment to make an Appointment object.
  This valid Appointment object is added to a sorted (by earliest time) ArrayList of Appointment objects.
  This method then marks all starting times for possible appointments as unavailable depending on the duration of the appointment.
  */
  public static void addAppointment() {

    Time time = parseTime();
    while (!checkAvailability(time)){
      System.out.println("This timeslot is unavailable!");
      time = parseTime();
    }
    Appointment.ApptType type = parseType();
    Appointment appt = new Appointment(time, type);
    appts.add(appt);
    Collections.sort(appts);
    markUnavailable(time, type.getDuration());
    System.out.println("Adding appointment!");
  }
  /*
  This method receives the input from user for the time desired for the appointment.
  This utilizes Time methods to ensure valid inputs, and will continue to prompt user for input until they are valid.
  */
  public static Time parseTime(){
    Scanner sc = new Scanner(System.in);
    // assign arbitrary values that aren't valid
    int hour = 0;
    int minute = 2;
    String AMPM = "";
    while (!Time.checkHour(hour) || !Time.checkMinute(minute) || !Time.checkAMPM(AMPM)){
      System.out.print("Please enter the desired time (Format: HH MM AM/PM): ");
      hour = sc.nextInt();
      minute = sc.nextInt();
      AMPM = sc.next();
    }
    //debug statement
    Time time = new Time(hour, minute, AMPM);
    return time;
  }

  /*
  This method receives input from the user to choose which type of appointment is desired.
  The method will continue to prompt the user until the user chooses a valid input.
  */
  public static Appointment.ApptType parseType(){
    Scanner sc = new Scanner(System.in);
    int choice = 0;
    Appointment.ApptType type = Appointment.ApptType.HAIRCUT;
    System.out.println("1. Haircut (30 minutes)\n2. Haircut and Shampoo(60 minutes)");
    System.out.print("Enter which appointment you want to make: ");
    choice = sc.nextInt();
    while(choice != 1 && choice != 2){
      System.out.print("Invalid choice! Enter which appointment you want to make(1 or 2): ");
      choice = sc.nextInt();
    }
    if (choice == 2){
      type = Appointment.ApptType.HAIRCUT_PLUS_SHAMPOO;
    }
    return type;
  }
  /*
  This method adds Time objects to the unavailable ArrayList.
  This method is necessary to maintain conflicts among appointments.
  No appointment is allowed to be scheduled during an already scheduled appointment.
  Example: Haircut appointment at 4:00 PM. 4:00 PM and 4:15 PM become unavailable starting time slots.
          The next time slot you can make an appointment is before 4:00PM or anytime starting from 4:30PM.
  */
  public static void markUnavailable(Time time, int duration){
    // given a time, mark that unavailable and its succeeding timeslots
    // given duration, as well.
    int hour = time.getHour();
    int minute = time.getMinute();
    String AMPM = time.getAMPM();
    int timeSlots = duration / 15;
    unavailable.add(time);
    for (int i = 1; i < timeSlots; i++){
      minute += 15;
      if (minute == 60){
        if (hour == 12){
          hour = 1;
        }
        else if (hour == 11){
          hour++;
          if (AMPM == "AM") AMPM = "PM";
          else AMPM = "AM";
        }
        else hour++;
        minute = 0;
      }
      Time newTime = new Time(hour, minute, AMPM);
      unavailable.add(newTime);
    }
  }

  /*
  This method checks the availability of the starting time desired.
  It checks if 1. the time is after the program started running and 2. if it is in the unavailable ArrayList.
  */
  public static boolean checkAvailability(Time time){
    if (time.compareTo(startingTime) <= 0 || unavailable.contains(time)){
      return false;
    }
    return true;
  }
  /*
  This method lists the possible operations you can perform with this program.
  1. Adding an appointment, 2. Listing the appointment(s) and 3. Exiting the program.
  */
  public static int getChoice() {
    int choice = -1;
    while (choice != 1 && choice != 2 && choice != 3){
      System.out.println("1. Add appointment\n2. List appointment(s)\n3. Exit");
      System.out.print("Enter one of the choices to proceed: ");
      Scanner getNum = new Scanner(System.in);
      choice = getNum.nextInt();
    }

    return choice;
  }
  /*
  This method calls one of the methods depending on the choice given by getChoice().
  This method returns a boolean that keeps the program running if it is true, or stops the program if returns false.
  */
  public static boolean useChoice(int choice){
    boolean running = true;
    switch(choice){
      case 1:
        addAppointment();
        break;
      case 2: list();
        break;
      case 3: running = exit();
        break;
    }
    return running;
  }

  /*
  This method simply returns false if called to end the program loop, essentially "exiting.
  */
  public static boolean exit(){
    return false;
  }

  /*
  This method iterates through the sorted appts ArrayList and prints them out in a formatted fashion.
  */
  public static void list(){
    // list all the appointments, update toString() in Appointment class

    String apptTypeFormat = String.format("%-20s", "Appointment Type");
    String timeFormat = String.format("%-20s", "Time");
    System.out.println(timeFormat + apptTypeFormat);
    for (Appointment appt : appts){
      System.out.println(appt);
    }
  }

  /*
  This method parses a string that is formatted in HH:mm a style.
  This method parses the string to get the Time object from it (obtain hour, minute and AMPM).
  */
  public static Time parseTime(String timeString){
    String delims = "[: ]+";
    String tokens[] = new String[3];
    tokens = timeString.split(delims);
    int hour = Integer.parseInt(tokens[0]);
    int minute = Integer.parseInt(tokens[1]);
    String AMPM = tokens[2];
    Time time = new Time(hour, minute, AMPM);
    return time;
  }

  /*
  The main method. Obtains the current local time of the running program and runs operations.
  Only schedules appointments after local time of the running program since assignment asks to list "Future appointments."
  Prints "Exiting..." when the program ends.
  */
  public static void main(String [] args){
    boolean running = true;
    DateFormat df = new SimpleDateFormat("hh:mm a");
    Date dateobj = new Date();
    String curTime = df.format(dateobj);
    startingTime = parseTime(curTime);
    System.out.println("Welcome to your scheduler!");
    System.out.println("The current time is: " + startingTime);
    while(running){
      int choice = getChoice();
      running = useChoice(choice);
    }

    System.out.println("Exiting...");
  }
}
