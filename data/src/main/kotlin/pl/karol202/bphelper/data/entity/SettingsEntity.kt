package pl.karol202.bphelper.data.entity

import pl.karol202.bphelper.domain.model.Settings

class SettingsEntity

fun Settings.toEntity() = SettingsEntity()
fun SettingsEntity.toModel() = Settings()
