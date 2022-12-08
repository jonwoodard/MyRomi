package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SubsystemManager extends SubsystemBase
{
  static
  {
    System.out.println("SubsystemManager");
  }

  private final static ArrayList<RomiSubsystem> romiSubsystems = new ArrayList<>();

  @Override
  public void periodic()
  {
    for(RomiSubsystem rs : romiSubsystems)
    {
      rs.readPeriodicInputs();
      rs.writePeriodicOutputs();
    }
  }

  public static void addRomiSubsystem(RomiSubsystem subsystem)
  {
    romiSubsystems.add(subsystem);
  }
}
