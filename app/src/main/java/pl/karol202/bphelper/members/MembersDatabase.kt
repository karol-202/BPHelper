package pl.karol202.bphelper.members

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Member.TABLE)
data class Member(@PrimaryKey @ColumnInfo(name = COLUMN_NAME) val name: String,
                  @ColumnInfo(name = COLUMN_PRESENT) var present: Boolean = false,
                  @ColumnInfo(name = COLUMN_IRONMAN) var ironman: Boolean = false) : Parcelable
{
	companion object
	{
		const val TABLE = "members"
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
	private class Callback : RoomDatabase.Callback()
	{
		override fun onOpen(db: SupportSQLiteDatabase)
		{
			super.onOpen(db)
			db.execSQL("UPDATE ${Member.TABLE} SET ${Member.COLUMN_PRESENT} = 0, ${Member.COLUMN_IRONMAN} = 0")
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
	private val memberDao: MemberDao = MembersDatabase.getDatabase(
		context
	).memberDao()

	val allMembers = memberDao.getAll()

	fun addMember(member: Member)
	{
		memberDao.insert(member)
	}

	fun updateMember(member: Member)
	{
		memberDao.update(member)
	}

	fun removeMember(member: Member)
	{
		memberDao.delete(member)
	}
}