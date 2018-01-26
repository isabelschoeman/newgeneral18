package org.firstinspires.ftc.teamcode.Autonomous;

/**
 * Created by Laeo on 11/18/17.
 */

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

@Autonomous(name = "BLUE 2", group = "Sensor")
public class Auto_straight_blue extends LinearOpMode {
    ColorSensor colorSensor;
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor FrontLeft = null;
    private DcMotor FrontRight = null;
    private DcMotor BackLeft = null;
    private DcMotor BackRight = null;
    private DcMotor Pulley = null;
    private DcMotor ThiccBoiPlacer = null;
    private Servo Servo1 = null;
    private Servo Servo2 = null;
    private Servo colorServo = null;


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
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
        colorServo = hardwareMap.get(Servo.class, "colorServo");


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
        float hsvValues[] = {0F, 0F, 0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        // sometimes it helps to multiply the raw RGB values with a scale factor
        // to amplify/attentuate the measured values.
        final double SCALE_FACTOR = 255;
        colorServo.setPosition(.90);
        //do stuff here!
        //do stuff here!
        waitForStart();
        while (opModeIsActive()) {
            //clamp on block
            closeGrabber();
            //Lift lift
            Pulley.setPower(.9);
            delay(500);
            Pulley.setPower(0);

            //dropping color servo
            colorServo.setPosition(0);

            //setting color variables
            int red = 0;
            int blue = 0;
            int count = 0;

            //checking jewel color
            for (int i = 0; i < 50; i++) {
                if (colorSensor.red() > colorSensor.blue()) {
                    red++;
                }
                if (colorSensor.red() < colorSensor.blue()) {
                    blue++;
                }
                telemetry.update();
            }

            //writing values to the phone
            telemetry.addData("Clear", colorSensor.alpha());
            telemetry.addData("Red  ", colorSensor.red());
            telemetry.addData("Green", colorSensor.green());
            telemetry.addData("Blue ", colorSensor.blue());
            telemetry.addData("Hue", hsvValues[0]);
            telemetry.addData("RED", red);
            telemetry.addData("BLUE", blue);

            double jewelturntime = getRuntime();

            //if the jewel is red
            if (red > blue) {
                telemetry.addData("Red Wins!", colorSensor.red());
                telemetry.update();
                strafeLeft(.4,250);
                colorServo.setPosition(0.9);
                delay(500);;
                strafeLeft(.4,1650);
                delay(1000);
                moveForward(.4, 500);
                delay(500);
                turnLeft(.6,500);
                delay(500);
                moveForward(.6, 750);
                Pulley.setPower(-.9);
                delay(750);
                Pulley.setPower(0);
                openGrabber();
                delay(500);
                moveBackward(.4,250);
                delay(250);
                moveForward(.4, 350);
                delay(500);
                moveBackward(.4,250);
            }
            //if the jewel is blue
            else {
                telemetry.addData("Blue Wins!", colorSensor.red());
                telemetry.update();
                turnLeft(.4, 100);
                delay(100);
                colorServo.setPosition(0.95);
                delay(100);
                turnRight(.4, 100);
                delay(100);
                strafeLeft(.4, 2000);
                //strafeLeft(.4, );
                delay(1000);
                turnRight(.4,100);
                moveForward(.4, 700);
                delay(500);
                turnLeft(.6,500);
                delay(500);
                moveForward(.6, 500);
                Pulley.setPower(-.9);
                delay(750);
                Pulley.setPower(0);
                openGrabber();
                delay(500);
                moveBackward(.4,250);
                delay(250);
                moveForward(.4, 350);
                delay(500);
                moveBackward(.4,250);
            }
            break;
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
        FrontRight.setPower(-power);
        BackLeft.setPower(power);
        BackRight.setPower(-power);
        delay(time);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
    }
    public void turnLeft(double power, int time){
        FrontLeft.setPower(-power);
        FrontRight.setPower(power);
        BackLeft.setPower(-power);
        BackRight.setPower(power);
        delay(time);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);
    }

}
