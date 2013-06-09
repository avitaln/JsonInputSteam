import scala.beans.BeanProperty

/**
 * User: avitaln
 * Date: 6/8/13
 */

case class SampleTreeObject(
              @BeanProperty value : String,
              @BeanProperty left : SampleTreeObject,
              @BeanProperty right : SampleTreeObject
              )


object SampleTreeObject {
  def generate(depth : Int, maxDepth : Int) : SampleTreeObject = {
    if (depth > maxDepth) {
      SampleTreeObject(randomString(100), null, null)
    } else {
      SampleTreeObject(randomString(100), generate(depth+1,maxDepth), generate(depth+1,maxDepth))
    }
  }

  def randomString(len: Int) : String = "abcdefg123456abcdefg123456abcdefg123456abcdefg123456abcdefg123456abcdefg123456"
}
