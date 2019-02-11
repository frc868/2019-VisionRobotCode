/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Helper;
import frc.robot.RobotMap;
/**
 * Add your docs here.
 */
public class Drivetrain extends SubsystemManagerChild {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private Spark leftPrimary, rightPrimary; 
  
  public Drivetrain() {
    leftPrimary = new Spark(RobotMap.DriveTrain.LEFT_P);
    rightPrimary = new Spark(RobotMap.DriveTrain.RIGHT_P);
  }
  public void setLeft(double leftP) {
    leftPrimary.set((leftP > 1 ? 1 : leftP) < -1 ? -1 : leftP);
  }
  public void setRight(double rightP) {
    rightPrimary.set((rightP > 1 ? 1 : rightP) < -1 ? -1 : rightP);
  }
  public void set(double left, double right) {
    setLeft(left);
    setRight(right);
  }
  public double getLeft() {
    return leftPrimary.get();
  }
  public double getRight() {
    return rightPrimary.get();
  }

  public void stop() {
    this.set(0,0);
  }

  @Override
  public void updateSD() {
    SmartDashboard.putNumber("Left Power", getLeft());
    SmartDashboard.putNumber("Right Power", getRight());
  }
}
