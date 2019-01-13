package pl.karol202.bphelper

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "members")
data class Member(@PrimaryKey val name: String,
                  var present: Boolean = false)