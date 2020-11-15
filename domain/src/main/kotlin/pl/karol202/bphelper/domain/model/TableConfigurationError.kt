package pl.karol202.bphelper.domain.model

sealed class TableConfigurationError
{
	class TooFewMembers(val missingAmount: Int) : TableConfigurationError()

	class TooManyMembers(val exceedingAmount: Int) : TableConfigurationError()

	object ConfigurationImpossible : TableConfigurationError()
}
