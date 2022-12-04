package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RomiDrivetrain;

public class SpinDegrees extends CommandBase
{
  private final RomiDrivetrain drivetrain;
  private final double speed;
  private final double angleDegrees;
  private boolean isDriving = false;

  /**
   * Command that spins the robot a given number of degrees
   * @param speed the speed to spin, use + for clockwise and - for counterclockwise
   * @param angleDegrees the angle in degrees to spin (use positive value)
   * @param drivetrain the drivetrain subsystem
   */
  public SpinDegrees(RomiDrivetrain drivetrain, double speed, double angleDegrees)
  {
    this.drivetrain = drivetrain;
    this.speed = speed;
    this.angleDegrees = Math.abs(angleDegrees);

    if(this.drivetrain != null)
      addRequirements(drivetrain);
  }

  @Override
  public void initialize()
  {
    System.out.println("TurnDegrees(" + speed + ", " + angleDegrees + ")");

    // NOTE: resetting the encoders takes time, it is not instantaneous
    if(this.drivetrain != null)
      drivetrain.resetEncoders();
  }

  @Override
  public void execute()
  {
    // Wait for the encoder to reset before spinning
    if(drivetrain != null)
    {
      if(drivetrain.isEncoderReset())
      { 
        drivetrain.arcadeDrive(0.0, speed);
        isDriving = true;
      }
      else
        drivetrain.stopMotors();
    }
  }

  @Override
  public void end(boolean interrupted)
  {
    if(this.drivetrain != null)
      drivetrain.stopMotors();
  }

  @Override
  public boolean isFinished()
  {
    if(this.drivetrain != null)
      return isDriving && drivetrain.getAverageSpinningAngle() >= angleDegrees;
    else
      return true;
  }
}
