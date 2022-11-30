package frc.robot.modes;

import frc.robot.RobotContainer;

public class TeleopMode implements Mode
{
  static
  {
    System.out.println("TeleopMode");
  }

  private final RobotContainer robotContainer;

  public TeleopMode(RobotContainer robotContainer)
  {
    this.robotContainer = robotContainer;
  }

  @Override
  public void init()
  {}

  @Override
  public void periodic()
  {
    // double drive = -robotContainer.joystick.getRawAxis(1);
    // double rotate = robotContainer.joystick.getRawAxis(4);

    // robotContainer.drivetrain.arcadeDrive(drive, rotate);
  }

  @Override
  public void exit()
  {}
}
