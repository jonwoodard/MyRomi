package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot
{
  
  static
  {
    System.out.println("Robot");
    RobotContainer.runMeFirst();
  }

  /**
   * ROBOT methods
   */
  @Override
  public void robotInit() 
  {
    System.out.println("Robot Init");
    SmartDashboard.putString("message", "Robot Init");
    // DriverStation.reportError("ERROR: This is an error", true);
    // DriverStation.reportWarning("WARNING: This is a warning", true);

    RobotContainer.drivetrain.resetEncoders();
    RobotContainer.romiGyro.reset();

    RobotContainer.redLED.off();
    RobotContainer.yellowLED.off();
  }

  @Override
  public void robotPeriodic() 
  {}



  /** 
   * DISABLED MODE methods
   */
  @Override
  public void disabledInit() 
  {
    System.out.println("Disabled Mode");
    SmartDashboard.putString("message", "Disabled");
    
    RobotContainer.disabledMode.init();
  }

  @Override
  public void disabledPeriodic() 
  {
    RobotContainer.disabledMode.periodic();
  }

  @Override
  public void disabledExit()
  {
    RobotContainer.disabledMode.exit();
  }



  /**
   * AUTONOMOUS MODE methods
   */
  @Override
  public void autonomousInit() 
  {
    System.out.println("Autonomous Mode");
    SmartDashboard.putString("message", "Autonomous");

    RobotContainer.autonomousMode.init();
  }

  @Override
  public void autonomousPeriodic() 
  {
    RobotContainer.autonomousMode.periodic();
  }

  @Override
  public void autonomousExit()
  {
    RobotContainer.autonomousMode.exit();
  }



  /** 
   * TELEOP MODE methods
   */
  @Override
  public void teleopInit() 
  {
    System.out.println("Teleop Mode");
    SmartDashboard.putString("message", "Teleop");

    RobotContainer.teleopMode.init();
  }

  @Override
  public void teleopPeriodic() 
  {
    RobotContainer.teleopMode.periodic();
  }

  @Override
  public void teleopExit()
  {
    RobotContainer.teleopMode.exit();
  }



  /** 
   * TEST MODE methods
   */
  @Override
  public void testInit() 
  {
    System.out.println("Test Mode");
    SmartDashboard.putString("message", "Test");

    RobotContainer.testMode.init();
  }

  @Override
  public void testPeriodic() 
  {
    RobotContainer.testMode.periodic();
  }

  @Override
  public void testExit()
  {
    RobotContainer.testMode.exit();
  }
}
