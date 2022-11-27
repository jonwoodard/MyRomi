package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Drive;

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


  /** Creates a new RomiDrivetrain. */
  public RomiDrivetrain() 
  {
    // super(leftMotor, rightMotor);
    diffDrive.setSafetyEnabled(false);

    // Use inches as unit for encoder distances
    leftEncoder.setDistancePerPulse((Math.PI * Drive.WHEEL_DIAMETER_INCH) / Drive.COUNTS_PER_REV);
    rightEncoder.setDistancePerPulse((Math.PI * Drive.WHEEL_DIAMETER_INCH) / Drive.COUNTS_PER_REV);
    resetEncoders();
    encoderResetTimer.reset();
    encoderResetTimer.start();

    // Invert right side since motor is flipped
    rightMotor.setInverted(true);

    // SendableRegistry.addLW(this, "RomiDrivetrain", "Motors");
    SendableRegistry.addLW(diffDrive, "RomiDrivetrain", "Motors");
    SendableRegistry.addLW(leftEncoder, "RomiDrivetrain", "Encoder Left");
    SendableRegistry.addLW(rightEncoder, "RomiDrivetrain", "Encoder Right");

    diffDrive.setSafetyEnabled(true);
  }

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

  private double truncate(double value, int precision)
  {
    double power = Math.pow(10, precision);
    return Math.floor(value * power) / power;
  }

  public void arcadeDrive(double speed, double rotate) 
  {
    driveState = DriveState.kARCADE;
    arcadeDriveSpeed = truncate(speed * speedFactor.value, 2);
    arcadeDriveRotate = truncate(rotate * speedFactor.value, 2);

    tankDriveLeftSpeed = 0.0;
    tankDriveRightSpeed = 0.0;
    // diffDrive(speed, rotate);
  }

  public void tankDrive(double leftSpeed, double rightSpeed)
  {
    driveState = DriveState.kTANK;
    tankDriveLeftSpeed = truncate(leftSpeed * speedFactor.value, 2);
    tankDriveRightSpeed = truncate(rightSpeed * speedFactor.value, 2);

    arcadeDriveSpeed = 0.0;
    arcadeDriveRotate = 0.0;
    // diffDrive.tankDrive(leftSpeed, rightSpeed);
  }

  public void stopMotors()
  {
    driveState = DriveState.kNONE;
    arcadeDriveSpeed = 0.0;
    arcadeDriveRotate = 0.0;
    tankDriveLeftSpeed = 0.0;
    tankDriveRightSpeed = 0.0;
    // diffDrive.stopMotor();
  }

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

  public double getLeftSpinDegree()
  {
    return leftEncoderDistance / (Math.PI * Drive.ROBOT_TRACK_WIDTH_INCH) * 360.0;
    // return leftEncoder.getDistance() / (Math.PI * ROBOT_TRACK_WIDTH_INCH) * 360.0;
  }

  public double getRightSpinDegree()
  {
    return rightEncoderDistance / (Math.PI * Drive.ROBOT_TRACK_WIDTH_INCH) * 360.0;
    // return rightEncoder.getDistance() / (Math.PI * ROBOT_TRACK_WIDTH_INCH) * 360.0;
  }
  
  public double getLeftDistanceInch() 
  {
    return leftEncoderDistance;
    // return leftEncoder.getDistance();
  }

  public double getRightDistanceInch() 
  {
    return rightEncoderDistance;
    // return rightEncoder.getDistance();
  }

  public double getAverageDistanceInch()
  {
    return (leftEncoderDistance + rightEncoderDistance) / 2.0;
  }

  public boolean isEncoderReset()
  {
    // System.out.println("LE = " + leftEncoderRaw + " RE = " + rightEncoderRaw);

    return isEncoderReset;
  }

  @Override
  public synchronized void readPeriodicInputs()
  {
    leftEncoderDistance = leftEncoder.getDistance();
    rightEncoderDistance = rightEncoder.getDistance();
    leftEncoderRaw = leftEncoder.get();
    rightEncoderRaw = rightEncoder.get();
  }

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

  public String toString()
  {
    String str = String.format("Left = %5.1f | Right = %5.1f", getLeftDistanceInch(), getRightDistanceInch());
    // String str = "Left = " + getLeftDistanceInch() + " | Right = " + getRightDistanceInch();
    return str;
  }


}
