package pl.karol202.bphelper.viewmodel.members

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import pl.karol202.bphelper.data.MembersRepository

class MembersViewModelImpl(application: Application) : AndroidViewModel(application)
{
	private val job = Job()
	private val coroutineContext = job + Dispatchers.Main
	private val coroutineScope = CoroutineScope(coroutineContext)

	private val repository = MembersRepository(application)

	val allMembers = repository.allMembers

	override fun onCleared()
	{
		super.onCleared()
		job.cancel()
	}

	fun addMember(member: MemberEntity) = coroutineScope.launch(Dispatchers.IO) { repository.addMember(member) }

	fun updateMember(member: MemberEntity) = coroutineScope.launch(Dispatchers.IO) { repository.updateMember(member) }

	fun removeMember(member: MemberEntity) = coroutineScope.launch(Dispatchers.IO) { repository.removeMember(member) }
}
