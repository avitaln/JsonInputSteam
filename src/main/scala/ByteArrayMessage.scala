/**
 * User: avitaln
 * Date: 6/8/13
 */
case class ByteArrayMessage(array: Array[Byte], length: Int) {
  def apply(i: Int): Byte = array(i)
}
