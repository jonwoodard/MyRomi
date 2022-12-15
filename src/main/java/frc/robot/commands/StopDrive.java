package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RomiDrivetrain;

public class StopDrive extends CommandBase
{
  private final RomiDrivetrain drivetrain;

  public StopDrive(RomiDrivetrain drivetrain)
  {
    this.drivetrain = drivetrain;

    if(this.drivetrain != null)
      addRequirements(drivetrain);
  }

  @Override
  public void initialize()
  {
    System.out.println("StopDrive()");

    if(drivetrain != null)
      drivetrain.stopMotors();
  }

  @Override
  public void execute()
  {}

  @Override
  public void end(boolean interrupted)
  {}

  @Override
  public boolean isFinished()
  {
    return true;
  }
}
