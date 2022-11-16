package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Talon;

public class RomiDrivetrain extends DifferentialDrive
{
  static
  {
    System.out.println("RomiDrivetrain");
  }

  private static final double COUNTS_PER_REV = 1440.0;
  private static final double WHEEL_DIAMETER_INCH = 70.0 * (1.0 / 25.4); // 70.0 mm * (1.0 in / 25.4 mm)
  private static final double ROBOT_TRACK_WIDTH_INCH = 141.0 * (1.0 / 25.4);  // 141.0 mm * (1.0 in / 25.4 mm)

  // The Romi has the left and right motors set to
  // PWM channels 0 and 1 respectively
  private static final Talon leftMotor = new Talon(0);
  private static final Talon rightMotor = new Talon(1);

  // The Romi has onboard encoders that are hardcoded
  // to use DIO pins 4/5 and 6/7 for the left and right
  private static final Encoder leftEncoder = new Encoder(4, 5);
  private static final Encoder rightEncoder = new Encoder(6, 7);

  // Set up the differential drive controller
  // private final DifferentialDrive diffDrive = new DifferentialDrive(leftMotor, rightMotor);

  /** Creates a new RomiDrivetrain. */
  public RomiDrivetrain() 
  {
    super(leftMotor, rightMotor);

    // Use inches as unit for encoder distances
    leftEncoder.setDistancePerPulse((Math.PI * WHEEL_DIAMETER_INCH) / COUNTS_PER_REV);
    rightEncoder.setDistancePerPulse((Math.PI * WHEEL_DIAMETER_INCH) / COUNTS_PER_REV);
    resetEncoders();

    // Invert right side since motor is flipped
    rightMotor.setInverted(true);

    SendableRegistry.addLW(this, "RomiDrivetrain", "Motors");
    SendableRegistry.addLW(leftEncoder, "RomiDrivetrain", "Encoder Left");
    SendableRegistry.addLW(rightEncoder, "RomiDrivetrain", "Encoder Right");
  }

  // public void arcadeDrive(double xaxisSpeed, double zaxisRotate) 
  // {
  //   diffDrive.arcadeDrive(xaxisSpeed, zaxisRotate);
  // }

  public void resetEncoders() 
  {
    leftEncoder.reset();
    rightEncoder.reset();
  }

  public double getLeftSpinDegree()
  {
    return leftEncoder.getDistance() / (Math.PI * ROBOT_TRACK_WIDTH_INCH) * 360.0;
  }

  public double getRightSpinDegree()
  {
    return rightEncoder.getDistance() / (Math.PI * ROBOT_TRACK_WIDTH_INCH) * 360.0;
  }
  
  public double getLeftDistanceInch() 
  {
    return leftEncoder.getDistance();
  }

  public double getRightDistanceInch() 
  {
    return rightEncoder.getDistance();
  }

  // public boolean isDriveInchDone(double distance)
  // {
  //   return getLeftDistanceInch() < distance && getRightDistanceInch() < distance;
  // }

  public String toString()
  {
    String str = String.format("Left = %5.1f | Right = %5.1f", getLeftDistanceInch(), getRightDistanceInch());
    // String str = "Left = " + getLeftDistanceInch() + " | Right = " + getRightDistanceInch();
    return str;
  }


}
