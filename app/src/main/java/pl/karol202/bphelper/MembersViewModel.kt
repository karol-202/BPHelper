package pl.karol202.bphelper

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MembersViewModel(application: Application) : AndroidViewModel(application)
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

	fun addMember(member: Member) = coroutineScope.launch(Dispatchers.IO) {
		repository.addMember(member)
	}

	fun updateMember(member: Member) = coroutineScope.launch(Dispatchers.IO) {
		repository.updateMember(member)
	}

	fun removeMember(member: Member) = coroutineScope.launch(Dispatchers.IO) {
		repository.removeMember(member)
	}
}