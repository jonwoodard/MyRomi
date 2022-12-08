package frc.robot.subsystems;

// https://github.com/Team254/FRC-2020-Public

/**
 * Interface to periodically update inputs/outputs for the romi subsystem
 */
public interface RomiSubsystem 
{
  public abstract void readPeriodicInputs();
  public abstract void writePeriodicOutputs();
}
