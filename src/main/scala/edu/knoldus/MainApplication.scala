package edu.knoldus

import edu.knoldus.modules.Tweet
import edu.knoldus.operations.TwitterOperations
import org.apache.log4j.Logger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object MainApplication {

  def main(args: Array[String]): Unit = {
    val log = Logger.getLogger(this.getClass)
    val sleepTime = 2000
    val sleepTime1 = 5000
    val twitterApp = new TwitterOperations
    val hashTag = "#abc"
    val tweets: Future[List[Tweet]] = twitterApp.getTweetsOnHashTag(hashTag)
    tweets onComplete {
      case Success(tweet) => log.info(s"\n\n\nTweets based on $hashTag = $tweet\n")
      case Failure(ex) => log.info(s"${ex.getMessage}")
    }
    Thread.sleep(sleepTime)
    val countOfTweets = twitterApp.getCountOfTweets(hashTag)
    countOfTweets onComplete {
      case Success(noOfTweets) => log.info(s"\n\n\nNumber of tweets retrieved = $noOfTweets\n")
      case Failure(ex) => log.info(s"${ex.getMessage}")
    }
    Thread.sleep(sleepTime)
    val username = "@vidi_gupta12"
    val avgTweets = twitterApp.getAvgTweetsPerDay(username)
    avgTweets onComplete {
      case Success(avgTweetsPerDay) => log.info(s"\n\n\nAverage tweets per day = $avgTweetsPerDay\n")
      case Failure(ex) => log.info(s"${ex.getMessage}")
    }
    Thread.sleep(sleepTime)
    val avgFavAndReTweet = twitterApp.getAvgFavouritesAndReTweets(username)
    avgFavAndReTweet onComplete {
      case Success(avgFavAndReTweets) => log.info(s"\n\n\nAverage favourites and re-tweets per day = $avgFavAndReTweets\n")
      case Failure(ex) => log.info(s"${ex.getMessage}")
    }
    Thread.sleep(sleepTime1)
  }

}
