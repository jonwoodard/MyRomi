package frc.robot.mode;

public interface StateControl
{
  public abstract void init();
  public abstract void periodic();
  public abstract void exit();
}
