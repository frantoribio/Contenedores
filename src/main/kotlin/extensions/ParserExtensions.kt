package extensions

import parsers.NamedParser
import parsers.Parser

infix fun <T> Parser<T>.named(name: String) = NamedParser(this, name)