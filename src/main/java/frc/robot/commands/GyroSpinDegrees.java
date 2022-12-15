package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RomiDrivetrain;
import frc.robot.subsystems.RomiGyro4237;

public class GyroSpinDegrees extends CommandBase
{
  private final RomiDrivetrain drivetrain;
  private final RomiGyro4237 gyro;
  private final double speed;
  private final double angleDegrees;
  private double initialAngleDegrees;

  /**
   * Command that spins the robot a given number of degrees
   * @param speed the speed to spin, use + for clockwise and - for counterclockwise
   * @param angleDegrees the angle in degrees to spin (use positive value)
   * @param drivetrain the drivetrain subsystem
   */
  public GyroSpinDegrees(RomiDrivetrain drivetrain, RomiGyro4237 gyro, double speed, double angleDegrees)
  {
    this.drivetrain = drivetrain;
    this.gyro = gyro;
    this.speed = speed;
    this.angleDegrees = Math.abs(angleDegrees);
    

    if(this.drivetrain != null && this.gyro != null)
      addRequirements(drivetrain, gyro);
  }

  @Override
  public void initialize()
  {
    System.out.println("SpinDegreesGyro(" + speed + ", " + angleDegrees + ")");

    // NOTE: resetting the encoders takes time, it is not instantaneous
    if(gyro != null)
      initialAngleDegrees = gyro.getAngle();
  }

  @Override
  public void execute()
  {
    // Wait for the encoder to reset before spinning
    if(drivetrain != null)
    {
      drivetrain.arcadeDrive(0.0, speed);
    }
  }

  @Override
  public void end(boolean interrupted)
  {
    if(drivetrain != null)
      drivetrain.stopMotors();
  }

  @Override
  public boolean isFinished()
  {
    if(drivetrain != null && gyro != null && speed != 0.0)
      return Math.abs(gyro.getAngle() - initialAngleDegrees) >= angleDegrees;
    else
      return true;
  }
}
