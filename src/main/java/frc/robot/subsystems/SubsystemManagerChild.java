package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.subsystems.SubsystemManager;

public abstract class SubsystemManagerChild extends Subsystem {
    public SubsystemManagerChild() {
      SubsystemManager.subsystems.add(this);
    }

    public void init() {

    }

    public void initSD() {

    }

    public void update() {

    }

    public void updateSD() {
      
    }
}