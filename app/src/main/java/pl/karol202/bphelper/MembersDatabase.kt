package pl.karol202.bphelper

import android.arch.lifecycle.LiveData
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.*
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Entity(tableName = Member.TABLE_NAME)
data class Member(@PrimaryKey @ColumnInfo(name = COLUMN_NAME) val name: String,
                              @ColumnInfo(name = COLUMN_PRESENT) var present: Boolean = false)
{
	companion object
	{
		const val TABLE_NAME = "members"
		const val COLUMN_NAME = "name"
		const val COLUMN_PRESENT = "present"
	}
}

@Dao
interface MemberDao
{
	@Insert
	fun insert(vararg member: Member)

	@Update
	fun update(vararg member: Member)

	@Delete
	fun delete(vararg member: Member)

	@Query("SELECT * FROM members ORDER BY name ASC")
	fun getAll(): LiveData<List<Member>>
}

@Database(entities = [Member::class], version = 3, exportSchema = false)
abstract class MembersDatabase : RoomDatabase()
{
	private class Callback(private val openCallback: (SupportSQLiteDatabase) -> Unit) : RoomDatabase.Callback()
	{
		override fun onOpen(db: SupportSQLiteDatabase)
		{
			super.onOpen(db)
			openCallback(db)
		}
	}

	companion object
	{
		private var database: MembersDatabase? = null

		fun getDatabase(context: Context, onOpen: (SupportSQLiteDatabase) -> Unit) = database ?:
			Room.databaseBuilder(context.applicationContext, MembersDatabase::class.java, "MembersDatabase")
				.fallbackToDestructiveMigration() //TODO To be removed
				.addCallback(Callback(onOpen))
				.build()
				.apply { database = this }
	}

	abstract fun memberDao(): MemberDao
}

class MembersRepository(context: Context,
                        private val scope: CoroutineScope)
{
	private val memberDao: MemberDao = MembersDatabase.getDatabase(context) { db ->
		onOpen(db)
	}.memberDao()

	val allMembers = memberDao.getAll()

	fun addMember(member: Member) = scope.launch(Dispatchers.IO) {
		memberDao.insert(member)
	}

	fun removeMember(member: Member) = scope.launch(Dispatchers.IO) {
		memberDao.delete(member)
	}

	private fun onOpen(database: SupportSQLiteDatabase) = scope.launch(Dispatchers.IO) {
		database.execSQL("UPDATE members SET present = 0")
	}
}