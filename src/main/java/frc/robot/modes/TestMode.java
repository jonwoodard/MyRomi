package frc.robot.modes;

// import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.RobotContainer;

public class TestMode implements Mode
{
  static
  {
    System.out.println("TestMode");
  }

  private final RobotContainer robotContainer;

  public TestMode(RobotContainer robotContainer)
  {
    this.robotContainer = robotContainer;
    
    // CommandScheduler.getInstance().disable();
  }

  @Override
  public void init()
  {
    // Disable the LEDs so that they can be controlled manually
    if(robotContainer.redLED != null)
      robotContainer.redLED.enablePeriodicUpdates(false);
    if(robotContainer.yellowLED != null)
      robotContainer.yellowLED.enablePeriodicUpdates(false);
  }

  @Override
  public void periodic()
  {}

  @Override
  public void exit()
  {
    // Enable the LEDs so that they are controlled automatically
    if(robotContainer.redLED != null)
      robotContainer.redLED.enablePeriodicUpdates(true);
    if(robotContainer.yellowLED != null)
      robotContainer.yellowLED.enablePeriodicUpdates(true);

    // CommandScheduler.getInstance().enable();
  }
}
