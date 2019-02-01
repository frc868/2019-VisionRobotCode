/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Helper;
import frc.robot.Robot;

public class FollowVision extends Command {
  public int target = 420;
  public double k1 = .003; // TODO: tune this
  public double k2 = .002; // TODO: tune this
  public double k3 = .002; // TODO: tune this

  public FollowVision() {
    requires(Robot.drivetrain);
    requires(Robot.camera);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (!Robot.camera.hasTarget()) {
      Robot.drivetrain.stop();
    } else {
      double distanceError = Robot.camera.getDistance() - target;
      double distanceValue = distanceError * k1;

      double angleError = Robot.camera.getPosition();
      double angleValue = angleError * k2;

      double heightDifferenceError = Robot.camera.getHeightDifference();
      double heightDifferenceValue = heightDifferenceError * k3;

      double left = distanceValue - angleValue + heightDifferenceValue;
      double right = distanceValue + angleValue - heightDifferenceValue;

      left = Helper.deadzone(left, -.15, .1);
      right = Helper.deadzone(right, -.15, .1);
      Robot.drivetrain.set(left, right);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
