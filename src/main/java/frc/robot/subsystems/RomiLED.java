package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;

public class RomiLED /*extends DigitalOutput*/ implements RomiSubsystem
{
  static
  {
    System.out.println("RomiLED");
  }

  private enum State
  {
    kOFF, kON, kBLINK_OFF, kBLINK_ON;
  }

  public enum Color
  {
    kGREEN(1), kRED(2), kYELLOW(3);

    public final int port;
    private Color(int value)
    {
      port = value;
    }
  }

  private final DigitalOutput digitalOutput;
  private State state = State.kOFF;
  private Color color = null;

  private double lastBlink = 0.0;
  // private boolean isBlinking = false;
  private double blinkOnSec = 0.0;
  private double blinkOffSec = 0.0;
  private boolean isUpdateEnabled = true;

  /**
   * Creates a romi LED (Green, Red, or Yellow)
   * @param color the LED to create
   */
  public RomiLED(Color color)
  {
    // super(color.port);
    digitalOutput = new DigitalOutput(color.port);
    this.color = color;
    off();

    // Create an "LEDs" subsystem in the LiveWindow, along with ".name" field for the LED
    // Switch to Test mode to create a ".controllable" field
    SendableRegistry.addLW(digitalOutput, "RomiLED", color.toString());
  }

  /**
   * Method to turn on the LED
   */
  public void on()
  {
    // isBlinking = false;
    // set(true);
    state = State.kON;
  }

  /**
   * Method to turn off the LED
   */
  public void off()
  {
    // isBlinking = false;
    // set(false);
    state = State.kOFF;
  }

  /**
   * Method to change the LED state
   *  - ON and OFF switch
   *  - BLINK_ON and BLINK_OFF switch
   */
  public void change()
  {
    // isBlinking = false;

    switch(state)
    {
      case kOFF:
        state = State.kON;
        // on();
        break;
      case kON:
        state = State.kOFF;
        // off();
        break;
      case kBLINK_OFF:
        state = State.kBLINK_ON;
        break;
      case kBLINK_ON:
        state = State.kBLINK_OFF;
        break;
    }
  }
  
  /**
   * Method that checks if the LED is on
   * @return true if the LED state is ON or BLINK_ON
   */
  public boolean isOn()
  {
    if(state == State.kON || state == State.kBLINK_ON)
      return true;
    else
      return false;
  }

  /**
   * Method that checks if the LED is off
   * @return true if the LED state is OFF or BLINK_OFF
   */
  public boolean isOff()
  {
    if(state == State.kOFF || state == State.kBLINK_OFF)
      return true;
    else
      return false;
  }

  /**
   * Method that checks if the LED is blinking
   * @return true if the LED state is BLINK_ON or BLINK_OFF
   */
  public boolean isBlinking()
  {
    if(state == State.kBLINK_ON || state == State.kBLINK_OFF)
      return true;
    else
      return false;
  }
  
  /**
   * Method to set the blink rate of the LED
   */
  public void blink(double onSec, double offSec)
  {
    // isBlinking = true;
    state = State.kBLINK_ON;
    blinkOnSec = onSec;
    blinkOffSec = offSec;
    // double currentTime = Timer.getFPGATimestamp();

    // switch(state)
    // {
    //   case kON:
    //     if(currentTime - lastBlink > onSec)
    //     {
    //       lastBlink = lastBlink + onSec;
    //       state = State.kOFF;
    //       // off();
    //     }
    //     break;
    //   case kOFF:
    //     if(currentTime - lastBlink > offSec)
    //     {
    //       lastBlink = lastBlink + offSec;
    //       state = State.kON;
    //       // on();
    //     }
    //     break;
    // }
  }

  /**
   * Method to update the LED state if it is blinking
   */
  private void updateBlink()
  {
    double currentTime = Timer.getFPGATimestamp();

    switch(state)
    {
      case kBLINK_ON:
        if(currentTime - lastBlink > blinkOnSec)
        {
          lastBlink = lastBlink + blinkOnSec;
          state = State.kBLINK_OFF;
          // off();
        }
        break;
      case kBLINK_OFF:
        if(currentTime - lastBlink > blinkOffSec)
        {
          lastBlink = lastBlink + blinkOffSec;
          state = State.kBLINK_ON;
          // on();
        }
        break;
      default:
        break;
    }
  }

  /**
   * Method that is called in robotPeriodic() to read inputs
   */
  @Override
  public synchronized void readPeriodicInputs()
  {}

  /**
   * Method that is called in robotPeriodic() to write outputs
   */
  @Override
  public synchronized void writePeriodicOutputs()
  {
    if(isBlinking())
      updateBlink();
    
    if(state == State.kON || state == State.kBLINK_ON)
      digitalOutput.set(true);
    else
    digitalOutput.set(false);
  }

  /**
   * Method used to enable/disable periodic updates
   * Used to disable in Test Mode for the LiveWindow
   */
  @Override
  public void enablePeriodicUpdates(boolean isEnabled)
  {
    isUpdateEnabled = isEnabled;
  }

  /**
   * Method used to check if the periodic updates are enabled
   */
  @Override
  public boolean isPeriodicUpdateEnabled()
  {
    return isUpdateEnabled;
  }

  @Override
  public String toString()
  {
    String str = color + " " + state;
    return str;
  }

  // @Override
  // public void initSendable(SendableBuilder builder) 
  // {
  //   // Add a ".type" field
  //   builder.setSmartDashboardType("RomiLED");

  //   // Add a "Value" field to get and set the value
  //   builder.addBooleanProperty("Value", this::get, this::set);
  // }
}
