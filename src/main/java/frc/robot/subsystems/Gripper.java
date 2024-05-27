// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Gripper extends SubsystemBase {
  
  private final DoubleSolenoid m_solenoids = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 4, 5);

  /** Creates a new Gripper. */
  public Gripper() {} 

  @Override
  public void periodic() {}

    public void moverGripper(DoubleSolenoid.Value movimiento) {
      m_solenoids.set(movimiento);
    }

    public void detenerGripper(DoubleSolenoid.Value detenerMovimiento) {
      m_solenoids.set(detenerMovimiento);
    }

}
