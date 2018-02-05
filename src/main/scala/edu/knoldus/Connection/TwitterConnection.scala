package edu.knoldus.Connection

import com.typesafe.config.{Config, ConfigFactory}
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Twitter, TwitterFactory}

class TwitterConnection {

  /**
    * this method returns an instance of Twitter
    *
    * */
  def getConnection: Twitter = {
    try {
      val config: Config = ConfigFactory.load("application.conf")
      val consumerKey: String = config.getString("consumer key")
      val consumerSecret: String = config.getString("consumer secret")
      val accessToken: String = config.getString("access token")
      val accessTokenSecret: String = config.getString("access token secret")
      val configurationBuilder = new ConfigurationBuilder()
      configurationBuilder.setDebugEnabled(true)
        .setOAuthConsumerKey(consumerKey)
        .setOAuthConsumerSecret(consumerSecret)
        .setOAuthAccessToken(accessToken)
        .setOAuthAccessTokenSecret(accessTokenSecret)
      val twitter = new TwitterFactory(configurationBuilder.build())
      twitter.getInstance()
    }
    catch {
      case ex: Exception => throw new Exception("couldn't make connection")
    }
  }

}
