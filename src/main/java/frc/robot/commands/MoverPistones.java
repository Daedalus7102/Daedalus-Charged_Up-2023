// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Pistones;

public class MoverPistones extends CommandBase {
  
  private final Pistones s_pistones;
  private final DoubleSolenoid.Value movimiento;

  /** Creates a new Gripper. */
  public MoverPistones(Pistones s_pistones, DoubleSolenoid.Value movimiento) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.s_pistones = s_pistones;
    this.movimiento = movimiento;
    addRequirements(s_pistones);
  }

  // Called when the command is initially scheduled.
  @Override
  public void execute() {
    s_pistones.moverPistones(movimiento);
  }

  @Override
  public void end(boolean interrupted) {
    s_pistones.detenerPistones(DoubleSolenoid.Value.kOff);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
