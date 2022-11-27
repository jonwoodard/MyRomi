package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.DigitalInput;

public class RomiButton /*extends DigitalInput*/ implements RomiSubsystem
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
  private boolean currentlyPressed = false;

  public RomiButton(ButtonName button)
  {
    // super(button.port);
    digitalInput = new DigitalInput(button.port);
    this.button = button;

    SendableRegistry.addLW(digitalInput, "RomiButton", button.toString());
  }

  private void updateState()
  {
    // boolean currentlyPressed = get();
    switch(state)
    {
      case kPRESSED:
        if(currentlyPressed)
          state = State.kSTILL_PRESSED;
        else
          state = State.kRELEASED;
        break;

      case kSTILL_PRESSED:
        if(!currentlyPressed)
          state = State.kRELEASED;
        break;

      case kRELEASED:
        if(currentlyPressed)
          state = State.kPRESSED;
        else
          state = State.kSTILL_RELEASED;
        break;
        
      case kSTILL_RELEASED:
        if(currentlyPressed)
          state = State.kPRESSED; 
        break;

      default:
        break;
    }
  }

  public boolean getButton()
  {
    // updateState();
    return state == State.kPRESSED || state == State.kSTILL_PRESSED;
  }

  public boolean getButtonPressed()
  {
    // updateState();
    return state == State.kPRESSED;
  }
  
  public boolean getButtonReleased()
  {
    // updateState();
    return state == State.kRELEASED;
  }

  @Override
  public synchronized void readPeriodicInputs()
  {
    currentlyPressed = digitalInput.get();
    updateState();
  }

  @Override
  public synchronized void writePeriodicOutputs()
  {

  }

  @Override
  public String toString()
  {
    String str = button + " " + state;
    return str;
  }
}
