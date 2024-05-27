// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

 // Constantes contenidas dentro del archivo Constants.java 

import frc.robot.Constants.ConstantesIO;
import frc.robot.Constants.ConstantesGripper;
import frc.robot.Constants.ConstantesElevador;
import frc.robot.Constants.ConstantesPistones;
import frc.robot.Constants.ConstantesMotoresGarra;

/* Clases usadas para el control --> Control PS4 */
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

// Subsistemas del robot 
import frc.robot.subsystems.Chasis;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Elevador;
import frc.robot.subsystems.Pistones;
import frc.robot.subsystems.Garra;

/* Comandos utilizados en el teleoperado */
import frc.robot.commands.ManejoArcade;
import frc.robot.commands.OpenGripper;
import frc.robot.commands.MoverElevador;
import frc.robot.commands.MoverPistones;
import frc.robot.commands.MoverGarra;

public class RobotContainer {

  /* Se declaran y crean los objetos correspondientes a cada subsistema*/ 
  private final Chasis s_chasis = new Chasis();
  private final Gripper s_gripper = new Gripper();
  private final Elevador s_elevador = new Elevador();
  private final Pistones s_pistones = new Pistones();
  private final Garra s_garra = new Garra();

  /* Se declara el control a utilizar y se crea el objeto */
  private final PS4Controller control = new PS4Controller(ConstantesIO.idPuerto);
  private final Joystick c_control = new Joystick(ConstantesIO.idPuerto);
  private final PS4Controller c_control_1 = new PS4Controller(ConstantesIO.idPuerto_1);
  private final Joystick control_1 = new Joystick(ConstantesIO.idPuerto_1);
  private final GenericHID m_stick = new GenericHID(ConstantesIO.idPuerto);
  /*
   * Se declara un comando por defecto --> Siempre estará al pendiente de ser
   * utilizado
   */
  public RobotContainer() {
    s_chasis.setDefaultCommand(new ManejoArcade(s_chasis,
        () -> c_control_1.getLeftY(),
        () -> c_control_1.getRightX()));
    configureButtonBindings();
  }

  public Chasis getChasis(){
    return s_chasis;
  }

  public Gripper getGripper(){
    return s_gripper;
  }
  
  public Garra getGarra(){
    return s_garra;
  }

  public Pistones getPistones(){
      return s_pistones;
  }

  public Elevador getElevador(){
      return s_elevador;
  }

  private void configureButtonBindings() {
    /*
     * Se declara el botón que accionará cada comando que se utilizará en el
     * teleoperado
    */

    new JoystickButton(control, ConstantesIO.botonTriangulo).whileActiveOnce(new MoverElevador(s_elevador, ConstantesElevador.subir));
    new JoystickButton(control, ConstantesIO.botonCruz).whileActiveOnce(new MoverElevador(s_elevador, ConstantesElevador.bajar));

    new POVButton(m_stick, ConstantesIO.flechaArriba).whileActiveOnce(new MoverPistones(s_pistones, ConstantesPistones.subir));
    new POVButton(m_stick, ConstantesIO.flechaAbajo).whileActiveOnce(new MoverPistones(s_pistones, ConstantesPistones.bajar));
    
    new JoystickButton(control, ConstantesIO.bumperDerecho).whileActiveOnce(new OpenGripper(s_gripper, ConstantesGripper.subir));
    new JoystickButton(control, ConstantesIO.bumperIzquierdo).whileActiveOnce(new OpenGripper(s_gripper, ConstantesGripper.bajar));

    new JoystickButton(control, ConstantesIO.gatilloIzquierdo).whileActiveOnce(new MoverGarra(s_garra, ConstantesMotoresGarra.velDentroGarra));
    new JoystickButton(control, ConstantesIO.gatilloDerecho).whileActiveOnce(new MoverGarra(s_garra, ConstantesMotoresGarra.velFueraGarra));
  }
}
