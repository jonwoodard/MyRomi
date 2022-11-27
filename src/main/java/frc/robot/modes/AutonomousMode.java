package frc.robot.modes;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.commands.AutoPlanA;
import frc.robot.commands.AutoPlanB;

public class AutonomousMode implements Mode
{
  static
  {
    System.out.println("AutonomousMode");
  }

  private final RobotContainer robotContainer;

  private String autoSelection = "None";
  private Command autoCommand = null;
  // private double autoDistance1 = 0.0;
  // private double autoAngle = 0.0;
  // private double autoDistance2 = 0.0;
  // private int autoStep = 0;
  // private int counter = 0;

  public AutonomousMode(RobotContainer robotContainer)
  {
    this.robotContainer = robotContainer;
  }

  @Override
  public void init()
  {
    // CommandScheduler.getInstance().enable();
    
    // autoStep = 1;

    // Check if button A or B was pressed during disabled mode
    // FIXME autoSelection = RobotContainer.disabledMode.getAutoSelection();

    // If no button was pressed during disabled mode, then get the autonomous selection from the dashboard
    // if(autoSelection == "None")
    autoSelection = robotContainer.autoChooser.getSelected();
    System.out.println("Auto selected: " + autoSelection);

    switch(autoSelection)
    {
      case "A":
        autoCommand = new AutoPlanA(robotContainer.drivetrain);
        autoCommand.schedule();
        break;
      case "B":
        autoCommand = new AutoPlanB(robotContainer.drivetrain);
        autoCommand.schedule();
        break;
      default:
        autoCommand = null;
        break;
    }
  }

  @Override
  public void periodic()
  {
    // switch(autoStep)
    // {
    //   case 1:
    //     if(autoDrive(autoDistance1))
    //       autoStep++;
    //     break;
    //   case 2:
    //     if(autoSpinGyro(autoAngle))
    //       autoStep++;
    //     break;
    //   case 3:
    //     if(autoDrive(autoDistance2))
    //       autoStep++;
    //     break;
    // }

    // counter++;
    // if(counter % 10 == 0)
    //   System.out.println(robotContainer.drivetrain);
  }

  @Override
  public void exit()
  {
    // CommandScheduler.getInstance().disable();

    robotContainer.drivetrain.arcadeDrive(0.0, 0.0);
    robotContainer.drivetrain.resetEncoders();

    autoSelection = "None";
    if(autoCommand != null)
    {
      autoCommand.cancel();
      autoCommand = null;
    }
    // autoStep = 0;
  }



  // private boolean autoDrive(double distanceInch)
  // {
  //   boolean isFinished = false;

  //   if(robotContainer.drivetrain.getLeftDistanceInch() < distanceInch && 
  //     robotContainer.drivetrain.getRightDistanceInch() < distanceInch)
  //   {  
  //     robotContainer.drivetrain.arcadeDrive(0.5, 0.0);
  //   }
  //   else
  //   {
  //     robotContainer.drivetrain.arcadeDrive(0.0, 0.0);
  //     robotContainer.drivetrain.resetEncoders();
  //     isFinished = true;
  //   }

  //   return isFinished;
  // }

  // private boolean autoSpin(double angleDegree)
  // {
  //   boolean isFinished = false;
  //   double direction = Math.signum(angleDegree);

  //   if(robotContainer.drivetrain.getLeftSpinDegree() < Math.abs(angleDegree) && 
  //     robotContainer.drivetrain.getRightSpinDegree() < Math.abs(angleDegree))
  //   {  
  //     robotContainer.drivetrain.tankDrive(direction * 0.5, -direction * 0.5);
  //   }
  //   else
  //   {
  //     robotContainer.drivetrain.tankDrive(0.0, 0.0);
  //     robotContainer.drivetrain.resetEncoders();
  //     isFinished = true;
  //   }

  //   return isFinished;
  // }

  // private boolean autoSpinGyro(double angleDegree)
  // {
  //   boolean isFinished = false;
  //   double direction = Math.signum(angleDegree);

  //   if(robotContainer.romiGyro.getAngle() < Math.abs(angleDegree))
  //   {
  //     robotContainer.drivetrain.tankDrive(direction * 0.5, -direction * 0.5);
  //   }
  //   else
  //   {
  //     robotContainer.drivetrain.tankDrive(0.0, 0.0);
  //     robotContainer.drivetrain.resetEncoders();
  //     isFinished = true;
  //   }

  //   return isFinished;
  // }
}
