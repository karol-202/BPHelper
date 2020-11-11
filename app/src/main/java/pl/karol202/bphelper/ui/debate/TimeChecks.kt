package pl.karol202.bphelper.ui.debate

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TimeChecks(val speechDuration: Boolean = false,
                      val speechDurationMax: Boolean = false,
                      val poiStart: Boolean = false,
                      val poiEnd: Boolean = false) : Parcelable
