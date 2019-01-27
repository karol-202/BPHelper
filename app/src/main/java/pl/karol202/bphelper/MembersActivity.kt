package pl.karol202.bphelper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_members.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener

class MembersActivity : AppCompatActivity()
{
	private lateinit var membersViewModel: MembersViewModel
	private var tableConfigurationType = TableConfigurationType.TYPE_4X2

	private val configurationAdapter = TableConfigurationAdapter()
	private val membersAdapter = MembersAdapter()

	override fun onCreate(savedInstanceState: Bundle?)
	{
		setTheme(R.style.AppTheme)
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_members)

		initViewModel()

		initToolbar()
		initConfigurationSpinner()
		initRecyclerView()
		initDrawButton()
		updateErrorBanner()
	}

	private fun initViewModel()
	{
		membersViewModel = ViewModelProviders.of(this).get()
		membersViewModel.allMembers.observe(this, Observer { members ->
			members?.let { membersAdapter.members = it }
			updateErrorBanner()
		})
	}

	private fun initToolbar()
	{
		setSupportActionBar(toolbar)
	}

	private fun initConfigurationSpinner()
	{
		spinnerTableConfiguration.apply {
			adapter = configurationAdapter
			setSelection(configurationAdapter.getPosition(tableConfigurationType))
			onItemSelectedListener {
				onItemSelected { _, _, position, _ ->
					tableConfigurationType = TableConfigurationType.values()[position]
					updateErrorBanner()
				}
			}
		}
	}

	private fun initRecyclerView()
	{
		membersAdapter.callback = object : MembersAdapter.Callback {
			override fun addMember(member: Member)
			{
				membersViewModel.addMember(member)
			}

			override fun updateMember(member: Member)
			{
				membersViewModel.updateMember(member)
			}

			override fun removeMember(member: Member)
			{
				membersViewModel.removeMember(member)
			}
		}

		recyclerMembers.layoutManager = LinearLayoutManager(this)
		recyclerMembers.adapter = membersAdapter
		recyclerMembers.addItemDecoration(ItemDivider(getDrawableCompat(R.drawable.divider)))
	}

	private fun initDrawButton()
	{
		fabDraw.onClick {
			val remainingSeats = getRemainingSeatsForMembers()
			val members = membersViewModel.allMembers.value
			if(remainingSeats == 0 && members != null) startTablesActivity(tableConfigurationType, members)
			else showMembersErrorAlert(remainingSeats)
		}
	}

	private fun getRemainingSeatsForMembers(): Int?
	{
		val factory = tableConfigurationType.factory
		val members = membersViewModel.allMembers.value ?: return null
		return factory.getRemainingSeatsForMembers(members)
	}

	private fun showMembersErrorAlert(remainingSeats: Int?)
	{
		val message = when
		{
			remainingSeats != null && remainingSeats > 0 ->
				resources.getQuantityString(R.plurals.text_too_few_members, remainingSeats, remainingSeats)
			remainingSeats != null && remainingSeats < 0 ->
				resources.getQuantityString(R.plurals.text_too_many_members, -remainingSeats, -remainingSeats)
			else -> resources.getString(R.string.alert_cannot_draw_error)
		}

		alertDialog {
			setTitle(getString(R.string.alert_cannot_draw_title))
			setMessage(message)
			setNegativeButton(R.string.action_cancel) { dialog, _ ->
				dialog.dismiss()
			}
		}.show()
	}

	private fun updateErrorBanner()
	{
		val remainingSeats = getRemainingSeatsForMembers()
		if(remainingSeats != null && remainingSeats != 0)
		{
			val text = if(remainingSeats > 0) resources.getQuantityString(R.plurals.text_too_few_members, remainingSeats, remainingSeats)
					   else resources.getQuantityString(R.plurals.text_too_many_members, -remainingSeats, -remainingSeats)
			bannerMembersError.show(text)
		}
		else bannerMembersError.hide()
	}
}
