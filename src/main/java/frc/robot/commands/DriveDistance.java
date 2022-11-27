package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RomiDrivetrain;

public class DriveDistance extends CommandBase
{
  private final RomiDrivetrain drivetrain;
  private final double speed;
  private final double distanceInch;
  private boolean isDriving = false;
  
  /**
   * Command that drives the robot a given distance in inches
   * @param speed the speed to drive, use + for forward and - for backward
   * @param distanceInch the distance in inches to drive (use positive value)
   * @param drivetrain the drivetrain subsystem
   */
  public DriveDistance(double speed, double distanceInch, RomiDrivetrain drivetrain)
  {
    this.drivetrain = drivetrain;
    this.speed = speed;
    this.distanceInch = Math.abs(distanceInch);

    addRequirements(drivetrain);
  }

  @Override
  public void initialize()
  {
    System.out.println("DriveDistance(" + speed + ", " + distanceInch + ")");
    
    // NOTE: resetting the encoders takes time, it is not instantaneous
    drivetrain.resetEncoders();
  }

  @Override
  public void execute()
  {
    // Wait for the encoder to reset before driving
    if(drivetrain.isEncoderReset())
    {
      drivetrain.arcadeDrive(speed, 0.0);
      isDriving = true;
    }
    else
      drivetrain.stopMotors();
  }

  @Override
  public void end(boolean interrupted)
  {
    drivetrain.stopMotors();
  }

  @Override
  public boolean isFinished()
  {
    return isDriving && Math.abs(drivetrain.getAverageDistanceInch()) >= distanceInch;
  }
}
