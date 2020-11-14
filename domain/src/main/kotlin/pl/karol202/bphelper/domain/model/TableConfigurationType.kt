package pl.karol202.bphelper.domain.model

enum class TableConfigurationType(val tableTypes: List<TableType>)
{
	TYPE_4X2(listOf(
		TableType(size = 2, role = TableType.Role.OG),
		TableType(size = 2, role = TableType.Role.OO),
		TableType(size = 2, role = TableType.Role.CG),
		TableType(size = 2, role = TableType.Role.CO),
	)),
	TYPE_2X3(listOf(
		TableType(size = 3, role = TableType.Role.GOV),
		TableType(size = 3, role = TableType.Role.OPP),
	)),
	TYPE_2X4(listOf(
		TableType(size = 4, role = TableType.Role.GOV),
		TableType(size = 4, role = TableType.Role.OPP),
	));

	companion object
	{
		val DEFAULT = TYPE_4X2

		fun findByName(name: String) = values().find { it.name == name }
	}
}
