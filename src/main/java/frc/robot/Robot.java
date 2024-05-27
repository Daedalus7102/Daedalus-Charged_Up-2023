// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;

import frc.robot.subsystems.Chasis;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Elevador;
import frc.robot.subsystems.Pistones;
import frc.robot.subsystems.Garra;

import frc.robot.commands.OpenGripper;
import frc.robot.commands.MoverGarra;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;  

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.Constants.ConstantesMotoresGarra;
import frc.robot.Constants.ConstantesGripper;
import frc.robot.Constants.ConstantesPistones;

import java.lang.Math;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  AHRS ahrs;
  Joystick stick;

  Chasis s_chasis;
  Gripper s_gripper;
  Garra s_garra;
  Elevador s_elevador;
  Pistones s_pistones;

  private Command m_autonomousCommand;
  private final Timer temporizador = new Timer();

  private static final String kCenterBalance = "Center Balance";
  private static final String kSideAuto = "Side";
  private static final String kCenterOutBalance = "Center Out & Balance";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private RobotContainer m_robotContainer;

  String AutonomoSelected = "";

  // Autonomous config
  Boolean necesitaBajar = true;
  Double initialAngle = 0.0;
  Double turnHalfCircle = 180.0;
  Double finalAngle = 0.0;
  Double maxTurnSpeed = 0.35;
  Double turnThreshold = 3.0;

  Boolean needsAutoBalance = false;
  Double maxAngleAutoBalance = 15.0;
  
  Double rampaAngle  = 15.0; // ángulo para detectar la rampa
  Double findRampaSpeed = 0.60; // en 0.22 se trababa al subir la rampa
  Double OUT_findRampaSpeed = 0.35; // en 0.22 se trababa al subir la rampa
  Double OUT_REGRESO_findRampaSpeed = 0.25;
  Double balanceSpeed = -0.10; // en -0.12 el robot se balanceaba demasiado rápido

  //////////
  Boolean atravesarRampa = true; // false se queda en la rampa
  Boolean encontrarRampa = true; // No modificar, sive para que el robot sepa que no ha encontrado la rampa

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // NAVX
    try {
      /* Communicate w/navX MXP via the MXP SPI Bus.                                     */
      /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
      /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
      ahrs = new AHRS(SPI.Port.kMXP);
    } catch (RuntimeException ex ) {
      DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
    }

    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    stick = new Joystick(0);

    s_chasis = m_robotContainer.getChasis();
    s_gripper = m_robotContainer.getGripper();
    s_garra = m_robotContainer.getGarra();
    s_pistones = m_robotContainer.getPistones();
    s_elevador = m_robotContainer.getElevador();

    SmartDashboard.putData("Cerrar gripper", new OpenGripper(s_gripper, ConstantesGripper.bajar));
    SmartDashboard.putData("Abrir gripper", new OpenGripper(s_gripper, ConstantesGripper.subir));
    SmartDashboard.putData("Ruedas adentro", new MoverGarra(s_garra, ConstantesMotoresGarra.velDentroGarra));
    SmartDashboard.putData("Ruedas afuera", new MoverGarra(s_garra, ConstantesMotoresGarra.velFueraGarra));

    //m_chooser.setDefaultOption("Side Auto", kSideAuto);
    //m_chooser.addOption("Center auto", kCenterAuto);

    m_chooser.setDefaultOption("Center Balance", kCenterBalance);
    m_chooser.addOption("Side Auto", kSideAuto);
    //m_chooser.addOption("Center Out & Balance", kCenterOutBalance);
    SmartDashboard.putData("Auto choices", m_chooser);

    // Creates UsbCamera and MjpegServer [1] and connects them
    UsbCamera camera = CameraServer.startAutomaticCapture();
    camera.setResolution(18, 14);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    
    ahrs.reset();
    //Double currentAngleRobot = Double.valueOf( Math.abs(ahrs.getRoll()) );
    Double currentAngleRobot = Double.valueOf( ahrs.getRoll() );
    SmartDashboard.putNumber( "ROLL", currentAngleRobot );
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    AutonomoSelected = m_autoSelected;
    System.out.println("Auto selected: " + m_autoSelected);
    
    temporizador.reset();
    temporizador.start();
    ahrs.reset();

    initialAngle = Math.abs(Double.valueOf(ahrs.getYaw())) % 180;
    finalAngle = initialAngle + turnHalfCircle;
    Boolean encontrarRampa = true;
  }

  public void autonomoDisparar(){
    if(temporizador.get() < 0.25){
      s_pistones.moverPistones(ConstantesPistones.bajar);
    }
    else {
      s_pistones.detenerPistones(ConstantesPistones.frenar);
    }

    if(temporizador.get() > 2){
      //Espera el balanceo      
      if(temporizador.get() < 3){
        s_garra.moverGarra(-0.6); 
      }
      else{
        s_garra.moverGarra(0);
      }
    }
  }

  public void autonomoReversa(){
    s_chasis.autonomo(findRampaSpeed, findRampaSpeed);
  }

  public void autonomoAdelante(){
    s_chasis.autonomo(-OUT_REGRESO_findRampaSpeed, -OUT_REGRESO_findRampaSpeed);
  }

  public void autonomoBalancearse(){
    Double currentAngleRobot = Double.valueOf( ahrs.getRoll() );
    Double balanceSpeedDif =  Math.abs(currentAngleRobot)/maxAngleAutoBalance;
    
    if(temporizador.get() < 15){
      if(currentAngleRobot > 3){
        //Hacerse hacía adelante
        s_chasis.autonomo(balanceSpeedDif * balanceSpeed, balanceSpeedDif * balanceSpeed);
      }
      else if(currentAngleRobot < -3){
        s_chasis.autonomo(-balanceSpeedDif * balanceSpeed, -balanceSpeedDif * balanceSpeed);
      }
    }
    else if(Math.abs(currentAngleRobot) < 2){
      s_chasis.autonomo(0, 0);
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    System.out.println(temporizador.get());
    
    SmartDashboard.putBoolean("encontrarRampa", encontrarRampa);
    SmartDashboard.putBoolean("needsAutoBalance", needsAutoBalance);

    Double tiempoDisparador = 3.5;
    Double tiempoAtravesar = 3.2;   //anterior 3.2
    Double tiempoRegresar = 2.5; 

    Double currentAngleRobot = Double.valueOf( Math.abs(ahrs.getRoll()) );

    if(temporizador.get() < tiempoDisparador){
      autonomoDisparar();
    }

    switch (m_autoSelected) {
      case kSideAuto:
      if (temporizador.get() > tiempoDisparador) {
        while(temporizador.get() > 5 && temporizador.get() < 8.2){
          s_chasis.autonomo(.35, .35);
        }
        while(temporizador.get() > 8.2 && temporizador.get() < 9.4){
          s_chasis.autonomo(.31, -.31);
        }
      }
        break;
      case kCenterBalance:
        if (temporizador.get() > tiempoDisparador) {
          if(encontrarRampa){
            autonomoReversa();
          }
  
          if (encontrarRampa && Math.abs(currentAngleRobot) >= rampaAngle){
            encontrarRampa = false;
            needsAutoBalance = true;
          }
          if(!encontrarRampa && needsAutoBalance){
            autonomoBalancearse();
          }
        }
        break;
    }
    Timer.delay(0.01);
  }
  /** This function is called periodically during teleoperated init. */
  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    ahrs.reset();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic(){
    CommandScheduler.getInstance().run();
    SmartDashboard.putNumber(   "IMU_Yaw",              ahrs.getYaw());
  }

  /** This function is called once when test is initialized. */
  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    CommandScheduler.getInstance().run();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
