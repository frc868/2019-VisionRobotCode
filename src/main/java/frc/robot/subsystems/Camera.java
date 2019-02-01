/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Camera extends SubsystemManagerChild {
  private SerialPort port;
  private String raw;
  private double distance, position, height_difference;
  // private final int BUFFER_SIZE = 2;

  // private ArrayList<Double> distanceBuffer, positionBuffer, height_differenceBuffer;

  public Camera() {
    super();
    port = new SerialPort(115200, SerialPort.Port.kUSB1);

    // distanceBuffer = new ArrayList<Double>();
    // positionBuffer = new ArrayList<Double>();
    // distanceBuffer = new ArrayList<Double>();
  }

  @Override
  public void init() {
    CameraServer.getInstance().startAutomaticCapture();
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
          height_difference = Double.parseDouble(dataArray[2]);
            
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
    return raw;
  }

  public double getDistance() {
    return distance;
  }

  public double getPosition() {
    return position - 320;
  }

  public double getHeightDifference() {
    return height_difference;
  }

  public void sendData(String data) {
    port.writeString(data);
  }

  public void switchToVision() {
    sendData("setmapping MJPG 320 240 10 YUYV 640 480 30 TechHOUNDS DeepSpace");
  }

  public void switchToCamera() {
    sendData("setmapping MJPG 320 240 10 YUYV 320 240 10 TechHOUNDS868 Trash2019");
  }
}
