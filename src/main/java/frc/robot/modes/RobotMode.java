package frc.robot.modes;

// import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.RobotContainer;
import frc.robot.subsystems.RomiSubsystem;

public class RobotMode implements Mode
{
  static
  {
    System.out.println("RobotMode");
  }

  // private final Timer timer = new Timer();
  // private double prevXAccel = 0.0;
  // private double prevYAccel = 0.0;

  private final RobotContainer robotContainer;

  public RobotMode(RobotContainer robotContainer)
  {
    this.robotContainer = robotContainer;
  }

  @Override
  public void init()
  {
    robotContainer.drivetrain.resetEncoders();
    robotContainer.romiGyro.reset();

    robotContainer.redLED.off();
    robotContainer.yellowLED.off();

    // timer.reset();
    // timer.start();
  }

  @Override
  public void periodic()
  {
    CommandScheduler.getInstance().run();

    for(RomiSubsystem romiSubsystem : robotContainer.allRomiSubsystems)
    {
      if(romiSubsystem.isPeriodicUpdateEnabled())
      {
        romiSubsystem.readPeriodicInputs();
        romiSubsystem.writePeriodicOutputs();
      }
    }


    // RobotContainer.drivetrain.readPeriodicInputs();
    // RobotContainer.drivetrain.writePeriodicOutputs();
    // double xAccel = RobotContainer.accelerometer.getX();
    // double yAccel = RobotContainer.accelerometer.getY();
    // double loopTime = timer.get();
    // double xJerk = (xAccel - prevXAccel) / loopTime;
    // double yJerk = (yAccel = prevYAccel) / loopTime;

    // String str = String.format("x = %5.1f | y = %5.1f", xJerk, yJerk);
    // System.out.println(str);

    // prevXAccel = xAccel;
    // prevYAccel = yAccel;
    // timer.reset();
  }
}
