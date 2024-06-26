// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pistones extends SubsystemBase {
  
  private final DoubleSolenoid m_solenoids = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);

  /** Creates a new Gripper. */
  public Pistones() {} 

  @Override
  public void periodic() {}

    public void moverPistones(DoubleSolenoid.Value movimiento) {
      m_solenoids.set(movimiento);
    }

    public void detenerPistones(DoubleSolenoid.Value detenerMovimiento) {
      m_solenoids.set(detenerMovimiento);
    }

}
