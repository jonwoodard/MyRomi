package frc.robot;

import edu.wpi.first.wpilibj.romi.RomiGyro;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.mode.AutonomousMode;
import frc.robot.mode.DisabledMode;
import frc.robot.mode.TeleopMode;
import frc.robot.mode.TestMode;
import frc.robot.sensors.RomiButton;
import frc.robot.sensors.RomiLED;
import frc.robot.sensors.RomiButton.Button;
import frc.robot.sensors.RomiLED.Color;
import frc.robot.subsystems.RomiDrivetrain;

public final class RobotContainer 
{
  static
  {
    System.out.println("RobotContainer");
  }

  public final static RomiDrivetrain drivetrain = new RomiDrivetrain();
  public final static Joystick joystick = new Joystick(0);

  // https://docs.wpilib.org/en/stable/docs/romi-robot/hardware-support.html
  public final static RomiGyro romiGyro = new RomiGyro();

  
  // *** Buttons and LEDs
  public final static RomiButton buttonA = new RomiButton(Button.kA);

  // Pick either Button B or Green LED
  public final static RomiButton buttonB = new RomiButton(Button.kB);
  // private final RomiLED green = new RomiLED(Color.kGREEN);

  // Pick either Button C or Red LED
  // private final RomiButton buttonC = new RomiButton(Button.kC);
  public final static RomiLED redLED = new RomiLED(Color.kRED);

  public final static RomiLED yellowLED = new RomiLED(Color.kYELLOW);


  public final static SendableChooser<String> autoChooser = new SendableChooser<>();

  public final static AutonomousMode autonomousMode = new AutonomousMode();
  public final static TeleopMode teleopMode = new TeleopMode();
  public final static TestMode testMode = new TestMode();
  public final static DisabledMode disabledMode = new DisabledMode();

  private RobotContainer()
  {}

  public static void runMeFirst()
  {}
}
