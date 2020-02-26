package blink

import blink.CardValue.CardValue
import blink.Suit.Suit
import scala.util.Random

case class Card(suit: Suit, cardValue: CardValue){
  override def toString = s"$cardValue of $suit"
}

object Deck {
  def getShuffledDeck: List[Card] = new Random().shuffle(getSuit(Suit.Clubs) ::: getSuit(Suit.Spades) ::: getSuit(Suit.Hearts) ::: getSuit(Suit.Diamonds))

  private def getSuit(suit: Suit) = Range(0, 13).map(i => Card(suit, CardValue(i))).toList
}
