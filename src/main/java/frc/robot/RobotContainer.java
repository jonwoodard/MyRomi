package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.AutoPlanA;
import frc.robot.commands.AutoPlanB;
import frc.robot.commands.AutoPlanC;
import frc.robot.controls.JoystickAxis;
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

  private boolean yes = true;
  private boolean no = false;

  private final boolean useFullRobot          = yes;  // if true, then ALL of the following booleans are automatically true

  private final boolean useRomiDrivetrain     = yes;
  private final boolean useRomiGyro           = yes;
  private final boolean useRomiAccelerometer  = yes;
  private final boolean useRomiButtonA        = yes;
  private final boolean useRomiButtonB        = yes;   // Pick either Button B or Green LED
  // private final boolean useRomiButtonC        = false;  // Pick either Button C or Red LED
  // private final boolean useRomiLEDGreen       = false;  // Pick either Button B or Green LED
  private final boolean useRomiLEDRed         = yes;   // Pick either Button C or Red LED
  private final boolean useRomiLEDYellow      = yes;

  private final boolean useJoystick           = yes;
  private final boolean useAutoChooser        = yes;

  private final boolean useCommandScheduler   = yes;  // convenient way to disable the Command Scheduler


  private final RomiDrivetrain drivetrain;
  private final RomiGyro4237 gyro;  // https://docs.wpilib.org/en/stable/docs/romi-robot/hardware-support.html
  private final RomiAccelerometer accelerometer;
  
  // *** RomiButtons ***
  private final RomiButton buttonA;
  private final RomiButton buttonB;  // Pick either Button B or Green LED
  // private final RomiButton buttonC;  // Pick either Button C or Red LED

  // *** RomiLEDS ***
  // private final RomiLED greenLED;    // Pick either Button B or Green LED
  private final RomiLED redLED;      // Pick either Button C or Red LED
  private final RomiLED yellowLED;
  
  private final Joystick joystick;
  private final SendableChooser<String> autoChooser;

  final static ArrayList<RomiSubsystem> allRomiSubsystems = new ArrayList<>();


  /**
   * Contruct all the subsytems and components of the robot
   * NOTE: default modifier (not public, private, protected), only accessible within the same package
   */
  RobotContainer()
  {
    drivetrain      = (useFullRobot || useRomiDrivetrain)     ? new RomiDrivetrain()          : null;
    gyro            = (useFullRobot || useRomiGyro)           ? new RomiGyro4237()            : null;
    accelerometer   = (useFullRobot || useRomiAccelerometer)  ? new RomiAccelerometer()       : null;
    buttonA         = (useFullRobot || useRomiButtonA)        ? new RomiButton(ButtonName.kA) : null;
    buttonB         = (useFullRobot || useRomiButtonB)        ? new RomiButton(ButtonName.kB) : null;
    redLED          = (useFullRobot || useRomiLEDRed)         ? new RomiLED(Color.kRED)       : null;
    yellowLED       = (useFullRobot || useRomiLEDYellow)      ? new RomiLED(Color.kYELLOW)    : null;

    joystick        = (useFullRobot || useJoystick)           ? new Joystick(0)               : null;
    autoChooser     = (useFullRobot || useAutoChooser)        ? new SendableChooser<>()       : null;

    configureRomiSubsystems();
    configureAutoChooser();
    configureBindings();
  }

  private void configureRomiSubsystems()
  {
    if(drivetrain != null)
      allRomiSubsystems.add(drivetrain);
    if(gyro != null)
      allRomiSubsystems.add(gyro);
    if(accelerometer != null)
      allRomiSubsystems.add(accelerometer);
    if(yellowLED != null)
      allRomiSubsystems.add(yellowLED);
    if(redLED != null)
      allRomiSubsystems.add(redLED);
    if(buttonA != null)
      allRomiSubsystems.add(buttonA);
    if(buttonB != null)
      allRomiSubsystems.add(buttonB);
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
      autoChooser.addOption("Auto C", "C");
      SmartDashboard.putData("Auto choices", autoChooser);
    }
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
    if(joystick != null && drivetrain != null)
    {
      JoystickButton driverButtonA = new JoystickButton(joystick, 1);
      driverButtonA.whenPressed(() -> drivetrain.toggleSpeedFactor(), drivetrain);

      JoystickAxis leftYAxis = new JoystickAxis(joystick, 1, true);
      JoystickAxis rightXAxis = new JoystickAxis(joystick, 4, false);
      drivetrain.setDefaultCommand(new ArcadeDrive(drivetrain, leftYAxis, rightXAxis));
      // drivetrain.setDefaultCommand(new ArcadeDrive(drivetrain, leftYAxis, rightXAxis));
    }
  }

  // /**
  //  * Method to configure bindings for Driver controls
  //  */
  // private void configureDriverBindings2()
  // {
  //   // using lambda expressions

  //   if(joystick != null && drivetrain != null)
  //   {
  //     Button driverButtonA = new Button(() -> joystick.getRawButtonPressed(1));
  //     driverButtonA.whenPressed(() -> drivetrain.toggleSpeedFactor(), drivetrain);

  //     Supplier<Double> leftYAxis = () -> -joystick.getRawAxis(1);
  //     Supplier<Double> rightXAxis = () -> joystick.getRawAxis(4);
  //     drivetrain.setDefaultCommand(new ArcadeDrive(drivetrain, leftYAxis, rightXAxis));
  //     // drivetrain.setDefaultCommand(new ArcadeDrive(drivetrain, () -> -joystick.getRawAxis(1), () -> joystick.getRawAxis(4)));
  //   }
  // }

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

  public Command getAutonomousCommand()
  {
    Command autoCommand = null;

    if(autoChooser != null)
    {
      String autoSelection = autoChooser.getSelected();
      System.out.println("Auto selected: " + autoSelection);

      switch(autoSelection)
      {
        case "A":
          if(drivetrain != null)
            autoCommand = new AutoPlanA(drivetrain);
          break;

        case "B":
          if(drivetrain != null)
            autoCommand = new AutoPlanB(drivetrain);
          break;

        case "C":
          if(drivetrain != null && gyro != null)
            autoCommand = new AutoPlanC(drivetrain, gyro);
          break;
          
        default:
          autoCommand = null;
          break;
      }
    }
    return autoCommand;
  }

  public boolean useCommandScheduler()
  {
    return useFullRobot || useCommandScheduler;
  }
}
