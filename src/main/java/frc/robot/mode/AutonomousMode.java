package frc.robot.mode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;

public class AutonomousMode implements StateControl
{
  static
  {
    System.out.println("AutonomousMode");
  }

  private String autoSelection = "None";
  private double autoDistance1 = 0.0;
  private double autoAngle = 0.0;
  private double autoDistance2 = 0.0;
  private int autoStep = 0;

  public AutonomousMode()
  {
    RobotContainer.autoChooser.setDefaultOption("Auto None", "None");
    RobotContainer.autoChooser.addOption("Auto X", "X");
    RobotContainer.autoChooser.addOption("Auto Y", "Y");
    SmartDashboard.putData("Auto choices", RobotContainer.autoChooser);
  }

  @Override
  public void init()
  {
    RobotContainer.drivetrain.resetEncoders();
    autoStep = 1;

    autoSelection = RobotContainer.disabledMode.getAutoSelection();

    if(autoSelection == "None")
      autoSelection = RobotContainer.autoChooser.getSelected();
    System.out.println("Auto selected: " + autoSelection);

    switch(autoSelection)
    {
      case "A":
        autoDistance1 = 12.0;
        autoAngle = -90.0;
        autoDistance2 = 6.0;
        break;
      case "B":
        autoDistance1 = 12.0;
        autoAngle = 90.0;
        autoDistance2 = 6.0;
        break;
      case "X":
        autoDistance1 = 6.0;
        autoAngle = -90.0;
        autoDistance2 = 12.0;
        break;
      case "Y":
        autoDistance1 = 6.0;
        autoAngle = 90.0;
        autoDistance2 = 12.0;
        break;
      default:
        autoDistance1 = 0.0;
        autoAngle = 0.0;
        autoDistance2 = 0.0;
        break;
    }
  }

  @Override
  public void periodic()
  {
    switch(autoStep)
    {
      case 1:
        if(autoDrive(autoDistance1))
          autoStep++;
        break;
      case 2:
        if(autoSpinGyro(autoAngle))
          autoStep++;
        break;
      case 3:
        if(autoDrive(autoDistance2))
          autoStep++;
        break;
    }
    System.out.println(RobotContainer.drivetrain);
  }

  @Override
  public void exit()
  {
    RobotContainer.drivetrain.arcadeDrive(0.0, 0.0);
    RobotContainer.drivetrain.resetEncoders();

    autoSelection = "None";
    autoStep = 0;
  }

  private boolean autoDrive(double distanceInch)
  {
    boolean isFinished = false;

    if(RobotContainer.drivetrain.getLeftDistanceInch() < distanceInch && 
        RobotContainer.drivetrain.getRightDistanceInch() < distanceInch)
    {  
      RobotContainer.drivetrain.arcadeDrive(0.5, 0.0);
    }
    else
    {
      RobotContainer.drivetrain.arcadeDrive(0.0, 0.0);
      RobotContainer.drivetrain.resetEncoders();
      isFinished = true;
    }

    return isFinished;
  }

  private boolean autoSpin(double angleDegree)
  {
    boolean isFinished = false;
    double direction = Math.signum(angleDegree);

    if(RobotContainer.drivetrain.getLeftSpinDegree() < Math.abs(angleDegree) && 
        RobotContainer.drivetrain.getRightSpinDegree() < Math.abs(angleDegree))
    {  
      RobotContainer.drivetrain.tankDrive(direction * 0.5, -direction * 0.5);
    }
    else
    {
      RobotContainer.drivetrain.tankDrive(0.0, 0.0);
      RobotContainer.drivetrain.resetEncoders();
      isFinished = true;
    }

    return isFinished;
  }

  private boolean autoSpinGyro(double angleDegree)
  {
    boolean isFinished = false;
    double direction = Math.signum(angleDegree);

    if(RobotContainer.romiGyro.getAngle() < Math.abs(angleDegree))
    {
      RobotContainer.drivetrain.tankDrive(direction * 0.5, -direction * 0.5);
    }
    else
    {
      RobotContainer.drivetrain.tankDrive(0.0, 0.0);
      RobotContainer.drivetrain.resetEncoders();
      isFinished = true;
    }

    return isFinished;
  }
}
