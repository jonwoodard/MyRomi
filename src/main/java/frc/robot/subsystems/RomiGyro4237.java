package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.romi.RomiGyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RomiGyro4237 extends SubsystemBase implements RomiSubsystem
{
  static
  {
    System.out.println("RomiGyro4237");
  }

  private final RomiGyro gyro = new RomiGyro();
  private final Timer gyroResetTimer = new Timer();
  
  // Periodic INPUT
  private double angle;

  // Periodic OUTPUT
  private boolean resetGyro;
  private boolean isGyroReset;


  public RomiGyro4237()
  {
    reset();
  }

  /**
   * Method to reset the gyro
   * NOTE: resetting the gyro takes time, it is not instantaneous
   * NOTE: use the method isGyroReset() to check if the gyro has reset
   */
  public void reset()
  {
    resetGyro = true;
    isGyroReset = false;
    gyroResetTimer.reset();
    gyroResetTimer.start();
  }

  /**
   * Method to check if the gyro have been reset
   * @return true when the gyro is reset
   */
  public boolean isGyroReset()
  {
    return isGyroReset;
  }

  /**
   * Method to get the gyro angle
   * @return the angle of the gyro
   */
  public double getAngle()
  {
    return angle;
  }

  /**
   * Method that is called in robotPeriodic() to read inputs
   */
  @Override
  public synchronized void readPeriodicInputs()
  {
    angle = gyro.getAngle();
  }

  /**
   * Method that is called in robotPeriodic() to write outputs
   */
  @Override
  public synchronized void writePeriodicOutputs()
  {
    if(resetGyro)
    {
      gyro.reset();
      resetGyro = false;
    }
    else if(!isGyroReset)
    {
      if(Math.abs(angle) <= 0.5)
        isGyroReset = true;
      else if(gyroResetTimer.hasElapsed(0.1))
      {
        gyro.reset();
        gyroResetTimer.reset();
        gyroResetTimer.start();
      }
    }
  }

  @Override
  public void periodic()
  {
    // readPeriodicInputs();
    // writePeriodicOutputs();
  }
  
  @Override
  public String toString()
  {
    String str = String.format("Gyro: angle = %5.1f", angle);
    return str;
  }
}

