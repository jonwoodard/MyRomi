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

  public void on()
  {
    // isBlinking = false;
    // set(true);
    state = State.kON;
  }

  public void off()
  {
    // isBlinking = false;
    // set(false);
    state = State.kOFF;
  }

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
  
  public boolean isOn()
  {
    if(state == State.kON || state == State.kBLINK_ON)
      return true;
    else
      return false;
  }

  public boolean isOff()
  {
    if(state == State.kOFF || state == State.kBLINK_OFF)
      return true;
    else
      return false;
  }

  public boolean isBlinking()
  {
    if(state == State.kBLINK_ON || state == State.kBLINK_OFF)
      return true;
    else
      return false;
  }
 
  
  /**
   * This method must be called periodically in order to blink the LED.
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

  @Override
  public synchronized void readPeriodicInputs()
  {}

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

  @Override
  public void enablePeriodicUpdates(boolean isEnabled)
  {
    isUpdateEnabled = isEnabled;
  }

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
