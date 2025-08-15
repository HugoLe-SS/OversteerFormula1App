package com.hugo.result.presentation.screens

sealed class DriverStatus {
    data object Finished : DriverStatus()
    data object Lapped : DriverStatus()
    data object Retired : DriverStatus()
    data object Disqualified : DriverStatus()
    data object DidNotStart : DriverStatus()
    data object DidNotQualify : DriverStatus()
    data object Unknown : DriverStatus() // This is your fallback

    companion object {
        fun fromString(statusString: String?): DriverStatus {
            return when (statusString?.lowercase()?.trim()) {
                "finished" -> Finished
                "lapped" -> Lapped
                "retired", "dnf" -> Retired
                "disqualified", "dsq" -> Disqualified
                "did not start", "dns" -> DidNotStart
                "did not qualify", "dnq" -> DidNotQualify
                null -> Unknown
                else -> Unknown
            }
        }
    }
}