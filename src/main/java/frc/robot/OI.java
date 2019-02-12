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
    }
    public void init(){
    }
    public void update(){
        double leftYAxis = controller.getRawAxis(1);
        double rightXAxis = controller.getRawAxis(4);
        Robot.drivetrain.set(-(leftYAxis - rightXAxis), (leftYAxis + rightXAxis));
    }
}
