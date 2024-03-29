package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.FollowVision;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.SubsystemManager;
import frc.robot.subsystems.UltrasonicSensor;



public class Robot extends TimedRobot {
  public static OI m_oi = new OI();
  public static Drivetrain drivetrain = new Drivetrain();
  public static Camera camera = new Camera();
  public static UltrasonicSensor ultrasonic = new UltrasonicSensor();

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  @Override
  public void robotInit() {
    SubsystemManager.init();
    SubsystemManager.initSD();

    SmartDashboard.putNumber("k_dist",   FollowVision.k_dist);
    SmartDashboard.putNumber("k_pos",    FollowVision.k_pos);
    SmartDashboard.putNumber("k_hratio", FollowVision.k_hratio);
  }

  @Override
  public void robotPeriodic() {
    SubsystemManager.update();
    SubsystemManager.updateSD();
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
    m_oi.init();
  }

  @Override
  public void teleopPeriodic() {
    m_oi.update();
    Scheduler.getInstance().run();
  }
}
