package pl.karol202.bphelper.framework.sharedprefs

import com.tfcporciuncula.flow.FlowSharedPreferences
import kotlinx.coroutines.flow.map
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class FlowSharedPreference<T>(private val flowSharedPreferences: FlowSharedPreferences,
                              private val serializer: KSerializer<T>,
                              private val keyName: String)
{
	fun get() = flowSharedPreferences.getString(keyName)
			.asFlow()
			.map { if(it.isNotBlank()) Json.decodeFromString(serializer, it) else null }

	suspend fun set(value: T)
	{
		val serialized = Json.encodeToString(serializer, value)
		flowSharedPreferences.getString(keyName).setAndCommit(serialized)
	}
}
