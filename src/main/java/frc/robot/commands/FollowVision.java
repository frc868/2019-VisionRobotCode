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
      double error = Robot.camera.getDistance() - target;
      double power = error * k1;
      double angle = Robot.camera.getPosition();
      double offset = angle * k2;

      double left = power - offset;
      double right = power + offset;

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
