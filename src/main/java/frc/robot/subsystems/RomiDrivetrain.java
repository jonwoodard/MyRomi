package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.DrivetrainConstants;

public class RomiDrivetrain extends SubsystemBase implements RomiSubsystem
{
  static
  {
    System.out.println("RomiDrivetrain");
  }

  private enum DriveState
  {
    kNONE, kARCADE, kTANK;
  }

  private enum SpeedFactor
  {
    kSLOW(0.75), kFAST(1.0);

    public final double value;

    private SpeedFactor(double value)
    {
      this.value = value;
    }
  }

  // The Romi has the left and right motors set to
  // PWM channels 0 and 1 respectively
  private final Talon leftMotor = new Talon(0);
  private final Talon rightMotor = new Talon(1);

  // The Romi has onboard encoders that are hardcoded
  // to use DIO pins 4/5 and 6/7 for the left and right
  private final Encoder leftEncoder = new Encoder(4, 5);
  private final Encoder rightEncoder = new Encoder(6, 7);
  private final Timer encoderResetTimer = new Timer();

  // Set up the differential drive controller
  private final DifferentialDrive diffDrive = new DifferentialDrive(leftMotor, rightMotor);
 
  private DriveState driveState = DriveState.kNONE;
  private SpeedFactor speedFactor = SpeedFactor.kSLOW;

  // Periodic INPUT
  private boolean resetEncoders;
  private boolean isEncoderReset;
  private double leftEncoderDistance;
  private double rightEncoderDistance;
  private int leftEncoderRaw;
  private int rightEncoderRaw;

  // Periodic OUTPUT
  private double arcadeDriveSpeed;
  private double arcadeDriveRotate;
  private double tankDriveLeftSpeed;
  private double tankDriveRightSpeed;


  /** 
   * Creates a new RomiDrivetrain
   */
  public RomiDrivetrain() 
  {
    // super(leftMotor, rightMotor);
    diffDrive.setSafetyEnabled(false);

    // Use inches as unit for encoder distances
    leftEncoder.setDistancePerPulse((Math.PI * DrivetrainConstants.WHEEL_DIAMETER_INCH) / DrivetrainConstants.COUNTS_PER_REV);
    rightEncoder.setDistancePerPulse((Math.PI * DrivetrainConstants.WHEEL_DIAMETER_INCH) / DrivetrainConstants.COUNTS_PER_REV);
    resetEncoders();

    // Invert right side since motor is flipped
    rightMotor.setInverted(true);

    // SendableRegistry.addLW(this, "RomiDrivetrain", "Motors");
    SendableRegistry.addLW(diffDrive, "RomiDrivetrain", "Motors");
    SendableRegistry.addLW(leftEncoder, "RomiDrivetrain", "Encoder Left");
    SendableRegistry.addLW(rightEncoder, "RomiDrivetrain", "Encoder Right");

    diffDrive.setSafetyEnabled(true);
  }

  /**
   * Method to toggle the speed factor from SLOW to FAST
   */
  public void toggleSpeedFactor()
  {
    switch(speedFactor)
    {
      case kSLOW:
        speedFactor = SpeedFactor.kFAST;
        break;
      case kFAST:
        speedFactor = SpeedFactor.kSLOW;
        break;
    }
  }

  /**
   * Method to truncate a double to a given precision
   * @param value the value to be truncated
   * @param precision the number of digits of precision
   * @return the truncated value
   */
  private double truncate(double value, int precision)
  {
    double power = Math.pow(10, precision);
    return Math.floor(value * power) / power;
  }

  /**
   * Method to drive the robot using arcade drive technique
   * @param speed the speed to drive
   * @param rotate the rotation of the drive, use + for clockwise and - for counterclockwise
   */
  public void arcadeDrive(double speed, double rotate) 
  {
    driveState = DriveState.kARCADE;
    arcadeDriveSpeed = truncate(speed * speedFactor.value, 2);
    arcadeDriveRotate = truncate(rotate * speedFactor.value, 2);

    tankDriveLeftSpeed = 0.0;
    tankDriveRightSpeed = 0.0;
    // diffDrive(speed, rotate);
  }

  /**
   * Method to drive the robot using tank drive technique
   * @param leftSpeed the speed of the left motor
   * @param rightSpeed the speed of the right motor
   */
  public void tankDrive(double leftSpeed, double rightSpeed)
  {
    driveState = DriveState.kTANK;
    tankDriveLeftSpeed = truncate(leftSpeed * speedFactor.value, 2);
    tankDriveRightSpeed = truncate(rightSpeed * speedFactor.value, 2);

    arcadeDriveSpeed = 0.0;
    arcadeDriveRotate = 0.0;
    // diffDrive.tankDrive(leftSpeed, rightSpeed);
  }

