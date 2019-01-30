/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Camera extends SubsystemManagerChild {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  SerialPort camera;
  
  String raw;
  int distance;
  int position;
  int height_difference;

  public Camera() {
    super();
    camera = new SerialPort(115200, SerialPort.Port.kUSB1);
  }

  @Override
  public void init() {
    CameraServer.getInstance().startAutomaticCapture();
  }

  @Override
  public void update() {
    try {
      raw = camera.readString();
    } catch (Exception e) {
      raw = ",,";
    }

    distance = Integer.parseInt(raw.split(",")[0]);
    position = Integer.parseInt(raw.split(",")[1]);
    height_difference = Integer.parseInt(raw.split(",")[2]);
    
  }

  @Override
  public void updateSD() {
    SmartDashboard.putString("Raw Camera Data", getRawData());
  }

  public String getRawData() {
    return raw;
  }

  public int getDistance() {
    return distance;
  }

  public int getPosition() {
    return position;
  }

  public int getHeightDifference() {
    return height_difference;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

}
