// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Gripper;

public class OpenGripper extends CommandBase {
  
  private final Gripper s_gripper;
  private final DoubleSolenoid.Value movimiento;

  /** Creates a new Gripper. */
  public OpenGripper(Gripper s_gripper, DoubleSolenoid.Value movimiento) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.s_gripper = s_gripper;
    this.movimiento = movimiento;
    addRequirements(s_gripper);
  }

  // Called when the command is initially scheduled.
  @Override
  public void execute() {
    s_gripper.moverGripper(movimiento);
  }

  @Override
  public void end(boolean interrupted) {
    s_gripper.moverGripper(DoubleSolenoid.Value.kOff);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
