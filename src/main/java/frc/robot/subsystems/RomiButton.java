package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RomiButton extends SubsystemBase /*extends DigitalInput*/ implements RomiSubsystem
{
  static
  {
    System.out.println("RomiButton");
  }

  private enum State
  {
    kPRESSED, kSTILL_PRESSED, kRELEASED, kSTILL_RELEASED;
  }

  public enum ButtonName
  {
    kA(0), kB(1), kC(2);

    public final int port;
    private ButtonName(int value)
    {
      port = value;
    }
  }
  private final DigitalInput digitalInput;
  private State state = State.kSTILL_RELEASED;
  private ButtonName button = null;
  private boolean isCurrentlyPressed = false;

  /**
   * Creates a romi button (A, B, or C)
   * @param button the button to create
   */
  public RomiButton(ButtonName button)
  {
    // super(button.port);
    digitalInput = new DigitalInput(button.port);
    this.button = button;

    SendableRegistry.addLW(digitalInput, "RomiButton", button.toString());
  }

  /**
   * Method that returns the state of the button (true = pressed, false = released)
   * @return the state of the button
   */
  public boolean getButton()
  {
    // updateState();
    return state == State.kPRESSED || state == State.kSTILL_PRESSED;
  }

  /**
   * Method that returns if the button was just pressed
   * @return the state of the press
   */
  public boolean getButtonPressed()
  {
    // updateState();
    return state == State.kPRESSED;
  }
  
  /**
   * Method that returns if the button was just released
   * @return the state of the release
   */
  public boolean getButtonReleased()
  {
    // updateState();
    return state == State.kRELEASED;
  }
  
  private void updateState()
  {
    // boolean currentlyPressed = get();
    switch(state)
    {
      case kPRESSED:
        if(isCurrentlyPressed)
          state = State.kSTILL_PRESSED;
        else
          state = State.kRELEASED;
        break;

      case kSTILL_PRESSED:
        if(!isCurrentlyPressed)
          state = State.kRELEASED;
        break;

      case kRELEASED:
        if(isCurrentlyPressed)
          state = State.kPRESSED;
        else
          state = State.kSTILL_RELEASED;
        break;
        
      case kSTILL_RELEASED:
        if(isCurrentlyPressed)
          state = State.kPRESSED; 
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
  {
    isCurrentlyPressed = digitalInput.get();
    updateState();
  }

  /**
   * Method that is called in robotPeriodic() to write outputs
   */
  @Override
  public synchronized void writePeriodicOutputs()
  {}

  @Override
  public void periodic()
  {
    // readPeriodicInputs();
    // writePeriodicOutputs();
  }

  @Override
  public String toString()
  {
    String str = button + " " + state;
    return str;
  }
}
