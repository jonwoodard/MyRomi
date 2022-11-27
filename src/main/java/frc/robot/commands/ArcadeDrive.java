package frc.robot.commands;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RomiDrivetrain;

public class ArcadeDrive extends CommandBase
{
  private final RomiDrivetrain drivetrain;
  private Supplier<Double> speed;
  private Supplier<Double> rotate;

  public ArcadeDrive(RomiDrivetrain drivetrain, Supplier<Double> speed, Supplier<Double> rotate)
  {
    this.drivetrain = drivetrain;
    this.speed = speed;
    this.rotate = rotate;

    addRequirements(drivetrain);
  }

  @Override
  public void initialize()
  {}

  @Override
  public void execute()
  {
    drivetrain.arcadeDrive(speed.get(), rotate.get());
  }

  @Override
  public void end(boolean interrupted)
  {}

  @Override
  public boolean isFinished()
  {
    return false;
  }
}
