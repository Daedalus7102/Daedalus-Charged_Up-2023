// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ConstantesChasis;
import frc.robot.subsystems.Chasis;

public class MoverChasisAdelante extends CommandBase {

  private final Chasis s_chasis;
  private final double distancia;
  private double avanceEncoder;

  public MoverChasisAdelante(Chasis s_chasis, double distancia) {
    this.distancia = distancia;
    this.s_chasis = s_chasis;
    addRequirements(s_chasis);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // avanceEncoder = s_chasis.promedioEncoder() + distancia;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    s_chasis.autonomo(ConstantesChasis.velAutonoma, ConstantesChasis.velAutonoma);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    s_chasis.autonomo(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
    // if (s_chasis.promedioEncoder() > avanceEncoder)
    //   return true;
    // else
    //   return false;
  }
}
