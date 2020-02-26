package blink

import scala.io.StdIn

object SnapApplication extends App {

  println("Welcome to Snap!")
  println("How many decks would you like each player to play with?")
  val nDecksOpt = try {
    val n = StdIn.readInt()
    if(n > 0)
      Some(n)
    else
      None
  }
  catch {
    case _ : Exception => None
  }

  nDecksOpt.fold(println("Please enter a valid number...")){ nDecks =>

    println("How would you like the cards to be match? Suit(s), value(v) or both(b)?")
    val matchTypeOpt = try {

      StdIn.readChar() match  {

        case m if m == 's' => Some(MatchType.Suit)
        case m if m == 'v' => Some(MatchType.CardValue)
        case m if m == 'b' => Some(MatchType.Both)

      }
    }
    catch {
      case _ : Exception => None
    }

    matchTypeOpt.fold(println("Please enter a valid match type. Either s, v or b...")) { matchType =>

      val snap = new Snap(nDecks, matchType)
      val result = snap.play()

      if(result._1.length > result._2.length) {
        println(s"Player 1 wins by ${result._1.length} to ${result._2.length}.")
      }
      else
      {
        if(result._2.length > result._1.length) {
          println(s"Player 2 wins ${result._2.length} to ${result._1.length}")
        }
        else
          {
            println("It's a draw!")
          }
      }

    }
  }
}
