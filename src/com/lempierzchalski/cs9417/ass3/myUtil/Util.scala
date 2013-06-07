package com.lempierzchalski.cs9417.ass3.myUtil

import java.io.{FileWriter, PrintWriter, File}

/**
 * Created with IntelliJ IDEA.
 * User: Pierzchalski
 * Date: 7/06/13
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
object Util {
  def indexedFilePrintWriter(fileDir: String, fileName: String, fileType: String): PrintWriter = {
    val indexedFileName = {
      var i = 0
      var indexedFileName = ""
      while({indexedFileName = f"$fileDir$fileName$i$fileType"; val f = new File(indexedFileName); f.exists()}) {
        i += 1
      }
      indexedFileName
    }
    new PrintWriter(new FileWriter(indexedFileName))
  }
}
