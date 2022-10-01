package mappers

import models.Residuo

typealias InMapperCsv = ResiduosInMapper<Sequence<String>, Residuo>
typealias OutMapperCsv = ResiduosOutMapper<Residuo, Sequence<String>>