package frc.robot.controls;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.GenericHID;

public class JoystickAxis implements Supplier<Double>
{
  private final GenericHID joystick;
  private final int axisNumber;
  private final int sign;

  public JoystickAxis(GenericHID joystick, int axisNumber, boolean isNegated)
  {
    this.joystick = joystick;
    this.axisNumber = axisNumber;

    if(isNegated)
      this.sign = -1;
    else
      this.sign = 1;
  }

  @Override
  public Double get()
  {
    return sign * joystick.getRawAxis(axisNumber);
  }
}
