// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevador extends SubsystemBase {
  
  private final DoubleSolenoid m_solenoids = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);

  /** Creates a new Gripper. */
  public Elevador() {} 

  @Override
  public void periodic() {}

    public void moverElevador(DoubleSolenoid.Value movimiento) {
      m_solenoids.set(movimiento);
    }

    public void detenerElevador(DoubleSolenoid.Value detenerMovimiento) {
      m_solenoids.set(detenerMovimiento);
    }

}
