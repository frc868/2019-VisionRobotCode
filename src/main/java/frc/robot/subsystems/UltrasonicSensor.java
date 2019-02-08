/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class UltrasonicSensor extends SubsystemManagerChild {
  private Ultrasonic us;

  public UltrasonicSensor() {
    super();

    // echo-trigger
    us = new Ultrasonic(1, 0);
  }

  public double getDistance() {
    return us.getRangeInches();
  }

  @Override
  public void init() {
  }

  @Override
  public void initSD() {
  }

  @Override
  public void update() {
  }

  @Override
  public void updateSD() {
    SmartDashboard.putNumber("UltrasonicSensor", this.getDistance());
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
