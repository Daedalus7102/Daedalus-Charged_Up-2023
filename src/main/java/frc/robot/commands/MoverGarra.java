// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;

/* Subsistema correspondiente al comando */
import frc.robot.subsystems.Garra;

/* Constantes correspondiente al comando */

public class MoverGarra extends CommandBase {

  /* Variables a declarar dentro del comando */
  private final Garra s_garra;
  private final double s_velocidad;

  /* Constructor del comando y sus atributos */
  public MoverGarra(Garra s_garra, double s_velocidad) {
    this.s_garra = s_garra;
    this.s_velocidad = s_velocidad;
    addRequirements(s_garra);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    /* Utiliza el método creado en el subsistema */
    s_garra.moverGarra(s_velocidad);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    /* Utiliza el método creado en el subsistema */
    s_garra.moverGarra(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
