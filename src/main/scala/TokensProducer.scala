import java.util.concurrent.{Executors, ArrayBlockingQueue}
import scala.Array
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.OutputStream


/**
 * User: avitaln
 * Date: 6/8/13
 */
class TokensProducer(value: Any, val objectMapper: ObjectMapper) {

  private val EMPTY_ARRAY = new ByteArrayMessage(null, 0)
  private val UNDEFINED_ARRAY = new ByteArrayMessage(null, -1)
  private val queue = new ArrayBlockingQueue[ByteArrayMessage](1)

  private var current: ByteArrayMessage = UNDEFINED_ARRAY

  Executors.defaultThreadFactory().newThread(new Runnable {
    def run() {
      produceValues()
    }
  }).start()

  private def lookAhead : ByteArrayMessage = {
    if (current == UNDEFINED_ARRAY) {
      current = queue.take
    }
    current
  }

  def iterator = new Iterator[ByteArrayMessage] {
    def hasNext: Boolean = lookAhead.length > 0

    def next(): ByteArrayMessage = {
      val ret = lookAhead
      current = UNDEFINED_ARRAY
      ret
    }
  }

  private def produceValues() {
    objectMapper.writeValue(new OutputStream {
      override def write(b: Array[Byte], off: Int, len: Int) {
        val dst : Array[Byte] = new Array[Byte](len)
        System.arraycopy(b,0,dst,0,len)
        produce(ByteArrayMessage(dst,len))
      }
      def write(b: Int) {
        // Should never be reach because Jackson does not call it
        throw new UnsupportedOperationException
      }
    }, value)
    produce(EMPTY_ARRAY) // EOF
  }

  private def produce(x: ByteArrayMessage) {
    queue.put(x)
  }
}
