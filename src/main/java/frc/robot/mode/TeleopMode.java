package frc.robot.mode;

import frc.robot.RobotContainer;

public class TeleopMode implements StateControl
{
  static
  {
    System.out.println("TeleopMode");
  }

  public TeleopMode()
  {}

  @Override
  public void init()
  {}

  @Override
  public void periodic()
  {
    double drive = -RobotContainer.joystick.getRawAxis(1);
    double rotate = RobotContainer.joystick.getRawAxis(4);

    RobotContainer.drivetrain.arcadeDrive(drive, rotate);
  }

  @Override
  public void exit()
  {}
}
