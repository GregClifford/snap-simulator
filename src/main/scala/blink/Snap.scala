package blink

import blink.MatchType.MatchType
import scala.annotation.tailrec
import scala.util.Random

class Snap(nDecks: Int, matchType: MatchType) {

  type Pair = (Card, Card)

  private[blink] def getDecksForPlayer = Range(0, nDecks).flatMap(_ => Deck.getShuffledDeck).toList

  private[blink] def isMatch(card1: Card, card2: Card): Boolean = {

    matchType match {
      case MatchType.Suit => card1.suit == card2.suit
      case MatchType.CardValue => card1.cardValue == card2.cardValue
      case MatchType.Both => card1.suit == card2.suit && card1.cardValue == card2.cardValue
    }
  }

  private def getPlayer1Wins: Boolean = Random.nextInt(2) == 0

  private[blink] def splitPairsAtMatch(pairs: List[Pair]): (List[Pair], Option[Pair], List[Pair]) = {

    val indexOfMatch = pairs.indexWhere(pair => isMatch(pair._1, pair._2))
    if(indexOfMatch > -1)
      {
        val (pairsBeforeMatch, pairsAfterMatch) = pairs.splitAt(indexOfMatch)
        (pairsBeforeMatch, pairsAfterMatch.headOption, pairsAfterMatch.tail)
      }
    else
      {
        (pairs, None, Nil)
      }
  }

  def play(): (List[Card], List[Card]) = {

    println("Playing...")
    val allPairs: List[Pair] = getDecksForPlayer.zip(getDecksForPlayer)

    @tailrec
    def playGame(remainingPairs: List[Pair], player1Cards: List[Card], player2Cards: List[Card]): (List[Card], List[Card]) = {

      //recursively divide the list at each match, added the previous cards to the winning players total and continuing with the
      //remaining cards.

      val (pairsBeforeMatch, matchedPair, pairsAfterMatch) = splitPairsAtMatch(remainingPairs)

      matchedPair match {

          //there are no more matches in the decks, exit
        case None => pairsBeforeMatch.foreach(pair => println(s"Pair: ${pair._1} & ${pair._2}"))
          (player1Cards, player2Cards)

        case Some(thePair) => val cardsToAdd = (thePair :: pairsBeforeMatch).flatMap(cards => List(cards._1, cards._2))

          val player1Wins = getPlayer1Wins

          pairsBeforeMatch.foreach(pair => println(s"Pair: ${pair._1} & ${pair._2}"))
          println(s"Snap! Pair: ${thePair._1} & ${thePair._2}. Winner is ${if (player1Wins) "player 1!" else "player 2!"}")

          val player1NewCards = if (player1Wins) player1Cards ::: cardsToAdd else player1Cards
          val player2NewCards = if (player1Wins) player2Cards else player2Cards ::: cardsToAdd

          //only continue if there are more cards to play
          if (pairsAfterMatch.nonEmpty)
            playGame(pairsAfterMatch, player1NewCards, player2NewCards)
          else
            (player1NewCards, player2NewCards)
      }
    }
    playGame(allPairs, List.empty, List.empty)
  }
}
