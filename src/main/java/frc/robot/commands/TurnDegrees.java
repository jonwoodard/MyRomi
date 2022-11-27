package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RomiDrivetrain;

public class TurnDegrees extends CommandBase
{
  private final RomiDrivetrain drivetrain;
  private final double speed;
  private final double angleDegrees;
  private final double inchPerDegree = Math.PI * (141.0 / 25.4) / 360.0;
  private boolean isDriving = false;

  public TurnDegrees(double speed, double angleDegrees, RomiDrivetrain drivetrain)
  {
    this.drivetrain = drivetrain;
    this.speed = speed;
    this.angleDegrees = angleDegrees;

    addRequirements(drivetrain);
  }

  @Override
  public void initialize()
  {
    System.out.println("TurnDegrees(" + speed + ", " + angleDegrees + ")");

    drivetrain.resetEncoders();
  }

  @Override
  public void execute()
  {
    if(drivetrain.isEncoderReset())
    { 
      drivetrain.arcadeDrive(0.0, speed);
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
    return isDriving && getAverageTurningDistance() >= (inchPerDegree * angleDegrees);
  }

  private double getAverageTurningDistance()
  {
    double leftDistance = Math.abs(drivetrain.getLeftDistanceInch());
    double rightDistance = Math.abs(drivetrain.getRightDistanceInch());
    return (leftDistance + rightDistance) / 2.0;
  }
}
