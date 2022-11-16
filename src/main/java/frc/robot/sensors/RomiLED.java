package frc.robot.sensors;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;

public class RomiLED extends DigitalOutput
{
  static
  {
    System.out.println("RomiLED");
  }

  private enum State
  {
    kOFF, kON;
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

  private State state = State.kOFF;
  private Color color = null;
  private double lastBlink = 0.0;

  public RomiLED(Color color)
  {
    super(color.port);
    this.color = color;
    off();

    // Create an "LEDs" subsystem in the LiveWindow, along with ".name" field for the LED
    // Switch to Test mode to create a ".controllable" field
    SendableRegistry.addLW(this, "RomiLED", color.toString());
  }

  public void on()
  {
    set(true);
    state = State.kON;
  }

  public void off()
  {
    set(false);
    state = State.kOFF;
  }

  public void change()
  {
    switch(state)
    {
      case kOFF:
        on();
        break;
      case kON:
        off();
        break;
    }
  }
  
  public boolean isOn()
  {
    if(state == State.kON)
      return true;
    else
      return false;
  }

  public boolean isOff()
  {
    if(state == State.kOFF)
      return true;
    else
      return false;
  }
 
  
  /**
   * This method must be called periodically in order to blink the LED.
   */
  public void blink(double onSec, double offSec)
  {
    double currentTime = Timer.getFPGATimestamp();

    switch(state)
    {
      case kON:
        if(currentTime - lastBlink > onSec)
        {
          lastBlink = lastBlink + onSec;
          off();
        }
        break;
      case kOFF:
        if(currentTime - lastBlink > offSec)
        {
          lastBlink = lastBlink + offSec;
          on();
        }
        break;
    }
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
