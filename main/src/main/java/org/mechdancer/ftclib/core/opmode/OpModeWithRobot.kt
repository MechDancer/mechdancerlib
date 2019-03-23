package org.mechdancer.ftclib.core.opmode

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.mechdancer.ftclib.core.structure.composite.Robot
import org.mechdancer.ftclib.util.SmartLogger

@Disabled
abstract class OpModeWithRobot<T : Robot> : OpMode(), SmartLogger