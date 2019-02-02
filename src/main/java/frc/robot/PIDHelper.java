package frc.robot;

import java.util.concurrent.Callable;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class PIDHelper {
    public static class PIDHelperOutput implements PIDOutput {
        private double output; 

        @Override
        public void pidWrite(double output) {
            this.output = output;
        }

        public double getOutput() {
            return this.getOutput();
        }
    }

    public static class PIDHelperSource implements PIDSource { 
        Callable<Double> inputFunction;

        public PIDHelperSource(Callable<Double> inputFunction) {
            this.inputFunction = inputFunction;
        }

        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {
            
        }
    
        @Override
        public double pidGet() {
            try {
                return inputFunction.call();
            } catch (Exception e) {
                return 0;
            }
        }
    
        @Override
        public PIDSourceType getPIDSourceType() {
            return null;
        }
    }
}