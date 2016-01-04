package objsets

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.Ignore

@RunWith(classOf[JUnitRunner])
class TweetSetSuite extends FunSuite {
  trait TestSets {
    val set1 = new Empty
    val set2 = set1.incl(new Tweet("a", "a body", 20))
    val set3 = set2.incl(new Tweet("b", "b body", 20))
    val c = new Tweet("c", "c body", 7)
    val d = new Tweet("d", "d body", 9)
    val set4c = set3.incl(c)
    val set4d = set3.incl(d)
    val set5 = set4c.incl(d)
    
    val set6 = set1.incl(c)
    val set7 = set6.incl(d)
  }

  def asSet(tweets: TweetSet): Set[Tweet] = {
    var res = Set[Tweet]()
    tweets.foreach(res += _)
    res
  }

  def size(set: TweetSet): Int = asSet(set).size

  test("filter: on empty set") {
    new TestSets {
      println(set1)
      println(set2)
      println(set3)
      println(set4c)
      println(set5)
      assert(size(set1.filter(tw => tw.user == "a")) === 0)
    }
  }

  test("filter: a on set5") {
    new TestSets {
      assert(size(set5.filter(tw => tw.user == "a")) === 1)
    }
  }
  
  test("filter: 20 on set5") {
    new TestSets {
      assert(size(set5.filter(tw => tw.retweets == 20)) === 2)
    }
  }

  test("union: set4c and set4d") {
    new TestSets {
      assert(size(set4c.union(set4d)) === 4)
    }
  }

  test("union: with empty set (1)") {
    new TestSets {
      assert(size(set5.union(set1)) === 4)
    }
  }

  test("union: with empty set (2)") {
    new TestSets {
      assert(size(set1.union(set5)) === 4)
    }
  }
  
    test("mostRetweeted: with set 5") {
    new TestSets {
      val most = set5.mostRetweeted
      assert(most.user == "a" || most.user == "b")
    }
  }

  test("mostRetweeted: with set 1") {
    intercept[NoSuchElementException] {
      new TestSets {
        set1.mostRetweeted
      }
    }
  }
  
    test("remove: set6") {
    new TestSets {
    	assert(size(set6.remove(c)) === 0)
    	println(set6.remove(c))
    }
  }

  test("descending: set5") {
    new TestSets {
      val trends = set5.descendingByRetweet
      assert(!trends.isEmpty)
      assert(trends.head.user == "a" || trends.head.user == "b")
      println("================")
      println("descending: set5")
      println("================")
      trends foreach println
    }
  }
  
    test("descending: set7") {
    new TestSets {
      val trends = set7.descendingByRetweet
      assert(!trends.isEmpty)
      assert(trends.head.user == "d")
      println("================")
      println("descending: set7")
      println("================")
      trends foreach println
    }
  }
    
  test("trending") {
    new TestSets {
      val google = GoogleVsApple.googleTweets
      val apple = GoogleVsApple.appleTweets
      val trends = GoogleVsApple.trending
      assert(trends.head.retweets === 321)
      trends foreach println

    }
  }
}
