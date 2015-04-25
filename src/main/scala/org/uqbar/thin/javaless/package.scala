package org.uqbar.thin

import scala.language.implicitConversions
import scala.collection.JavaConversions._
import java.util.IdentityHashMap
import scala.util.Try
import org.uqbar.utils.collections.immutable.IdentityMap
import org.uqbar.thin.encoding.combinator.EncoderPreferences

package object javaless {

	val DefaultTerminals = Map(
		'class -> "class",
		'contextOpen -> "{",
		'contextClose -> "}",
		'argumentOpen -> "(",
		'argumentClose -> ")",
		'argumentSeparator -> ",",
		'public -> "public"
	)
	
	val DefaultPreferences = new EncoderPreferences(
		spacing = Map().withDefaultValue(false)
	)
	
	type Identifier = String

	implicit class ExtendedIdentityHashMap(inner: IdentityHashMap[Any, Range]) {
		def ++(other: IdentityHashMap[Any, Range]) = {
			val next = new IdentityHashMap[Any, Range]
			next.putAll(inner)
			next.putAll(other)
			next
		}
		def shifted(ammount: Int) = {
			val next = new IdentityHashMap[Any, Range]
			for (entry <- inner.entrySet) next.put(entry.getKey, entry.getValue.start + ammount until entry.getValue.end + ammount)
			next
		}

		def updated(key: Any, value: Range) = {
			val updatedReferences = new IdentityHashMap[Any, Range]
			updatedReferences.putAll(inner)
			updatedReferences.put(key, value)
			updatedReferences
		}
	}
}