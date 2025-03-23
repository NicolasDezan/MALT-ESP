package com.nicolas.maltesp.domain.constants

object MultiplierRange {
    object Steeping {
        const val SUBMERGED_TIME = 1f
        const val WATER_VOLUME = 10f
        const val REST_TIME = 0.1f
        const val CYCLES = 1f
    }

    object Germination {
        const val ROTATION_LEVEL = 1f
        const val TOTAL_TIME = 1f
        const val WATER_VOLUME = 1f
        const val WATER_ADDITION = 1f
    }

    object Kilning {
        const val TEMPERATURE = 1f
        const val TIME = 0.1f
    }
}