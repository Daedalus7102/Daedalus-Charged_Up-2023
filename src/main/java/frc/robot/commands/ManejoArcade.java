// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;

/* Subsistema correspondiente al comando */
import frc.robot.subsystems.Chasis;

/* Clase Supplier */
import java.util.function.Supplier;

public class ManejoArcade extends CommandBase {

  /* Variables a declarar dentro del comando */
  private final Chasis s_chasis;
  private final Supplier<Double> funcVelocidad, funcGiro;

  /* Constructor del comando y sus atributos */
  public ManejoArcade(Chasis s_chasis, Supplier<Double> funcVelocidad, Supplier<Double> funcGiro) {
    this.funcVelocidad = funcVelocidad;
    this.funcGiro = funcGiro;
    this.s_chasis = s_chasis;
    addRequirements(s_chasis);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}
  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    /* Recupera los valores que se reciben de los sticks del control en tiempo real */
    double stickIzquierdoTR = funcVelocidad.get();
    double stickDerechoTR = funcGiro.get();

    /* Utiliza el método creado en el subsistema */
    s_chasis.moverChasis(stickIzquierdoTR, stickDerechoTR);
  }
  
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
