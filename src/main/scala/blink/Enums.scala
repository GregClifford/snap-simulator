package blink

object Suit extends Enumeration {
  type Suit = Value
  val Clubs, Spades, Hearts, Diamonds = Value
}

object CardValue extends Enumeration {
  type CardValue = Value
  val Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King = Value
}

object MatchType extends Enumeration {
  type MatchType = Value
  val Suit, CardValue, Both = Value
}
