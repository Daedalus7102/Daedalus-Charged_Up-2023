// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.Constants.ConstantesChasis;

/* Clases de los SparkMax */
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/* Clases de funciones del subsistema */
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Chasis extends SubsystemBase {

  /* Controladores ---> SparkMax por red CAN */
  private final CANSparkMax m_IzqFrontal = new CANSparkMax(ConstantesChasis.idIzqFrontal, MotorType.kBrushless);
  private final CANSparkMax m_IzqTrasero = new CANSparkMax(ConstantesChasis.idIzqTrasero, MotorType.kBrushless);
  private final CANSparkMax m_DerTrasero = new CANSparkMax(ConstantesChasis.idDerTrasero, MotorType.kBrushless);
  private final CANSparkMax m_DerFrontal = new CANSparkMax(ConstantesChasis.idDerFrontal, MotorType.kBrushless);

  /* Encoders de los controladores */
  private final RelativeEncoder e_IzqFrontal = m_IzqFrontal.getEncoder();
  private final RelativeEncoder e_izqTrasero = m_IzqTrasero.getEncoder();
  private final RelativeEncoder e_DerTrasero = m_DerTrasero.getEncoder();
  private final RelativeEncoder e_DerFrontal = m_DerFrontal.getEncoder();

  /*
   * Constructor/Configurador del subsistema que permite hacer modificaciones a
   * atributos del susbsistema
   */
  public Chasis() {
    manejoChasis.setDeadband(0.05);
    m_DerFrontal.setInverted(true);
    m_DerTrasero.setInverted(true);
  }

  /* Grupos de motores (Izq - Der) para asignarlos al manejo diferencial */
  private final MotorControllerGroup ladoIzquierdo = new MotorControllerGroup(m_IzqFrontal, m_IzqTrasero);
  private final MotorControllerGroup ladoDerecho = new MotorControllerGroup(m_DerFrontal, m_DerTrasero);

  /*
   * Manejo diferencial que detecta y automáticamente asigna la velociad a los
   * motores del chasis
   */
  private final DifferentialDrive manejoChasis = new DifferentialDrive(ladoIzquierdo, ladoDerecho);

  /*
   * Factor de conversión para facilitar los cálculos de la distancia desplazada,
   * registrada por los encoders
   */
  private final double factorConversion = (2 * Math.PI) * ConstantesChasis.radioRuedas;

  /*
   * Obtiene el promedio de la distancia recorrida en función de lo que se
   * registre en los encoders del chasis
   */
  public double promedioEncoder(double enc1, double enc2, double enc3, double enc4) {
    return (enc1 + enc2 + enc3 + enc4) / 4;
  }

  @Override
  public void periodic() {
    /*
     * Obtiene la posición de los encoders y la muestra en el ShuffleBoard, la
     * unidad mostrada es metros
     */
    double p_EncIzqFrontal = e_IzqFrontal.getPosition() * factorConversion;
    double p_EncIzqTrasero = e_izqTrasero.getPosition() * factorConversion;
    double p_EncDerTrasero = e_DerTrasero.getPosition() * factorConversion;
    double p_EncDerFrontal = e_DerFrontal.getPosition() * factorConversion;
    SmartDashboard.putNumber("Encoder CHASIS",
        promedioEncoder(p_EncIzqFrontal, p_EncIzqTrasero, p_EncDerFrontal, p_EncDerTrasero));
  }

  /*
   * Método del subsistema que permite el movimiento del chasis a través de los
   * datos de entrada del control usado
   */
  public void moverChasis(double stickIzquierdo, double stickDerecho) {
    manejoChasis.arcadeDrive(stickIzquierdo * ConstantesChasis.limiteVelAvance,
        -stickDerecho * ConstantesChasis.limiteVelGiro);
  }

  /* Método del subistema que mueve el chasis en el modo autónomo */
  public void autonomo(double velIzquierda, double velDerecha) {
    ladoIzquierdo.set(velIzquierda);
    ladoDerecho.set(velDerecha);
  }
}
