package frc.robot.mode;

import frc.robot.RobotContainer;

public class DisabledMode implements StateControl
{
  static
  {
    System.out.println("DisabledMode");
  }
  
  private String autoSelection = "None";

  public DisabledMode()
  {}

  @Override
  public void init()
  {
    autoSelection = "None";
  }

  @Override
  public void periodic()
  {
    if(RobotContainer.buttonA.getButtonPressed())
    {
      autoSelection = "A";
    }
    else if(RobotContainer.buttonB.getButtonPressed())
    {
      autoSelection = "B";
    }
  }

  @Override
  public void exit()
  {}

  public String getAutoSelection()
  {
    return autoSelection;
  }
}
