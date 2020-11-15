package pl.karol202.bphelper.presentation.viewdata

import pl.karol202.bphelper.domain.model.TableConfigurationError

sealed class TableConfigurationErrorViewData
{
	class TooFewMembers(val missingAmount: Int) : TableConfigurationErrorViewData()

	class TooManyMembers(val exceedingAmount: Int) : TableConfigurationErrorViewData()

	object ConfigurationImpossible : TableConfigurationErrorViewData()
}

fun TableConfigurationError.toViewData() = when(this)
{
	is TableConfigurationError.TooFewMembers -> TableConfigurationErrorViewData.TooFewMembers(missingAmount)
	is TableConfigurationError.TooManyMembers -> TableConfigurationErrorViewData.TooManyMembers(exceedingAmount)
	is TableConfigurationError.ConfigurationImpossible -> TableConfigurationErrorViewData.ConfigurationImpossible
}
