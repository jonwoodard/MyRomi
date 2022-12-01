package frc.robot;

// final class - cannot be inherited as a superclass
public final class Constants
{
  static
  {
    System.out.println("Constants");
  }

  public final static class DrivetrainConstants
  {
    public final static int LEFT_MOTOR_PORT = 0;
    public final static int RIGHT_MOTOR_PORT = 1;
    public final static int LEFT_ENCODER_CHANNEL_A = 4;
    public final static int LEFT_ENCODER_CHANNEL_B = 5;
    public final static int RIGHT_ENCODER_CHANNEL_A = 6;
    public final static int RIGHT_ENCODER_CHANNEL_B = 7;

    
    public final static double COUNTS_PER_REV = 1440.0;
    public final static double WHEEL_DIAMETER_INCH = 70.0 * (1.0 / 25.4); // 70.0 mm * (1.0 in / 25.4 mm)
    public final static double ROBOT_TRACK_WIDTH_INCH = 141.0 * (1.0 / 25.4);  // 141.0 mm * (1.0 in / 25.4 mm)
  }

  // private constructor - cannot be instantiated outside of this class
  private Constants()
  {}
}
