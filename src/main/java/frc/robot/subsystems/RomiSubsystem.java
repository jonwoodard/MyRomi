package frc.robot.subsystems;

// https://github.com/Team254/FRC-2020-Public

/**
 * Interface to periodically update inputs/outputs for the romi subsystem
 */
public interface RomiSubsystem 
{
  public abstract void readPeriodicInputs();
  public abstract void writePeriodicOutputs();

  public default void enablePeriodicUpdates(boolean isEnabled)
  {}

  public default boolean isPeriodicUpdateEnabled()
  {
    return true;
  }
}
