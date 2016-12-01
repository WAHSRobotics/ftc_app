    package org.firstinspires.ftc.teamcode;

    import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
    import com.qualcomm.robotcore.eventloop.opmode.OpMode;

    @TeleOp(name = "Test TeleOp")
    public class TestTeleOp extends OpMode {


        private LSRobit robot = new LSRobit();

        @Override
        public void init() {
            robot.init(hardwareMap);
            robot.Arm1.setPosition(80/180);

            robot.leftfront.setPower(0);
            robot.rightfront.setPower(0);
            robot.leftback.setPower(0);
            robot.rightback.setPower(0);
        }

        public double scale(double inPower) {
            return inPower; //todo: make a real scale method
        }
//memes
        public void loop() {
            double y = gamepad1.right_stick_y;
            double x = gamepad1.right_stick_x;
            double z = 0.0;

            if (gamepad1.right_bumper) {
                robot.Arm1.setPosition(10/180);
            } else {
                robot.Arm1.setPosition(80/180);
            }

            if (gamepad1.left_trigger > 0.0) {
                z = gamepad1.left_trigger;
            } else if (gamepad1.right_trigger > 0.0) {
                z = -gamepad1.right_trigger;
            }

            robot.rightfront.setPower(scale(+ y - x + z));
            robot.leftfront.setPower(scale(- y - x + z));
            robot.rightback.setPower(scale(+ y + x + z));
            robot.leftback.setPower(scale(- y + x + z));
        }

    }