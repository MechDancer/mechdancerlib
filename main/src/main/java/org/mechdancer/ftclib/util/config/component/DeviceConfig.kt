package org.mechdancer.ftclib.util.config.component

sealed class DeviceConfig(val name: String, val port: Int) : HardwareConfigDsl {

    //==============
    // Matrix 电机
    //==============

    class Matrix12vMotorConfig(name: String, port: Int) : DeviceConfig(name, port)
    class MatrixLegacyMotorConfig(name: String, port: Int) : DeviceConfig(name, port)

    //==============
    // 舵机
    //==============

    class ServoConfig(name: String, port: Int) : DeviceConfig(name, port)
    class ContinuousRotationServoConfig(name: String, port: Int) : DeviceConfig(name, port)
    class RevBlinkinLedDriverConfig(name: String, port: Int) : DeviceConfig(name, port)


    //==============
    // NeverRest 电机
    //==============
    class NeveRest3dot7v1GearmotorConfig(name: String, port: Int) : DeviceConfig(name, port)

    class NeveRest40GearmotorConfig(name: String, port: Int) : DeviceConfig(name, port)
    class NeveRest60GearmotorConfig(name: String, port: Int) : DeviceConfig(name, port)

    //==============
    // Rev 电机
    //==============
    class RevRobotics20HDHexMotorConfig(name: String, port: Int) : DeviceConfig(name, port)

    class RevRobotics40HDHexMotorConfig(name: String, port: Int) : DeviceConfig(name, port)
    class RevRoboticsCoreHexMotorConfig(name: String, port: Int) : DeviceConfig(name, port)

    //==============
    // Digital
    //==============
    class DigitalDeviceConfig(name: String, port: Int) : DeviceConfig(name, port)

    class LedConfig(name: String, port: Int) : DeviceConfig(name, port)
    class RevTouchSensorConfig(name: String, port: Int) : DeviceConfig(name, port)

    //==============
    // Analog
    //==============
    class AnalogInputConfig(name: String, port: Int) : DeviceConfig(name, port)

    class OpticalDistanceSensorConfig(name: String, port: Int) : DeviceConfig(name, port)
    class ModernRoboticsAnalogTouchSensorConfig(name: String, port: Int) : DeviceConfig(name, port)


    //==============
    // I2C
    //==============
    class LynxEmbeddedImuConfig(name: String, val bus: Int, port: Int) : DeviceConfig(name, port)

    class AdafruitColorSensorConfig(name: String, port: Int) : DeviceConfig(name, port)
    class AdafruitBNO055IMUConfig(name: String, port: Int) : DeviceConfig(name, port)
    class ColorSensorConfig(name: String, port: Int) : DeviceConfig(name, port)
    class ModernRoboticsI2cCompassSensorConfig(name: String, port: Int) : DeviceConfig(name, port)
    class GyroConfig(name: String, port: Int) : DeviceConfig(name, port)
    class IrSeekerV3Config(name: String, port: Int) : DeviceConfig(name, port)
    class ModernRoboticsI2cRangeSensorConfig(name: String, port: Int) : DeviceConfig(name, port)
    class REV_VL53L0X_RANGE_SENSORConfig(name: String, port: Int) : DeviceConfig(name, port)
    class LynxColorSensorConfig(name: String, port: Int) : DeviceConfig(name, port)
    class KauaiLabsNavxMicroConfig(name: String, port: Int) : DeviceConfig(name, port)

    private val builder = StringBuilder()

    override fun start() {
        builder.appendln(when (this) {
            is LynxEmbeddedImuConfig          -> """<LynxEmbeddedImu name="$name" bus="$bus" port="$port"/>"""
            is NeveRest3dot7v1GearmotorConfig -> """"<NeveRest3.7v1Gearmotor name="$name" port="$port"/>"""
            else                              -> "<${this::class.java.simpleName.substringBefore("Config")}" +
                """ name="$name" port="$port"/>"""
        })
    }

    override fun build(): String = builder.toString()
}