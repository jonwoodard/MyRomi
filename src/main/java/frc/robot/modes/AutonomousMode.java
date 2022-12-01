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

  // private int autoStep = 0;
  // private int counter = 0;

  public AutonomousMode(RobotContainer robotContainer)
  {
    this.robotContainer = robotContainer;
  }

  @Override
  public void init()
  {
    // autoStep = 1;
    // autoSelection = RobotContainer.autoChooser.getSelected();
    // System.out.println("Auto selected: " + autoSelection);

    if(robotContainer.autoChooser != null)
    {
      autoSelection = robotContainer.autoChooser.getSelected();
      System.out.println("Auto selected: " + autoSelection);

      switch(autoSelection)
      {
        case "A":
          if(robotContainer.drivetrain != null)
          {
            autoCommand = new AutoPlanA(robotContainer.drivetrain);
            autoCommand.schedule();
          }
          break;

        case "B":
          if(robotContainer.drivetrain != null)
          {
            autoCommand = new AutoPlanB(robotContainer.drivetrain);
            autoCommand.schedule();
          }
          break;

        default:
          autoCommand = null;
          break;
      }
    }
  }

  @Override
  public void periodic()
  {
    // switch(autoSelection)
    // {
    //   case "A":
    //     autoPlanA();
    //     break;
    //   case "B":
    //     autoPlanB();
    //     break;
    // }

    // counter++;
    // if(counter % 10 == 0)
    //   System.out.println(robotContainer.drivetrain);
  }

  @Override
  public void exit()
  {
    // autoStep = 0;
    
    if(robotContainer.drivetrain != null)
    {
      robotContainer.drivetrain.stopMotors();
      robotContainer.drivetrain.resetEncoders();
    }

    autoSelection = "None";
    if(autoCommand != null)
    {
      autoCommand.cancel();
      autoCommand = null;
    }

  }


  // private void autoPlanA()
  // {
  //   switch(autoStep)
  //   {
  //     case 1:
  //       if(autoDrive(12.0))
  //         autoStep++;
  //       break;
  //     case 2:
  //       if(autoSpinGyro(-90.0))
  //         autoStep++;
  //       break;
  //   }
  // }

  // private void autoPlanB()
  // {
  //   switch(autoStep)
  //   {
  //     case 1:
  //       if(autoDrive(12.0))
  //         autoStep++;
  //       break;
  //     case 2:
  //       if(autoSpinGyro(90.0))
  //         autoStep++;
  //       break;
  //   }
  // }

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
