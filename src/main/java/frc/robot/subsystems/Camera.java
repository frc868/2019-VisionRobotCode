/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.SwitchToCamera;
import frc.robot.commands.SwitchToVision;

public class Camera extends SubsystemManagerChild {
  private SerialPort port;
  private UsbCamera  jevois;
  private String     raw;
  private double     distance, position, heightDifference;
  // private final int BUFFER_SIZE = 2;
  
  private final int RES_WIDTH  = 320;
  private final int RES_HEIGHT = 240;
  private final int FPS_VISION = 10;
  private final int FPS_CAMERA = 15;

  // private ArrayList<Double> distanceBuffer, positionBuffer, height_differenceBuffer;

  public Camera() {
    super();
    // following assumes that cam is on port 1 *which is robot-specific*
    port = new SerialPort(115200, SerialPort.Port.kUSB1);

    // distanceBuffer = new ArrayList<Double>();
    // positionBuffer = new ArrayList<Double>();
    // distanceBuffer = new ArrayList<Double>();
  }

  @Override
  public void init() {
    // change the param of startAutomaticCapture to whatever cam you're using
     jevois = CameraServer.getInstance().startAutomaticCapture(); // returns a UsbCamera
     jevois.setResolution(RES_WIDTH, RES_HEIGHT);
     this.switchToVision();
  }

  @Override
  public void update() {
    try {
      String newData = port.readString();
      if (newData != null && !newData.equals("")) {  
        raw = newData;      
        if (hasTarget()) {
          String[] dataArray = raw.split(",");

          distance = Double.parseDouble(dataArray[0]);
          position = Double.parseDouble(dataArray[1]);
          heightDifference = Double.parseDouble(dataArray[2]);
            
          // distanceBuffer.add(distance);
          // positionBuffer.add(position);
          // height_differenceBuffer.add(height_difference);

          // while (distanceBuffer.size() > BUFFER_SIZE) {
          //   distanceBuffer.remove(0);
          //   positionBuffer.remove(0);
          //   height_differenceBuffer.remove(0);
          // }

          // double distanceSum = 0;
          // double positionSum = 0;
          // double height_differenceSum = 0;

          // for (int i = 0; i < distanceBuffer.size(); i++) {
          //   distanceSum += distanceBuffer.get(i);
          //   positionSum += positionBuffer.get(i);
          //   height_differenceSum += height_differenceBuffer.get(i);
          // }

          // this.distance = distanceSum/distanceBuffer.size();
          // this.position = positionSum/positionBuffer.size();
          // this.height_difference = height_differenceSum/height_differenceBuffer.size();

        } else {
          // distanceBuffer.clear();
          // positionBuffer.clear();
          // height_differenceBuffer.clear();
        }
      }
    } catch (Exception e) {}
  }

  @Override 
  public void initSD() {
    SmartDashboard.putData("Switch to Camera", new SwitchToCamera());
    SmartDashboard.putData("Switch to Vision", new SwitchToVision());
  }

  @Override
  public void updateSD() {
    SmartDashboard.putBoolean("Has Target", hasTarget());
    SmartDashboard.putString("Raw Data", getRawData());
    SmartDashboard.putNumber("Distance", getDistance());
    SmartDashboard.putNumber("Position", getPosition());
    SmartDashboard.putNumber("Height Difference", getHeightDifference());
  }

  public boolean hasTarget() {
    return !getRawData().contains(",,");
  }

  public String getRawData() {
    return raw != null ? raw : ",,";
  }

  public double getDistance() {
    return distance;
  }

  public double getPosition() {
    return position - 320;
  }

  public double getHeightDifference() {
    return heightDifference;
  }

  public void sendData(String data) {
    port.writeString(data);
  }

  public void switchToVision() {
    /* SmartDashboard.putString("testing", "switching");
    sendData("setmapping2 MJPG 320 240 10 TechHOUNDS DeepSpace"); */
    jevois.setFPS(FPS_VISION);
  }

  public void switchToCamera() {
    // sendData("setmapping2 MJPG 320 240 10 TechHOUNDS868 Trash2019");
    jevois.setFPS(FPS_CAMERA);
  }
}
