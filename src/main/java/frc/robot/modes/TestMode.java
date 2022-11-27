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
    robotContainer.redLED.enablePeriodicUpdates(false);
    robotContainer.yellowLED.enablePeriodicUpdates(false);
  }

  @Override
  public void periodic()
  {}

  @Override
  public void exit()
  {
    // Enable the LEDs so that they are controlled automatically
    robotContainer.redLED.enablePeriodicUpdates(true);
    robotContainer.yellowLED.enablePeriodicUpdates(true);
    // CommandScheduler.getInstance().enable();
  }
}
