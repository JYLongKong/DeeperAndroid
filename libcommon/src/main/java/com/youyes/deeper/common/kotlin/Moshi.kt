package com.youyes.deeper.common.kotlin

import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * Created by YJY on 19-8-2.
 * Email：yujye@sina.com
 */

data class BlackjackHand(
    val hidden_card: Card,
    val visible_cards: List<Card>,
    @Transient  // Prevent a field from being encoding JSON
    private val total: Int = 0
)

data class Card(
    val rank: Char,
    val suit: Suit
)

enum class Suit {
    CLUBS, DIAMONDS, HEARTS, SPADES
}

fun basicUse() {
    val json = ""
    val moshi = Moshi.Builder().build()
    val jsonAdapter = moshi.adapter(BlackjackHand::class.java)
    val blackjackHand = jsonAdapter.fromJson(json)

    val blackjackHand2 =
        BlackjackHand(
            Card(
                '6',
                Suit.SPADES
            ),
            arrayListOf(
                Card(
                    '4',
                    Suit.CLUBS
                ),
                Card(
                    'A',
                    Suit.HEARTS
                )
            )
        )
    val moshi2 = Moshi.Builder().build()
    val jsonAdapter2 = moshi2.adapter(BlackjackHand::class.java)
    val json2 = jsonAdapter2.toJson(blackjackHand2)
}

/**
 * With a type adapter, we can change the encoding to something more compact
 *
 * transform
 *
 *  {
 *  "hidden_card": {
 *  "rank": "6",
 *  "suit": "SPADES"
 *  },
 *  "visible_cards": [
 *  {
 *  "rank": "4",
 *  "suit": "CLUBS"
 *  },
 *  {
 *  "rank": "A",
 *  "suit": "HEARTS"
 *  }
 *  ]
 *  }
 *
 *  to
 *
 *  {
 *  "hidden_card": "6S",
 *  "visible_cards": [
 *  "4C",
 *  "AH"
 *  ]
 *  }
 *
 */
class CardAdapter {
    @ToJson
    fun toJson(card: Card): String {
        return card.rank + card.suit.name.substring(0, 1)
    }

    @FromJson
    fun fromJson(card: String): Card {
        if (card.length != 2) throw JsonDataException("Unknown suit: $card")
        val rank = card[0]
        return when (card[1]) {
            'C' -> Card(
                rank,
                Suit.CLUBS
            )
            'D' -> Card(
                rank,
                Suit.DIAMONDS
            )
            'H' -> Card(
                rank,
                Suit.HEARTS
            )
            'S' -> Card(
                rank,
                Suit.SPADES
            )
            else -> throw JsonDataException("unknown suit: $card")
        }
    }
}

fun registerAdapter() {
    val moshi = Moshi.Builder().add(CardAdapter()).build()
}

fun parseJSONArrays() {
    val cardsJsonResponse = ""
    val type = Types.newParameterizedType(List::class.java, Card::class.java)
    val moshi = Moshi.Builder().build()
    val adapter = moshi.adapter<List<Card>>(type)
    val cards = adapter.fromJson(cardsJsonResponse)
}


/**
 * spaces or some characters are not permitted in FinalizeEscapeGC field names
 *
 * {
 * "username": "jesse",
 * "lucky number": 32
 * }
 */
data class Player(
    val username: String,
    @Json(name = "lucky number") val luckyNumber: Int
)

/**
 * want to output
 *
 * {
 * "width": 1024,
 * "height": 768,
 * "color": "#ff0000"
 * }
 *
 * from
 *
 * class Rectangle {
 * int width;
 * int height;
 * int color;
 * }
 *
 */
@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class HexColor

data class Rectangle(
    val width: Int,
    val height: Int,
    @HexColor val color: Int
)

class ColorAdapter {
    @ToJson
    fun toJson(@HexColor rgb: Int): String {
        return String.format("#%06x", rgb)
    }

    @FromJson
    @HexColor
    fun fromJson(rgb: String): Int {
        return Integer.parseInt(rgb.substring(1), 16)
    }
}

// If the class has a no-arguments constructor, Moshi will call that constructor and whatever value it assigns will be used.
// the total field is initialized to -1
class BlackjackHand2 {
    private val total = -1

    @SuppressWarnings("unused") // Moshi uses this!
    private constructor() {
    }

    constructor(hidden_card: Card, visible_cards: List<Card>) {}
}


// If the class doesn’t have a no-arguments constructor, Moshi can’t assign the field’s default value, even if it’s specified in the field declaration
// Instead, the field’s default is always 0 for numbers, false for booleans, and null for references
// the default value of total is 0!
class BlackjackHand3 {
    private val total = -1

    constructor(hidden_card: Card, visible_cards: List<Card>) {}
}

// The reflection adapter uses Kotlin’s reflection library to convert your Kotlin classes to and from JSON
// add the Kotlin adapter after your own custom adapters,
// Otherwise the KotlinJsonAdapterFactory will take precedence and your custom adapters will not be called.
// Note that the reflection adapter transitively depends on the kotlin-reflect library which is a 2.5 MiB .jar file.
fun enableKotlinReflection() {
    val moshi = Moshi.Builder()
        // ... add your own JsonAdapters and factories ...
        .add(KotlinJsonAdapterFactory())
        .build()
}

// @JsonClass(generateAdapter = true) generates a small and fast adapter for each of your Kotlin classes at compile time
// The codegen adapter requires that your Kotlin types and their properties be either internal or public (this is Kotlin’s default visibility).
@JsonClass(generateAdapter = true)
data class Bean(val bean: String)

// Limitations: Neither reflection or codegen support Kotlin types with FinalizeEscapeGC supertypes or FinalizeEscapeGC types with Kotlin supertypes
// If you need to convert such classes to JSON you must create a custom type adapter.

// Prefer codegen for better performance and to avoid the kotlin-reflect dependency
// Prefer reflection to convert both private and protected properties