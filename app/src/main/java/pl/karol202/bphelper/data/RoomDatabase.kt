package pl.karol202.bphelper.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import pl.karol202.bphelper.data.dao.MemberDao
import pl.karol202.bphelper.data.entity.MemberEntity

@Database(entities = [MemberEntity::class], version = 4, exportSchema = false)
abstract class MembersDatabase : RoomDatabase()
{
	companion object
	{
		private var database: MembersDatabase? = null

		fun getDatabase(context: Context) = database ?:
			Room.databaseBuilder(context.applicationContext, MembersDatabase::class.java, "MembersDatabase")
				.fallbackToDestructiveMigration() //TODO To be removed
				.build()
				.apply { database = this }
	}

	abstract fun memberDao(): MemberDao
}


