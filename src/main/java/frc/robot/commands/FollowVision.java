/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class FollowVision extends Command {
  // 200: porta-target, 230-240ish: rocket
  public int target = 230;

  public static double k_dist   = -0.028; // this is negative as a larger value means we are closer to the target 
  public static double k_pos    =  0.012;
  public static double k_hratio =  0.005;

  public FollowVision() {
    requires(Robot.drivetrain);
    requires(Robot.camera);
  }

  @Override
  protected void initialize() {
    //(new SwitchToVision()).start();
  }

  @Override
  protected void execute() {
    k_dist = SmartDashboard.getNumber("k_dist", k_dist);
    k_pos = SmartDashboard.getNumber("k_pos", k_pos);
    k_hratio = SmartDashboard.getNumber("k_hratio", k_hratio);

    // if one's null they should all be null
    if (Robot.camera.hasTarget()) {
      double distError = Robot.camera.getDistance() - target;
      double distValue = distError * k_dist;

      double posError = Robot.camera.getPosition();
      double posValue = posError * k_pos;

      double hRatioError = Robot.camera.getHeightRatio();
      double hRatioValue = hRatioError * k_hratio;

      SmartDashboard.putNumber("distValue", distValue);
      SmartDashboard.putNumber("posValue", posValue);
      SmartDashboard.putNumber("hRatioValue", hRatioValue);

      double left = (distValue + posValue + hRatioValue) / 10.0;
      SmartDashboard.putNumber("lpwr", left);
      double right = (distValue - posValue - hRatioValue) / 10.0;
      SmartDashboard.putNumber("rpwr", right);

      Robot.drivetrain.set(left, -right);
    } 
  }

  @Override
  protected boolean isFinished() {
    return (Robot.ultrasonic.getDistance() < 5);
  }

  @Override
  protected void end() {
    Robot.drivetrain.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }
}
