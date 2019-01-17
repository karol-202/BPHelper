package pl.karol202.bphelper

import android.arch.lifecycle.LiveData
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.*
import android.content.Context
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Parcelize
@Entity(tableName = Member.TABLE_NAME)
data class Member(@PrimaryKey @ColumnInfo(name = COLUMN_NAME) val name: String,
                              @ColumnInfo(name = COLUMN_PRESENT) var present: Boolean = false,
                              @ColumnInfo(name = COLUMN_IRONMAN) var ironman: Boolean = false) : Parcelable
{
	companion object
	{
		const val TABLE_NAME = "members"
		const val COLUMN_NAME = "name"
		const val COLUMN_PRESENT = "present"
		const val COLUMN_IRONMAN = "ironman"
	}

	val occupiedSeats: Int
		get() = if(ironman) 2 else 1
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

@Database(entities = [Member::class], version = 4, exportSchema = false)
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

	fun updateMember(member: Member) = scope.launch(Dispatchers.IO) {
		memberDao.update(member)
	}

	fun removeMember(member: Member) = scope.launch(Dispatchers.IO) {
		memberDao.delete(member)
	}

	private fun onOpen(database: SupportSQLiteDatabase) = scope.launch(Dispatchers.IO) {
		database.execSQL("UPDATE ${Member.TABLE_NAME} " +
				              "SET ${Member.COLUMN_PRESENT} = 0, ${Member.COLUMN_IRONMAN} = 0")
	}
}