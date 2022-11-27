package frc.robot.modes;

public interface Mode
{
  public abstract void init();
  public abstract void periodic();
  
  public default void exit()
  {}
}
