/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Helper;
/**
 * Add your docs here.
 */
public class Drivetrain extends SubsystemManagerChild {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  WPI_TalonSRX left, right;

  public Drivetrain () {
    left = new WPI_TalonSRX(25);
    right = new WPI_TalonSRX(11);

    left.setNeutralMode(NeutralMode.Brake);
    right.setNeutralMode(NeutralMode.Brake);

    left.setInverted(false);
    right.setInverted(true);
  }

  public void setLeft(double power) {
    left.set(Helper.bound(power, -0.5, 0.5));
  }

  public void setRight(double power) {
    right.set(Helper.bound(power, -0.5, 0.5));
  }

  public void set(double left, double right) {
    setLeft(left);
    setRight(right);
  }

  public void stop() {
    set(0,0);
  }

  public double getLeft() {
    return left.get();
  }

  public double getRight() {
    return right.get();
  }

  @Override
  public void updateSD() {
    SmartDashboard.putNumber("Left Power", getLeft());
    SmartDashboard.putNumber("Right Power", getRight());
  }
}
