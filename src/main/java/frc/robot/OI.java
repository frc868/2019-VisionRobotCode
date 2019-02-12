/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.buttons.Button;
//import edu.wpi.first.wpilibj.buttons.JoystickButton;
//import frc.robot.commands.*;
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    XboxController controller;
    public OI(){
        controller = new XboxController(0);
    } //TODO spacing
    public void init(){
 //       Button a = new JoystickButton(controller, 0);
 //       Button b = new JoystickButton(controller, 1);
 //       a.whileHeld(new doIntake(.5));
 //       b.whileHeld(new Shoot(.5));
    } //TODO spacing
    public void update(){
        double leftYAxis = controller.getRawAxis(1);
        //double leftXAxis = controller.getRawAxis(0);
        //double rightYAxis = controller.getRawAxis(5);
        double rightXAxis = controller.getRawAxis(4);
        //double leftTrigger = controller.getRawAxis(2);
        //double rightTrigger = controller.getRawAxis(3);
        Robot.drivetrain.set((-leftYAxis + rightXAxis), (leftYAxis + rightXAxis));

    //    if(leftTrigger>0 && rightTrigger == 0)Robot.tilt.setPower(-leftTrigger);
    //    if(rightTrigger>0 && leftTrigger == 0)Robot.tilt.setPower(rightTrigger);
 }

}
