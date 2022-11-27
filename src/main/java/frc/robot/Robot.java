package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.modes.AutonomousMode;
import frc.robot.modes.DisabledMode;
import frc.robot.modes.RobotMode;
import frc.robot.modes.TeleopMode;
import frc.robot.modes.TestMode;

public class Robot extends TimedRobot
{
  static
  {
    System.out.println("Robot Class");
  }

  private final RobotContainer robotContainer = new RobotContainer();

  private final RobotMode robotMode = new RobotMode(robotContainer);
  private final DisabledMode disabledMode = new DisabledMode(robotContainer);
  private final AutonomousMode autonomousMode = new AutonomousMode(robotContainer);
  private final TeleopMode teleopMode = new TeleopMode(robotContainer);
  private final TestMode testMode = new TestMode(robotContainer);


  /**
   * ROBOT methods
   */
  @Override
  public void robotInit() 
  {
    System.out.println("Robot Init");
    SmartDashboard.putString("message", "Robot Init");
    
    robotMode.init();
  }

  @Override
  public void robotPeriodic() 
  {
    robotMode.periodic();
  }



  /** 
   * DISABLED MODE methods
   */
  @Override
  public void disabledInit() 
  {
    System.out.println("Disabled Mode");
    SmartDashboard.putString("message", "Disabled");
    
    disabledMode.init();
  }

  @Override
  public void disabledPeriodic() 
  {
    disabledMode.periodic();
  }

  @Override
  public void disabledExit()
  {
    disabledMode.exit();
  }



  /**
   * AUTONOMOUS MODE methods
   */
  @Override
  public void autonomousInit() 
  {
    System.out.println("Autonomous Mode");
    SmartDashboard.putString("message", "Autonomous");

    autonomousMode.init();
  }

  @Override
  public void autonomousPeriodic() 
  {
    autonomousMode.periodic();
  }

  @Override
  public void autonomousExit()
  {
    autonomousMode.exit();
  }



  /** 
   * TELEOP MODE methods
   */
  @Override
  public void teleopInit() 
  {
    System.out.println("Teleop Mode");
    SmartDashboard.putString("message", "Teleop");

    teleopMode.init();
  }

  @Override
  public void teleopPeriodic() 
  {
    teleopMode.periodic();
  }

  @Override
  public void teleopExit()
  {
    teleopMode.exit();
  }



  /** 
   * TEST MODE methods
   */
  @Override
  public void testInit() 
  {
    System.out.println("Test Mode");
    SmartDashboard.putString("message", "Test");

    testMode.init();
  }

  @Override
  public void testPeriodic() 
  {
    testMode.periodic();
  }

  @Override
  public void testExit()
  {
    testMode.exit();
  }
}
