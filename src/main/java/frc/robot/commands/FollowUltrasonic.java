package frc.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Helper;
import frc.robot.Robot;

public class FollowUltrasonic extends PIDCommand {
    private static final double P = 0.025, I = 0.0, D = 0.0;

    private int counts = 0;
    private final int COUNTS_NEEDED = 5;
    private final int TARGET = 0;


    public FollowUltrasonic() {
        super(P, I, D);
        requires(Robot.ultrasonic);
        requires(Robot.drivetrain);
    }

    @Override
    protected double returnPIDInput() {
        return TARGET - Robot.ultrasonic.getDistance();
    }

    @Override
    protected void usePIDOutput(double output) {
        SmartDashboard.putNumber("UltraOut", output);
        output = Helper.bound(output, -.3, .3);
        Robot.drivetrain.set(output, -output);
    }

    @Override
    protected boolean isFinished() {
        return false;
        // return counts > COUNTS_NEEDED;
    }
}