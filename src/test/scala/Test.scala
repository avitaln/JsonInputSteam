/**
 * User: avitaln
 * Date: 6/8/13
 */

import com.fasterxml.jackson.databind.ObjectMapper
import java.util.Scanner

object Test extends App {
  val o = SampleTreeObject.generate(0, 10)
  val is = new JsonInputSteam(new TokensProducer(o, new ObjectMapper).iterator)
  val jsonString = new Scanner(is).nextLine()
  println(jsonString)
}

