package frc.robot;

import java.util.ArrayList;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.romi.RomiGyro;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.Button;

import frc.robot.commands.ArcadeDrive;
import frc.robot.subsystems.RomiButton;
import frc.robot.subsystems.RomiDrivetrain;
import frc.robot.subsystems.RomiLED;
import frc.robot.subsystems.RomiSubsystem;
import frc.robot.subsystems.RomiButton.ButtonName;
import frc.robot.subsystems.RomiLED.Color;

// final class - cannot be inherited as a superclass
public final class RobotContainer 
{
  static
  {
    System.out.println("RobotContainer");
  }

  private final boolean useFullRobot        = true;
  private final boolean useRomiDrivetrain   = true;
  private final boolean useJoystick         = true;
  private final boolean useRomiGyro         = true;
  private final boolean useAccelerometer    = true;
  private final boolean useRomiButtonA      = true;
  private final boolean useRomiButtonB      = true;
  private final boolean useRomiRedLED       = true;
  private final boolean useRomiYellowLED    = true;
  private final boolean useAutoChooser      = true;

  public final RomiDrivetrain drivetrain;// = new RomiDrivetrain();
  public final Joystick joystick;// = new Joystick(0);

  // https://docs.wpilib.org/en/stable/docs/romi-robot/hardware-support.html
  public final RomiGyro romiGyro;// = new RomiGyro();
  public final BuiltInAccelerometer accelerometer;// = new BuiltInAccelerometer();
  
  // *** Buttons and LEDs
  public final RomiButton buttonA;// = new RomiButton(ButtonName.kA);

  // Pick either Button B or Green LED
  public final RomiButton buttonB;// = new RomiButton(ButtonName.kB);
  // private final RomiLED green = new RomiLED(Color.kGREEN);

  // Pick either Button C or Red LED
  // private final RomiButton buttonC = new RomiButton(Button.kC);
  public final RomiLED redLED;// = new RomiLED(Color.kRED);

  public final RomiLED yellowLED;// = new RomiLED(Color.kYELLOW);

  public final SendableChooser<String> autoChooser;// = new SendableChooser<>();

  public final ArrayList<RomiSubsystem> allRomiSubsystems = new ArrayList<>();

  /**
   * Contruct all the subsytems and components of the robot
   * NOTE: default modifier (not public, private, protected), only accessible within the same package
   */
  RobotContainer()
  {
    drivetrain =    (useFullRobot || useRomiDrivetrain) ? new RomiDrivetrain()          : null;
    joystick =      (useFullRobot || useJoystick)       ? new Joystick(0)               : null;
    romiGyro =      (useFullRobot || useRomiGyro)       ? new RomiGyro()                : null;
    accelerometer = (useFullRobot || useAccelerometer)  ? new BuiltInAccelerometer()    : null;
    buttonA =       (useFullRobot || useRomiButtonA)    ? new RomiButton(ButtonName.kA) : null;
    buttonB =       (useFullRobot || useRomiButtonB)    ? new RomiButton(ButtonName.kB) : null;
    redLED =        (useFullRobot || useRomiRedLED)     ? new RomiLED(Color.kRED)       : null;
    yellowLED =     (useFullRobot || useRomiYellowLED)  ? new RomiLED(Color.kYELLOW)    : null;
    autoChooser =   (useFullRobot || useAutoChooser)    ? new SendableChooser<>()       : null;

    configureAutoChooser();
    configureRomiSubsystem();
    configureBindings();

    if(accelerometer != null)
      SendableRegistry.addLW(accelerometer, "RomiAccelerometer", "Accelerometer");
  }

  /**
   * Method to configure the chooser for the Autonomous Selection
   */
  private void configureAutoChooser()
  {
    if(autoChooser != null)
    {
      autoChooser.setDefaultOption("Auto None", "None");
      autoChooser.addOption("Auto A", "A");
      autoChooser.addOption("Auto B", "B");
      SmartDashboard.putData("Auto choices", autoChooser);
    }
  }

  /**
   * Method to add the romi subsystems to the list of periodic updates
   */
  private void configureRomiSubsystem()
  {
    if(drivetrain != null)
      allRomiSubsystems.add(drivetrain);
    if(redLED != null)
      allRomiSubsystems.add(redLED);
    if(yellowLED != null)
      allRomiSubsystems.add(yellowLED);
    if(buttonA != null)
      allRomiSubsystems.add(buttonA);
    if(buttonB != null)
      allRomiSubsystems.add(buttonB);
  }

  /**
   * Method to configure ALL bindings for buttons, axis, joysticks, etc.
   */
  private void configureBindings()
  {
    configureDriverBindings();
    configureRomiButtonBindings();
  }

  /**
   * Method to configure bindings for Driver controls
   */
  private void configureDriverBindings()
  {
    // using lambda expressions

    if(joystick != null && drivetrain != null)
    {
      Button driverButtonA = new Button(() -> joystick.getRawButtonPressed(1));
      driverButtonA.whenPressed(() -> drivetrain.toggleSpeedFactor(), drivetrain);

      Supplier<Double> leftYAxis = () -> -joystick.getRawAxis(1);
      Supplier<Double> rightXAxis = () -> joystick.getRawAxis(4);
      drivetrain.setDefaultCommand(new ArcadeDrive(drivetrain, leftYAxis, rightXAxis));
      // drivetrain.setDefaultCommand(new ArcadeDrive(drivetrain, () -> -joystick.getRawAxis(1), () -> joystick.getRawAxis(4)));
    }
  }

  /**
   * Method to configure bindings for Romi Buttons
   */
  private void configureRomiButtonBindings()
  {
    // using lambda expressions

    if(buttonA != null)
    {
      Button romiButtonA = new Button(() -> buttonA.getButton());
      romiButtonA.whenPressed(new PrintCommand("RomiButton A Pressed"));
      romiButtonA.whenReleased(new PrintCommand("RomiButton A Released"));
    }

    if(buttonB != null)
    {
      Button romiButtonB = new Button(() -> buttonB.getButton());
      romiButtonB.whenPressed(new PrintCommand("RomiButton B Pressed"));
      romiButtonB.whenReleased(new PrintCommand("RomiButton B Released"));
    }
  }
}
