package blink

import org.specs2.mutable.Specification

class SnapSpec extends Specification {

  "The Snap class" should {

    "Return the correct number of decks for a player" in {

      val nDecks1 = 2
      new Snap(nDecks1, MatchType.Both).getDecksForPlayer.length must ===(nDecks1 * 52)
      val nDecks2 = 2
      new Snap(nDecks2, MatchType.Both).getDecksForPlayer.length must ===(nDecks2 * 52)

    }

    "Correctly match 2 cards" in {

      val card1 = Card(Suit.Diamonds, CardValue.Ace)
      val card2 = Card(Suit.Diamonds, CardValue.Ten)
      val card3 = Card(Suit.Hearts, CardValue.Ten)
      val card4 = Card(Suit.Diamonds, CardValue.Ten)
      val card5 = Card(Suit.Hearts, CardValue.Ten)
      val card6 = Card(Suit.Hearts, CardValue.Ten)

      val suitMatcher = new Snap(1, MatchType.Suit)
      val cardMatcher = new Snap(1, MatchType.CardValue)
      val bothMatcher = new Snap(1, MatchType.Both)

      suitMatcher.isMatch(card1, card2) must beTrue
      suitMatcher.isMatch(card1, card3) must beFalse

      cardMatcher.isMatch(card3, card4) must beTrue
      cardMatcher.isMatch(card1, card3) must beFalse

      bothMatcher.isMatch(card5, card6) must beTrue
      bothMatcher.isMatch(card1, card6) must beFalse

    }

    "Correctly split a list of pairs" in {

      val pair1 = (Card(Suit.Diamonds, CardValue.Ace), Card(Suit.Diamonds, CardValue.Two))
      val pair2 = (Card(Suit.Diamonds, CardValue.Ten), Card(Suit.Diamonds, CardValue.Ace))
      val pair3 = (Card(Suit.Hearts, CardValue.Ten), Card(Suit.Diamonds, CardValue.Ace))
      val pair4 = (Card(Suit.Diamonds, CardValue.Ten), Card(Suit.Diamonds, CardValue.Ten))
      val pair5 = (Card(Suit.Hearts, CardValue.Ten), Card(Suit.Diamonds, CardValue.Ace))
      val pair6 = (Card(Suit.Hearts, CardValue.Ten), Card(Suit.Diamonds, CardValue.Ace))

      val bothMatcher = new Snap(1, MatchType.Both)
      val allPairs = List(pair1, pair2, pair3, pair4, pair5, pair6)
      val (beforeMatch, matchOpt, afterMatch) = bothMatcher.splitPairsAtMatch(allPairs)

      beforeMatch must ===(List(pair1, pair2, pair3))
      matchOpt must ===(Some(pair4))
      afterMatch must ===(List(pair5, pair6))
    }


  }
}
