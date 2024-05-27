// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Elevador;

public class MoverElevador extends CommandBase {
  
  private final Elevador s_elevador;
  private final DoubleSolenoid.Value movimiento;

  /** Creates a new Gripper. */
  public MoverElevador(Elevador s_elevador, DoubleSolenoid.Value movimiento) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.s_elevador = s_elevador;
    this.movimiento = movimiento;
    addRequirements(s_elevador);
  }

  // Called when the command is initially scheduled.
  @Override
  public void execute() {
    s_elevador.moverElevador(movimiento);
  }

  @Override
  public void end(boolean interrupted) {
    s_elevador.moverElevador(DoubleSolenoid.Value.kOff);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
