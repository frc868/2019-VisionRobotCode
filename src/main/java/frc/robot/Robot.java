package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.FollowVision;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.UltrasonicSensor;
import frc.robot.subsystems.SubsystemManager;


public class Robot extends TimedRobot {
  public static OI m_oi;
  public static Drivetrain drivetrain;
  public static Camera camera;
  public static UltrasonicSensor ultrasonic;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();


  @Override
  public void robotInit() {
    m_oi = new OI();
    camera = new Camera();
    drivetrain = new Drivetrain();
    ultrasonic = new UltrasonicSensor();

    SubsystemManager.init();
    SubsystemManager.initSD();

    SmartDashboard.putNumber("k_dist", -0.025);
    SmartDashboard.putNumber("k_pos", .02);
    SmartDashboard.putNumber("k_hratio", .005);
  }

  @Override
  public void robotPeriodic() {
    SubsystemManager.update();
    SubsystemManager.updateSD();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    FollowVision fv = new FollowVision();
    fv.start();
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    //System.out.println("no.");
  }

  @Override
  public void teleopPeriodic() {
    m_oi.update();
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }
}
