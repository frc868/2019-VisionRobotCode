package frc.robot;

public class Helper {
    public static double bound(double input, double lower, double upper) {
        if (input < lower) {
            return lower;
        } else if (input > upper) {
            return upper;
        } else {
            return input;
        }
    }

    public static double bound(double input) {
        return bound(input, -1, 1);
    }

    public static double deadzone(double input, double lower, double upper) {
        if (input <= upper && input >= lower) {
            return 0;
        } else {
            return input;
        }
    }
}