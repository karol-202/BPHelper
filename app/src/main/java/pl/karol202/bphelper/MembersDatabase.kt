package pl.karol202.bphelper

import android.arch.lifecycle.LiveData
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.*
import android.content.Context
import android.support.annotation.WorkerThread

@Dao
interface MemberDao
{
	@Insert
	fun insert(member: Member)

	@Delete
	fun delete(member: Member)

	@Query("SELECT * FROM members ORDER BY name ASC")
	fun getAll(): LiveData<List<Member>>
}

@Database(entities = [Member::class], version = 3)
abstract class MembersDatabase : RoomDatabase()
{
	private class Callback : RoomDatabase.Callback()
	{
		override fun onOpen(db: SupportSQLiteDatabase)
		{
			super.onOpen(db)

		}
	}

	companion object
	{
		private var database: MembersDatabase? = null

		fun getDatabase(context: Context) = database ?:
			Room.databaseBuilder(context.applicationContext, MembersDatabase::class.java, "MembersDatabase")
				.fallbackToDestructiveMigration() //TODO To be removed
				.addCallback(Callback())
				.build()
				.apply { database = this }
	}

	abstract fun memberDao(): MemberDao
}

class MembersRepository(context: Context)
{
	private val memberDao = MembersDatabase.getDatabase(context).memberDao()

	val allMembers = memberDao.getAll()

	@WorkerThread
	fun addMember(member: Member)
	{
		memberDao.insert(member)
	}

	@WorkerThread
	fun removeMember(member: Member)
	{
		memberDao.delete(member)
	}
}