package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot
{
  static
  {
    System.out.println("Robot Class");
  }

  private final RobotContainer robotContainer = new RobotContainer();
  private Command autoCommand = null;

  Robot()
  {
    LiveWindow.disableAllTelemetry();
  }

  /**
   * ROBOT methods
   */
  @Override
  public void robotInit() 
  {
    System.out.println("Robot Init");
    SmartDashboard.putString("message", "Robot Init");
  }

  @Override
  public void robotPeriodic() 
  {
    if(robotContainer.useCommandScheduler())
      CommandScheduler.getInstance().run();
  }



  /** 
   * DISABLED MODE methods
   */
  @Override
  public void disabledInit() 
  {
    System.out.println("Disabled Mode");
    SmartDashboard.putString("message", "Disabled");
  }

  @Override
  public void disabledPeriodic() 
  {}

  @Override
  public void disabledExit()
  {}



  /**
   * AUTONOMOUS MODE methods
   */
  @Override
  public void autonomousInit() 
  {
    System.out.println("Autonomous Mode");
    SmartDashboard.putString("message", "Autonomous");

    autoCommand = robotContainer.getAutonomousCommand();
    if(autoCommand != null)
      autoCommand.schedule();
  }

  @Override
  public void autonomousPeriodic() 
  {}

  @Override
  public void autonomousExit()
  {
    if(autoCommand != null)
    {
      autoCommand.cancel();
      autoCommand = null;
    }
  }



  /** 
   * TELEOP MODE methods
   */
  @Override
  public void teleopInit() 
  {
    System.out.println("Teleop Mode");
    SmartDashboard.putString("message", "Teleop");
  }

  @Override
  public void teleopPeriodic() 
  {}

  @Override
  public void teleopExit()
  {}



  /** 
   * TEST MODE methods
   */
  @Override
  public void testInit() 
  {
    System.out.println("Test Mode");
    SmartDashboard.putString("message", "Test");
  }

  @Override
  public void testPeriodic() 
  {}

  @Override
  public void testExit()
  {}
}
