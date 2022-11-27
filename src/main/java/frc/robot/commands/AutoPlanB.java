package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.RomiDrivetrain;

public class AutoPlanB extends SequentialCommandGroup
{
  public AutoPlanB(RomiDrivetrain drivetrain)
  {
    double speed = 0.5;
    double waitTimeSec = 0.5;
    
    addCommands(
      new WaitCommand(waitTimeSec),

      new DriveDistance(speed, -10.0, drivetrain),
      // new WaitCommand(waitTimeSec),
      // new PrintCommand("Left Dist = " + drivetrain.getLeftDistanceInch() + " Right Dist = " + drivetrain.getRightDistanceInch()),

      new TurnDegrees(speed, -180.0, drivetrain),
      // new WaitCommand(waitTimeSec),
      // new PrintCommand("Left Dist = " + drivetrain.getLeftDistanceInch() + " Right Dist = " + drivetrain.getRightDistanceInch()),

      new DriveDistance(speed, -10.0, drivetrain),
      // new WaitCommand(waitTimeSec),
      // new PrintCommand("Left Dist = " + drivetrain.getLeftDistanceInch() + " Right Dist = " + drivetrain.getRightDistanceInch()),

      new TurnDegrees(speed, -180.0, drivetrain),
      new PrintCommand("AutoPlanB DONE")
    );
  }
}
