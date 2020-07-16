package com.ddone.connect

import java.io.OutputStream
import java.net.Socket

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/15-21:13
 *
 */
object Client {
  def main(args: Array[String]): Unit = {
    val clientSocket = new Socket("localhost", 9999)
    val outputStream: OutputStream = clientSocket.getOutputStream()
    outputStream.write(10)
    outputStream.flush()
    outputStream.close()
  }
}
