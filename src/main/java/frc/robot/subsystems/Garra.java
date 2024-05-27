// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ConstantesMotoresGarra;

public class Garra extends SubsystemBase {
  
  private final CANSparkMax garraIzquierda = new CANSparkMax(ConstantesMotoresGarra.idGarraIzquierda, MotorType.kBrushless);//3
  private final CANSparkMax garraDerecha = new CANSparkMax(ConstantesMotoresGarra.idGarraDerecha, MotorType.kBrushless);//4

  public Garra() {
      garraIzquierda.setInverted(false);
      garraDerecha.setInverted(false);
  }

  @Override
  public void periodic() {}

  public void moverGarra(double velocidad) {
    garraIzquierda.set(velocidad);
    garraDerecha.set(-velocidad);
  }
}
