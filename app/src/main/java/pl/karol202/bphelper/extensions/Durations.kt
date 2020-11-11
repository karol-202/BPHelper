package pl.karol202.bphelper.extensions

import android.content.Context
import pl.karol202.bphelper.R
import kotlin.time.Duration

fun Duration.format(context: Context) = context.getString(R.string.time_format, inMinutes.toInt(), inSeconds.toInt() % 60)
