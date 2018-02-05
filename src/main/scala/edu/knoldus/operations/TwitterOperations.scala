package edu.knoldus.operations

import edu.knoldus.Connection.TwitterConnection
import edu.knoldus.modules.Tweet
import twitter4j.{Query, Twitter}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TwitterOperations {

  val twitterTweets = new TwitterConnection
  val twitter: Twitter = twitterTweets.getConnection
  val tweetLimit = 30

  /**
    * this method returns a list of tweets
    * based on a hashTag
    */
  def getTweetsOnHashTag(hashTag: String): Future[List[Tweet]] = {
    Future {
      try {
        val query = new Query(hashTag)
        query.setCount(tweetLimit)
        query.setSince("2015-01-01")
        val tweets = twitter.search(query)
        tweets.getTweets.asScala.toList.map { tweet => Tweet(tweet.getText) }
      }
      catch {
        case ex: Exception => throw new Exception("couldn't retrieve tweets")
      }
    }
  }

  /**
    * this method counts number of tweets
    * based on a hashTag
    */
  def getCountOfTweets(hashTag: String): Future[Int] = {
    Future {
      try {
        val query = new Query(hashTag)
        query.setCount(tweetLimit)
        query.setSince("2015-01-01")
        val tweets = twitter.search(query)
        tweets.getTweets.asScala.toList.length
      }
      catch {
        case ex: Exception => throw new Exception("couldn't calculate number of tweets")
      }
    }
  }

  /**
    * this method returns average number of tweets per day
    */
  def getAvgTweetsPerDay(userName: String): Future[Int] = {
    Future {
      try {
        val query = new Query(userName)
        query.setCount(tweetLimit)
        query.setSince("2015-01-01")
        val totalDays = 10
        val tweets = twitter.search(query).getTweets.asScala.toList
        tweets.size / totalDays
      }
      catch {
        case ex: Exception => throw new Exception("couldn't calculate average tweets")
      }
    }
  }

  /**
    * this method return average number of favourites and reTweets
    */
  def getAvgFavouritesAndReTweets(userName: String): Future[(Int, Int)] = {
    Future {
      try {
        val query = new Query(userName)
        query.setCount(tweetLimit)
        query.setSince("2015-01-01")
        val tweets = twitter.search(query).getTweets.asScala.toList
        val reTweetsCountList = tweets.map { tweet => tweet.getRetweetCount }
        val favouritesCountList = tweets.map { tweet => tweet.getFavoriteCount }
        (favouritesCountList.sum / tweets.size, reTweetsCountList.sum / tweets.size)
      }
      catch {
        case ex: Exception => throw new Exception("could not find average re tweets and favourites")
      }
    }
  }

}
