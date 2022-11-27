package frc.robot.modes;

import frc.robot.RobotContainer;

public class DisabledMode implements Mode
{
  static
  {
    System.out.println("DisabledMode");
  }
  
  private final RobotContainer robotContainer;

  // private String autoSelection = "None";

  public DisabledMode(RobotContainer robotContainer)
  {
    this.robotContainer = robotContainer;
  }

  @Override
  public void init()
  {
    // autoSelection = "None";
  }

  @Override
  public void periodic()
  {
    // if(robotContainer.buttonA.getButtonPressed())
    // {
    //   autoSelection = "A";
    // }
    // else if(robotContainer.buttonB.getButtonPressed())
    // {
    //   autoSelection = "B";
    // }
  }

  @Override
  public void exit()
  {}

  // public String getAutoSelection()
  // {
  //   return autoSelection;
  // }
}
