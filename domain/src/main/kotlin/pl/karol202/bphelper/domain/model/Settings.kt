package pl.karol202.bphelper.domain.model

class Settings(val tableConfigurationType: TableConfigurationType)
{
	companion object
	{
		val DEFAULT = Settings(tableConfigurationType = TableConfigurationType.DEFAULT)
	}
}
