package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RomiAccelerometer extends SubsystemBase implements RomiSubsystem
{
  static
  {
    System.out.println("RomiAccelerometer");
  }
  
  private final BuiltInAccelerometer accelerometer = new BuiltInAccelerometer();

  private double x;
  private double y;
  private double z;

  public RomiAccelerometer()
  {
    SendableRegistry.addLW(accelerometer, "RomiAccelerometer", "Accelerometer");
    SubsystemManager.addRomiSubsystem(this);
  }

  /**
   * Method to get the acceleration in the x direction
   * @return the x acceleration
   */
  public double getX()
  {
    return x;
  }

  /**
   * Method to get the acceleration in the y direction
   * @return the y acceleration
   */
  public double getY()
  {
    return y;
  }

  /**
   * Method to get the acceleration in the z direction
   * @return the z acceleration
   */
  public double getZ()
  {
    return z;
  }

  /**
   * Method that is called in robotPeriodic() to read inputs
   */
  @Override
  public synchronized void readPeriodicInputs()
  {
    x = accelerometer.getX();
    y = accelerometer.getY();
    z = accelerometer.getZ();
  }
  
  /**
   * Method that is called in robotPeriodic() to write outputs
   */
  @Override
  public synchronized void writePeriodicOutputs()
  {}

  @Override
  public void periodic()
  {
    // readPeriodicInputs();
    // writePeriodicOutputs();
  }

  @Override
  public String toString()
  {
    String str = String.format("Accel: x = %5.1f | y = %5.1f | z = %5.1f", x, y, z);
    return str;
  }
}

