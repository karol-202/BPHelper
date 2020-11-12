package pl.karol202.bphelper.data

import android.content.Context
import androidx.room.*
import pl.karol202.bphelper.data.dao.MemberDao
import pl.karol202.bphelper.data.entity.MemberEntity

private const val DATABASE_NAME = "bphelper.local"
private const val DATABASE_VERSION = 5

@Database(entities = [MemberEntity::class], version = DATABASE_VERSION, exportSchema = false)
abstract class LocalDatabase : RoomDatabase()
{
	companion object
	{
		fun create(context: Context) =
			Room.databaseBuilder(context.applicationContext, LocalDatabase::class.java, DATABASE_NAME)
				.fallbackToDestructiveMigration() //TODO To be removed
				.build()
	}

	abstract fun memberDao(): MemberDao
}


