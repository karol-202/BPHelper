package pl.karol202.bphelper.framework.sharedprefs

import com.tfcporciuncula.flow.FlowSharedPreferences
import kotlinx.coroutines.flow.map
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class FlowSharedPreference<T>(flowSharedPreferences: FlowSharedPreferences,
                              private val serializer: KSerializer<T>,
                              keyName: String)
{
	private val preference = flowSharedPreferences.getString(keyName)

	val asFlow = preference.asFlow().map { it.deserialize() }

	fun get() = preference.get().deserialize()

	private fun String.deserialize() = takeIf { it.isNotBlank() }?.let { Json.decodeFromString(serializer, it) }

	suspend fun set(value: T)
	{
		preference.setAndCommit(Json.encodeToString(serializer, value))
	}
}
