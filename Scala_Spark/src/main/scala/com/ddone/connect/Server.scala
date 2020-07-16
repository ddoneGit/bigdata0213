package com.ddone.connect

import java.io.InputStream
import java.net.{ServerSocket, Socket}

/**
 **
 *
 * @author ddone
 *         *
 * @date 2020/7/15-21:10
 *
 */
object Server {
  def main(args: Array[String]): Unit = {
    val serverSocket = new ServerSocket(9999)
    val client: Socket = serverSocket.accept()
    val inputStream: InputStream = client.getInputStream
    val result: Int = inputStream.read()
    println(result)
    inputStream.close()
  }

}
