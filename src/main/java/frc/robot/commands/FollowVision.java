/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.concurrent.Callable;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Helper;
import frc.robot.Robot;
import frc.robot.PIDHelper;

public class FollowVision extends Command {
  public int target = 450;
  public double k1 = -.003; // this is negative as a larger value means we are closer to the target 
  // TODO: tune this ^
  public double k2 = .002; // TODO: tune this
  public double k3 = .002; // TODO: tune this

  // public PIDController distController, posController, hDiffController;
  // public PIDHelper.PIDHelperSource 
  //   distSource = new PIDHelper.PIDHelperSource(new Callable<Double>(){
  //     @Override
  //     public Double call() throws Exception {
  //       return Robot.camera.getDistance() - target;
  //     }
  //   }),
  //   posSource = new PIDHelper.PIDHelperSource(new Callable<Double>(){
  //     @Override
  //     public Double call() throws Exception {
  //       return Robot.camera.getPosition();
  //     }
  //   }),
  //   hDiffSource = new PIDHelper.PIDHelperSource(new Callable<Double>(){
  //     @Override
  //     public Double call() throws Exception {
  //       return Robot.camera.getHeightDifference();
  //     }
  //   });

  // public PIDHelper.PIDHelperOutput 
  //   distOutput = new PIDHelper.PIDHelperOutput(), 
  //   posOutput = new PIDHelper.PIDHelperOutput(), 
  //   hDiffOutput = new PIDHelper.PIDHelperOutput();


  public FollowVision() {
    requires(Robot.drivetrain);
    requires(Robot.camera);

    // distController = new PIDController(.003, 0, 0, distSource, distController);
    // posController = new PIDController(.002, 0, 0, posSource, posOutput);
    // hDiffController = new PIDController(.002, 0, 0, hDiffSource, hDiffOutput);
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
      double distError = Robot.camera.getDistance() - target;
      double distValue = distError * k1;

      double posError = Robot.camera.getPosition();
      double posValue = posError * k2;

      double hDiffError = Robot.camera.getHeightDifference();
      double hDiffValue = hDiffError * k3;

      SmartDashboard.putNumber("distValue", distValue);
      SmartDashboard.putNumber("posValue", posValue);
      SmartDashboard.putNumber("hDiffValue", hDiffValue);

      // double distValue = distOutput.getOutput();
      // double posValue = distOutput.getOutput();
      // double hDiffValue = distOutput.getOutput();

      double left = distValue + posValue + hDiffValue;
      double right = distValue - posValue - hDiffValue;

      // left = Helper.deadzone(left, -.1, .1);
      // right = Helper.deadzone(right, -.1, .1);
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
    Robot.drivetrain.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