  /**
   * Method to stop the drive motors
   */
  public void stopMotors()
  {
    driveState = DriveState.kNONE;
    arcadeDriveSpeed = 0.0;
    arcadeDriveRotate = 0.0;
    tankDriveLeftSpeed = 0.0;
    tankDriveRightSpeed = 0.0;
    // diffDrive.stopMotor();
  }

  /**
   * Method to reset the encoders
   * NOTE: resetting the encoders takes time, it is not instantaneous
   * NOTE: use the method isEncoderReset() to check if the encoder has reset
   */
  public void resetEncoders() 
  {
    driveState = DriveState.kNONE;
    resetEncoders = true;
    isEncoderReset = false;
    encoderResetTimer.reset();
    encoderResetTimer.start();
    // leftEncoder.reset();
    // rightEncoder.reset();
  }

  /**
   * Method to check if the encoders have been reset
   * @return true when the encoders were reset
   */
  public boolean isEncoderReset()
  {
    // System.out.println("LE = " + leftEncoderRaw + " RE = " + rightEncoderRaw);

    return isEncoderReset;
  }

  /**
   * Method to calculate the spin angle in degrees for the left encoder
   * @return the angle in degrees
   */
  public double getLeftSpinDegree()
  {
    return leftEncoderDistance / (Math.PI * DrivetrainConstants.ROBOT_TRACK_WIDTH_INCH) * 360.0;
    // return leftEncoder.getDistance() / (Math.PI * ROBOT_TRACK_WIDTH_INCH) * 360.0;
  }

  /**
   * Method to calculate the spin angle in degrees for the right encoder
   * @return the angle in degrees
   */
  public double getRightSpinDegree()
  {
    return rightEncoderDistance / (Math.PI * DrivetrainConstants.ROBOT_TRACK_WIDTH_INCH) * 360.0;
    // return rightEncoder.getDistance() / (Math.PI * ROBOT_TRACK_WIDTH_INCH) * 360.0;
  }
  
  /**
   * Method to return the distance in inches of the left encoder
   * @return the distance in inches
   */
  public double getLeftDistanceInch() 
  {
    return leftEncoderDistance;
    // return leftEncoder.getDistance();
  }

  /**
   * Method to return the distance in inches of the right encoder
   * @return the distance in inches
   */
  public double getRightDistanceInch() 
  {
    return rightEncoderDistance;
    // return rightEncoder.getDistance();
  }

  /**
   * Method to average the distance travelled using both encoders
   * @return the average distance
   */
  public double getAverageDistanceInch()
  {
    return (leftEncoderDistance + rightEncoderDistance) / 2.0;
  }

  /**
   * Method that averages the left and right encoder distances
   * @return the average of the left and right encoder distances
   */
  public double getAverageSpinningAngle()
  {
    double leftAngle = Math.abs(getLeftSpinDegree());
    double rightAngle = Math.abs(getRightSpinDegree());
    return (leftAngle + rightAngle) / 2.0;
  }

  /**
   * Method that is called in robotPeriodic() to read inputs
   */
  @Override
  public synchronized void readPeriodicInputs()
  {
    leftEncoderDistance = leftEncoder.getDistance();
    rightEncoderDistance = rightEncoder.getDistance();
    leftEncoderRaw = leftEncoder.get();
    rightEncoderRaw = rightEncoder.get();
  }

  /**
   * Method that is called in robotPeriodic() to write outputs
   */
  @Override
  public synchronized void writePeriodicOutputs()
  {
    if(resetEncoders)
    {
      diffDrive.stopMotor();
      leftEncoder.reset();
      rightEncoder.reset();
      resetEncoders = false;
    }
    else if(!isEncoderReset)
    {
      if(leftEncoderRaw == 0 && rightEncoderRaw == 0)
        isEncoderReset = true;
      else if(encoderResetTimer.hasElapsed(0.1))
      {
        leftEncoder.reset();
        rightEncoder.reset();
        encoderResetTimer.reset();
        encoderResetTimer.start();
      }
    }
    else
    {
      switch(driveState)
      {
        case kARCADE:
          diffDrive.arcadeDrive(arcadeDriveSpeed, arcadeDriveRotate);
          break;
        case kTANK:
          diffDrive.tankDrive(tankDriveLeftSpeed, tankDriveRightSpeed);
          break;
        case kNONE:
          diffDrive.stopMotor();
          break;
      }
    }

    diffDrive.feedWatchdog();
  }

  @Override
  public void periodic()
  {
    readPeriodicInputs();
    writePeriodicOutputs();
  }

  public String toString()
  {
    String str = String.format("Left = %5.1f | Right = %5.1f", leftEncoderDistance, rightEncoderDistance);
    // String str = "Left = " + getLeftDistanceInch() + " | Right = " + getRightDistanceInch();
    return str;
  }
}
