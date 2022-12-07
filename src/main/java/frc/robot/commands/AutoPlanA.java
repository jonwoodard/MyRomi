package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.RomiDrivetrain;

public class AutoPlanA extends SequentialCommandGroup
{
  /**
   * Command that completes Autonomous Plan A
   * @param drivetrain the drivetrain subsystem
   */
  public AutoPlanA(RomiDrivetrain drivetrain)
  {
    double speed = 0.5;
    double waitTimeSec = 0.5;

    if(drivetrain != null)
    {
      addCommands(
        new StopDrive(drivetrain),
        // new WaitCommand(waitTimeSec),

        new DriveDistance(drivetrain, speed, 10.0),
        // new WaitCommand(waitTimeSec),
        // new PrintCommand("Left Dist = " + drivetrain.getLeftDistanceInch() + " Right Dist = " + drivetrain.getRightDistanceInch()),

        new SpinDegrees(drivetrain, speed, 180.0),
        // new WaitCommand(waitTimeSec),
        // new PrintCommand("Left Dist = " + drivetrain.getLeftDistanceInch() + " Right Dist = " + drivetrain.getRightDistanceInch()),

        new DriveDistance(drivetrain, speed, 10.0),
        // new WaitCommand(waitTimeSec),
        // new PrintCommand("Left Dist = " + drivetrain.getLeftDistanceInch() + " Right Dist = " + drivetrain.getRightDistanceInch()),

        new SpinDegrees(drivetrain, speed, 180.0),
        
        new PrintCommand("AutoPlanA DONE")
      );
    }
  }
}
