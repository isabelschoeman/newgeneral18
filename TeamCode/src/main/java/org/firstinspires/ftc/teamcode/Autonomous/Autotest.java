package org.firstinspires.ftc.teamcode.Autonomous;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

@Autonomous(name = "timed straight auto", group = "Sensor")
public class Autotest extends LinearOpMode {

    /**
     * Note that the REV Robotics Color-Distance incorporates two sensors into one device.
     * It has a light/distance (range) sensor.  It also has an RGB color sensor.
     * The light/distance sensor saturates at around 2" (5cm).  This means that targets that are 2"
     * or closer will display the same value for distance/light detected.
     * <p>
     * Although you configure a single REV Robotics Color-Distance sensor in your configuration file,
     * you can treat the sensor as two separate sensors that share the same name in your op mode.
     * <p>
     * In this example, we represent the detected color by a hue, saturation, and value color
     * model (see https://en.wikipedia.org/wiki/HSL_and_HSV).  We change the background
     * color of the screen to match the detected color.
     * <p>
     * In this example, we  also use the distance sensor to display the distance
     * to the target object.  Note that the distance sensor saturates at around 2" (5 cm).
     */

    ColorSensor colorSensor;
    DistanceSensor sensorDistance;

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor FrontLeft = null;
    private DcMotor FrontRight = null;
    private DcMotor BackLeft = null;
    private DcMotor BackRight = null;
    private DcMotor Pulley = null;
    private DcMotor ThiccBoiPlacer = null;
    private Servo Servo1 = null;
    private Servo Servo2 = null;


    @Override
    public void runOpMode() {
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        FrontLeft  = hardwareMap.get(DcMotor.class, "FrontLeft");
        BackLeft  = hardwareMap.get(DcMotor.class, "BackLeft");
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        Pulley = hardwareMap.get(DcMotor.class, "Pulley");
        ThiccBoiPlacer = hardwareMap.get(DcMotor.class, "ThiccBoiPlacer");
        Servo1 = hardwareMap.get(Servo.class, "Servo1");
        Servo2 = hardwareMap.get(Servo.class, "Servo2");

        //WheelOne.setDirection(DcMotor.Direction.FORWARD);
        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        FrontLeft.setDirection(DcMotor.Direction.REVERSE);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        Pulley.setDirection(DcMotor.Direction.FORWARD);
        ThiccBoiPlacer.setDirection(DcMotor.Direction.FORWARD);

        //do stuff here!
        waitForStart();
        while (opModeIsActive()) {
            //clamp on block
            closeGrabber();
            //Lift lift
            Pulley.setPower(.9);
            delay(1000);
            Pulley.setPower(0);
            strafeRight(0, 0);
            strafeLeft(0, 0);
            turnRight(0,0);
            turnRight(0,0);
            //drive forward
            moveForward(.3, 750);
            //Lift lift
            Pulley.setPower(-.9);
            delay(500);
            Pulley.setPower(0);
            moveForward(.3, 500);
            //drop block
            openGrabber();
            delay(500);
            moveBackward(.3, 200);
            moveForward(.3, 350);
            moveBackward(.3, 100);
            return;
            //drive backward

        }

    }
    void closeGrabber() {
        Servo1.setPosition(0.6);
        Servo2.setPosition(0.4);
        delay(1000);
    }
    void openGrabber() {
        Servo1.setPosition(0.1);
        Servo2.setPosition(0.8);
    }

   //Put any functions that you want here

    public void delay(int time) {
        double startTime = runtime.milliseconds();
        while (runtime.milliseconds() - startTime < time) {
        }
    }
   public void moveForward(double power, int time) {
       FrontLeft.setPower(power);
       FrontRight.setPower(power);
       BackLeft.setPower(power);
       BackRight.setPower(power);
       delay(time);
       FrontLeft.setPower(0);
       FrontRight.setPower(0);
       BackLeft.setPower(0);
       BackRight.setPower(0);
   }
    public void moveBackward(double power, int time) {
        FrontLeft.setPower(-power);
        FrontRight.setPower(-power);
        BackLeft.setPower(-power);
        BackRight.setPower(-power);
        delay(time);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
    }

   public void strafeRight(double power, int time){
       FrontLeft.setPower(power);
       FrontRight.setPower(-power);
       BackLeft.setPower(-power);
       BackRight.setPower(power);
       delay(time);
       FrontLeft.setPower(0);
       FrontRight.setPower(0);
       BackLeft.setPower(0);
       BackRight.setPower(0);
   }
    public void strafeLeft(double power, int time){
        FrontLeft.setPower(-power);
        FrontRight.setPower(power);
        BackLeft.setPower(power);
        BackRight.setPower(-power);
        delay(time);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
    }
    public void turnRight(double power, int time){
        FrontLeft.setPower(power);
        FrontRight.setPower(power);
        BackLeft.setPower(-power);
        BackRight.setPower(-power);
        delay(time);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
    }
    public void turnLeft(double power, int time){
        FrontLeft.setPower(-power);
        FrontRight.setPower(-power);
        BackLeft.setPower(power);
        BackRight.setPower(power);
        delay(time);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
    }
}