package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RomiDrivetrain;

public class DriveDistance extends CommandBase
{
  private final RomiDrivetrain drivetrain;
  private final double speed;
  private final double distanceInch;
  private boolean isDriving = false;
  
  public DriveDistance(double speed, double distanceInch, RomiDrivetrain drivetrain)
  {
    this.drivetrain = drivetrain;
    this.speed = speed;
    this.distanceInch = distanceInch;

    addRequirements(drivetrain);
  }

  @Override
  public void initialize()
  {
    System.out.println("DriveDistance(" + speed + ", " + distanceInch + ")");
    
    drivetrain.resetEncoders();
  }

  @Override
  public void execute()
  {
    if(drivetrain.isEncoderReset())
    {
      drivetrain.arcadeDrive(speed, 0.0);
      isDriving = true;
    }
    else
      drivetrain.arcadeDrive(0.0, 0.0);
  }

  @Override
  public void end(boolean interrupted)
  {
    drivetrain.arcadeDrive(0.0, 0.0);
  }

  @Override
  public boolean isFinished()
  {
    return isDriving && Math.abs(drivetrain.getAverageDistanceInch()) >= distanceInch;
  }
}
