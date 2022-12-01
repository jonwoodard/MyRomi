package frc.robot;

import java.util.ArrayList;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.Button;

import frc.robot.commands.ArcadeDrive;
import frc.robot.subsystems.RomiAccelerometer;
import frc.robot.subsystems.RomiButton;
import frc.robot.subsystems.RomiDrivetrain;
import frc.robot.subsystems.RomiGyro4237;
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

  private final boolean useFullRobot          = true;  // if true, then ALL of the following booleans are automatically true

  private final boolean useRomiDrivetrain     = true;
  private final boolean useJoystick           = true;
  private final boolean useRomiGyro           = true;
  private final boolean useRomiAccelerometer  = true;
  private final boolean useRomiButtonA        = true;
  private final boolean useRomiButtonB        = true;
  private final boolean useRomiLEDRed         = true;
  private final boolean useRomiLEDYellow      = true;
  private final boolean useAutoChooser        = true;

  private final boolean useCommandScheduler   = true;  // convenient way to disable the Command Scheduler


  public final RomiDrivetrain drivetrain;// = new RomiDrivetrain();
  public final Joystick joystick;// = new Joystick(0);

  // https://docs.wpilib.org/en/stable/docs/romi-robot/hardware-support.html
  public final RomiGyro4237 gyro;// = new RomiGyro();
  public final RomiAccelerometer accelerometer;// = new BuiltInAccelerometer();
  
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
    drivetrain      = (useFullRobot || useRomiDrivetrain)     ? new RomiDrivetrain()          : null;
    joystick        = (useFullRobot || useJoystick)           ? new Joystick(0)               : null;
    gyro            = (useFullRobot || useRomiGyro)           ? new RomiGyro4237()            : null;
    accelerometer   = (useFullRobot || useRomiAccelerometer)  ? new RomiAccelerometer()       : null;
    buttonA         = (useFullRobot || useRomiButtonA)        ? new RomiButton(ButtonName.kA) : null;
    buttonB         = (useFullRobot || useRomiButtonB)        ? new RomiButton(ButtonName.kB) : null;
    redLED          = (useFullRobot || useRomiLEDRed)         ? new RomiLED(Color.kRED)       : null;
    yellowLED       = (useFullRobot || useRomiLEDYellow)      ? new RomiLED(Color.kYELLOW)    : null;
    autoChooser     = (useFullRobot || useAutoChooser)        ? new SendableChooser<>()       : null;

    configureAutoChooser();
    configureRomiSubsystem();
    configureBindings();
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
    if(gyro != null)
      allRomiSubsystems.add(gyro);
    if(accelerometer != null)
      allRomiSubsystems.add(accelerometer);
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
      drivetrain.setDefaultCommand(new ArcadeDrive(leftYAxis, rightXAxis, drivetrain));
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

  public boolean useCommandScheduler()
  {
    return useFullRobot || useCommandScheduler;
  }
}
