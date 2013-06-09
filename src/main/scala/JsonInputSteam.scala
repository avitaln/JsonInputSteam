import java.io.InputStream

/**
 * User: avitaln
 * Date: 6/8/13
 */
class JsonInputSteam(tokensIterator : Iterator[ByteArrayMessage]) extends InputStream {

  var currentArray : ByteArrayMessage = _
  var index = 0

  nextArray()

  override def read(): Int = {
    if (currentArray.length == 0) return -1

    if (index >= currentArray.length) {
      nextArray()
    }

    if (currentArray.length == 0) return -1

    val ret = currentArray(index)
    index = index + 1
    ret
  }

  override def read(b: Array[Byte], off: Int, len: Int): Int = {
    if (b == null) throw new NullPointerException
    if (off < 0 || len < 0 || len > b.length - off) throw new IndexOutOfBoundsException

    if (currentArray.length == 0) return -1

    var remaining = len
    var readBytes = 0
    var offset = off

    while (remaining > 0 && currentArray.length > 0) {
      val bytesToCopy = Math.min(currentArray.length - index, remaining)
      System.arraycopy(currentArray.array, index, b, offset, bytesToCopy)
      index += bytesToCopy
      remaining -= bytesToCopy
      readBytes += bytesToCopy
      offset += bytesToCopy
      if (index == currentArray.length) {
        nextArray()
      }
    }

    readBytes
  }

  private def nextArray() {
    currentArray = tokensIterator.next()
    index = 0
  }
}

