package pl.karol202.bphelper.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_members.*
import pl.karol202.bphelper.Member
import pl.karol202.bphelper.MembersViewModel
import pl.karol202.bphelper.R
import pl.karol202.bphelper.TableConfigurationType
import pl.karol202.bphelper.ui.extensions.act
import pl.karol202.bphelper.ui.extensions.alertDialog
import pl.karol202.bphelper.ui.extensions.ctx
import pl.karol202.bphelper.ui.extensions.setOnItemSelectedListener

class MembersFragment : Fragment()
{
	private var tableConfigurationType = TableConfigurationType.TYPE_4X2

	private val membersViewModel by lazy { ViewModelProviders.of(act).get<MembersViewModel>() }
	private val members get() = membersViewModel.allMembers.value
	private val configurationAdapter = TableConfigurationAdapter()
	private val membersAdapter = MembersAdapter()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
		inflater.inflate(R.layout.fragment_members, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		observeMembers()

		initConfigurationSpinner()
		initRecyclerView()
		initDrawButton()
		updateErrorBanner()
	}

	private fun observeMembers()
	{
		membersViewModel.allMembers.observe(this, Observer { members ->
			members?.let { membersAdapter.members = it }
			updateErrorBanner()
		})
	}

	private fun initConfigurationSpinner()
	{
		spinnerTableConfiguration.apply {
			adapter = configurationAdapter
			setSelection(configurationAdapter.getPosition(tableConfigurationType))
			setOnItemSelectedListener { _, _, position, _ ->
				tableConfigurationType = TableConfigurationType.values()[position]
				updateErrorBanner()
			}
		}
	}

	private fun initRecyclerView()
	{
		membersAdapter.callback = object : MembersAdapter.Callback
		{
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

		recyclerMembers.layoutManager = LinearLayoutManager(ctx)
		recyclerMembers.adapter = membersAdapter
		recyclerMembers.addItemDecoration(ItemDivider.createDefault(ctx))
	}

	private fun initDrawButton()
	{
		fabDraw.setOnClickListener {
			val members = members ?: return@setOnClickListener showMembersNullAlert()
			val remainingSeats = getRemainingSeatsForMembers(members)
			if(remainingSeats == 0) navigateToTablesFragment()
			else showMembersAmountAlert(remainingSeats)
		}
	}

	private fun getRemainingSeatsForMembers(members: List<Member>) =
		tableConfigurationType.getRemainingSeatsForMembers(members)

	private fun navigateToTablesFragment()
	{
		val action = MembersFragmentDirections.toTablesFragment(tableConfigurationType.name)
		NavHostFragment.findNavController(this).navigate(action)
	}

	private fun showMembersAmountAlert(remainingSeats: Int)
	{
		if(remainingSeats > 0)
			showMembersAlert(resources.getQuantityString(R.plurals.text_too_few_members, remainingSeats, remainingSeats))
		else if(remainingSeats < 0)
			showMembersAlert(resources.getQuantityString(R.plurals.text_too_many_members, -remainingSeats, -remainingSeats))
	}

	private fun showMembersNullAlert() = showMembersAlert(resources.getString(R.string.text_error))

	private fun showMembersAlert(message: String)
	{
		ctx.alertDialog {
			setTitle(getString(R.string.alert_cannot_draw_title))
			setMessage(message)
			setNegativeButton(R.string.action_cancel) { dialog, _ ->
				dialog.dismiss()
			}
		}.show()
	}

	private fun updateErrorBanner()
	{
		val remainingSeats = members?.let { getRemainingSeatsForMembers(it) }
		textMembersError.text = when
		{
			remainingSeats == null -> getString(R.string.text_error)
			remainingSeats > 0 -> resources.getQuantityString(R.plurals.text_too_few_members, remainingSeats, remainingSeats)
			remainingSeats < 0 -> resources.getQuantityString(R.plurals.text_too_many_members, -remainingSeats, -remainingSeats)
			else -> null
		}
	}
}
