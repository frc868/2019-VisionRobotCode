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
  public int target = 200;

  public double k_dist   = -0.02; // this is negative as a larger value means we are closer to the target 
  public double k_pos    =  0.004;
  public double k_hratio = -.0025;

  // public PIDController distController, posController, hRatioController;
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
  //   hRatioSource = new PIDHelper.PIDHelperSource(new Callable<Double>(){
  //     @Override
  //     public Double call() throws Exception {
  //       return Robot.camera.getHeightRatio();
  //     }
  //   });

  // public PIDHelper.PIDHelperOutput 
  //   distOutput = new PIDHelper.PIDHelperOutput(), 
  //   posOutput = new PIDHelper.PIDHelperOutput(), 
  //   hRatioOutput = new PIDHelper.PIDHelperOutput();


  public FollowVision() {
    requires(Robot.drivetrain);
    requires(Robot.camera);

    // distController = new PIDController(.003, 0, 0, distSource, distController);
    // posController = new PIDController(.002, 0, 0, posSource, posOutput);
    // hRatioController = new PIDController(.002, 0, 0, hRatioSource, hRatioOutput);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    // running this in non-vision mode will cause bad things to happen
    // new SwitchToVision();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (!Robot.camera.hasTarget()) {
      //Robot.drivetrain.set(0.2, 0.2);
      Robot.drivetrain.stop();
    } else {
      k_dist = SmartDashboard.getNumber("k_dist", k_dist);
      k_pos = SmartDashboard.getNumber("k_pos", k_pos);
      k_hratio = SmartDashboard.getNumber("k_hratio", k_hratio);

      double distError = Robot.camera.getDistance() - target;
      double distValue = distError * k_dist;

      double posError = Robot.camera.getPosition();
      double posValue = posError * k_pos;

      double hRatioError = Robot.camera.getHeightRatio();
      double hRatioValue = hRatioError * k_hratio;

      SmartDashboard.putNumber("distValue", distValue);
      SmartDashboard.putNumber("posValue", posValue);
      SmartDashboard.putNumber("hRatioValue", hRatioValue);

      // double distValue = distOutput.getOutput();
      // double posValue = distOutput.getOutput();
      // double hRatioValue = distOutput.getOutput();

      double left = (distValue + posValue + hRatioValue) / 10.0;
      SmartDashboard.putNumber("lpwr", left);
      double right = (distValue - posValue - hRatioValue) / 10.0;
      SmartDashboard.putNumber("rpwr", right);

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
