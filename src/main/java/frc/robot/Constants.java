// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */

public final class Constants {

    public static final class ConstantesChasis {
        public static final int idIzqFrontal = 1;
        public static final int idIzqTrasero = 2;
        public static final int idDerTrasero = 8;
        public static final int idDerFrontal = 7;

        public static final double limiteVelAvance = 0.95; // Límite de la velocidad de avance de los motores como un porcenteje (0 - 1)
        public static final double limiteVelGiro = 0.95; // Límite de la velocidad de giro de los motores como un porcenteje (0 - 1)

        /* <<< NO UTILIZAR ESTA SECCIÓN >>> 
         * El autónomo aun no está terminado ni probado,
         * por favor, no modificar nada del autónomo ni usarlo */
        public static final double velAutonoma = 0.4; // Limite de la velocidad de los motores en modo autónomo
        public static final double disAutonomo = 1.5; // Distancia (en metros) a recorrer en modo autónomo

        public static final double radioRuedas = 0.0762; // 3 pulgadas de radio en metros
    }

    public static final class ConstantesMotoresGarra {
       public static final int idGarraIzquierda = 3;
       public static final int idGarraDerecha = 4;

       public static final double velDentroGarra = 0.3;
       public static final double velFueraGarra = -0.6;
    }

    public static final class ConstantesIO {
        public static final int idPuerto = 0;
        public static final int idPuerto_1 = 1; // Puerto detectado por el FRC Driver Station del control a usar
        
        /* Valores obtenidos experimentalmente através del FRC Driver Station */
        
        public static final int botonCuadrado = 1;
        public static final int botonCruz = 2;
        public static final int botonCirculo = 3;
        public static final int botonTriangulo = 4;
        public static final int bumperDerecho = 6;
        public static final int bumperIzquierdo = 5;
        public static final int gatilloIzquierdo = 7;
        public static final int gatilloDerecho = 8;
        public static final int flechaArriba = 0;
        public static final int flechaAbajo = 180;
    }

    public static final class ConstantesGripper {
        /* Valores obtenidos experimentalmente através del FRC Driver Station */
        public static final DoubleSolenoid.Value subir = DoubleSolenoid.Value.kForward;
        public static final DoubleSolenoid.Value bajar = DoubleSolenoid.Value.kReverse;
    }

    public static final class ConstantesElevador {
        /* Valores obtenidos experimentalmente através del FRC Driver Station */
        public static final DoubleSolenoid.Value subir = DoubleSolenoid.Value.kForward;
        public static final DoubleSolenoid.Value bajar = DoubleSolenoid.Value.kReverse;
    }

    public static final class ConstantesPistones {
        /* Valores obtenidos experimentalmente através del FRC Driver Station */
        public static final DoubleSolenoid.Value subir = DoubleSolenoid.Value.kForward;
        public static final DoubleSolenoid.Value bajar = DoubleSolenoid.Value.kReverse;
        public static final DoubleSolenoid.Value frenar = DoubleSolenoid.Value.kOff;
    }
}
