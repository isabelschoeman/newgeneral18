package org.firstinspires.ftc.teamcode.Teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by AnnabelleButtenwieser on 2/17/18.
 */

@TeleOp(name="RKA", group="Iterative Opmode")
public class RKA extends OpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor Lift = null;
    private DcMotor FrontRight = null;
    private DcMotor BackLeft = null;
    private DcMotor BackRight = null;
    private DcMotor Pulley = null;
    private DcMotor NomLeft = null;
    private DcMotor NomRight = null;
    private Servo Servo1 = null;
    private Servo Servo2 = null;
    //private Servo Servo2 = null;
    //private Servo ServoRelicRelease = null;
    //private Servo ServoHand = null;
    //private Servo ServoElbow = null;
    //private DcMotor ThiccBoiRetriever= null;
    private Servo colorServo = null;
    double startTime = runtime.milliseconds();

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).

        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");

        BackRight = hardwareMap.get(DcMotor.class, "BackRight");

        Lift = hardwareMap.get(DcMotor.class, "Lift");

        Servo1 = hardwareMap.get(Servo.class, "Servo1");

        Servo2 = hardwareMap.get(Servo.class, "Servo2");


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery

        BackLeft.setDirection(DcMotor.Direction.REVERSE);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        Lift.setDirection(DcMotor.Direction.FORWARD);



        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }


    @Override
    public void init_loop(){

        Servo1.setPosition(0.9);
        Servo2.setPosition(0.9);

    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */

    public void loop() {
        double threshold = 0.5;
        double slowNumber = 0.75;

        if (gamepad1.right_stick_x < -threshold || gamepad1.right_stick_x > threshold) {
            if(gamepad1.left_bumper){
                //FrontLeft.setPower(-gamepad1.right_stick_x/2);
                //FrontRight.setPower(-gamepad1.right_stick_x/2);
                BackLeft.setPower(gamepad1.right_stick_x/2);
                BackRight.setPower(gamepad1.right_stick_x/2);
            }
            else{
               // FrontLeft.setPower(-gamepad1.right_stick_x*slowNumber);
                //FrontRight.setPower(-gamepad1.right_stick_x*slowNumber);
                BackLeft.setPower(gamepad1.right_stick_x*slowNumber);
                BackRight.setPower(gamepad1.right_stick_x*slowNumber);
            }

        } else if (gamepad1.left_stick_y < -threshold || gamepad1.left_stick_y > threshold || gamepad1.left_stick_x < -threshold || gamepad1.left_stick_x > threshold) {
            double leftPower;
            double rightPower;

            double drive = -gamepad1.left_stick_y;
            double turn = gamepad1.left_stick_x;
            //make sure left and right power are outside thres
            leftPower = Range.clip(drive + turn, -1.0, 1.0);
            rightPower = Range.clip(drive - turn, -1.0, 1.0);

            if (leftPower > threshold || leftPower < -threshold || rightPower < -threshold || rightPower > threshold) {
                if(gamepad1.left_bumper){
                    BackLeft.setPower(leftPower/2);
                    BackRight.setPower(rightPower/2);
                }else{
                    BackLeft.setPower(leftPower*slowNumber);
                    BackRight.setPower(rightPower*slowNumber);
                }

            } else {
                BackLeft.setPower(0);
                BackRight.setPower(0);
                //   double leftPower;
                //   double rightPower;
            }


        }
        // else if (gamepad1.right_stick_y < -threshold || gamepad1.right_stick_y> threshold) {
        //     FrontLeft.setPower(-gamepad1.right_stick_x);
        //    FrontRight.setPower(-gamepad1.right_stick_x);
        //    BackLeft.setPower(-gamepad1.right_stick_x);
        // }
        else {
            BackLeft.setPower(0);
            BackRight.setPower(0);
            //   double leftPower;
            //   double rightPower;
        }



        double liftPower = 5;

        if (gamepad1.a) {
            Lift.setPower(liftPower);

        } else if(gamepad1.b){
            Lift.setPower(-liftPower);
        }
        else {
            Lift.setPower(0);
            //   double leftPower;
            //   double rightPower;
        }



        if(gamepad1.x){
            Servo1.setPosition(0.9);
            Servo2.setPosition(0.2);
        }
        else if(gamepad1.y){
            Servo1.setPosition(0.2);
            Servo2.setPosition(0.9);
        }







        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.update();
    /*
     * Code to run ONCE after the driver hits STOP
     */
    }
}