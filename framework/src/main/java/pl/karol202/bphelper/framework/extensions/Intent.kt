package pl.karol202.bphelper.framework.extensions

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

fun Intent.set(vararg values: Pair<String, Any?>) = also {
	values.forEach { (key, value) -> this[key] = value }
}

operator fun Intent.set(key: String, value: Any?)
{
	when(value)
	{
		null -> removeExtra(key)
		is Boolean -> putExtra(key, value)
		is Byte -> putExtra(key, value)
		is Char -> putExtra(key, value)
		is Double -> putExtra(key, value)
		is Float -> putExtra(key, value)
		is Int -> putExtra(key, value)
		is Long -> putExtra(key, value)
		is Short -> putExtra(key, value)
		is String -> putExtra(key, value)
		is Bundle -> putExtra(key, value)
		is Parcelable -> putExtra(key, value)
		is Serializable -> putExtra(key, value)
		else -> throw IllegalArgumentException("Type not supported.")
	}
}
