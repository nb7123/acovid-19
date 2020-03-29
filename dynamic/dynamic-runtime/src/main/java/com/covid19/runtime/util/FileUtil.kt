package com.covid19.runtime.util

import java.io.*
import java.lang.Exception

/**
 * --------------------------------------------------------------|
 *                                                               |
 *                          _________-----_____                  |
 *            _____------           __      ----_                |
 *   _____----             ___------              \              |
 *        ----________        ----                 \             |
 *                    -----__    |             _____)            |
 *                         __-                /     \            |
 *             _______-----    ___--          \    /)\           |
 *     --------_______      ---____            \__/  /           |
 *                    -----__    \ --    _          /\           |
 *                           --__--__     \_____/   \_/\         |
 *                                   ----|   /          |        |
 *                                       |  |___________|        |
 *                                       |  | ((_(_)| )_)        |
 *                                       |  \_((_(_)|/(_)        |
 *                                       \             (         |
 *                                        \_____________)        |
 *                              @@@@@                            |
 * --------------------------------------------------------------|
 *       Author[michael]          |       Create[2020-01-30]
 * --------------------------------------------------------------
 * Description:
 * --------------------------------------------------------------
 */
class FileUtil {
    companion object{
        /**
         * copy stream data to another stream
         * @param src stream to copy
         * @param dest stream to write to
         */
        @Throws(IOException::class)
        fun copy(src: InputStream, dest: OutputStream) {
            val bufferSize = 4096
            val buffer = ByteArray(bufferSize)

            while (true) {
                val len = src.read(buffer)
                if (len <= 0) break

                dest.write(buffer, 0, len)
            }

            dest.flush()
        }

        /**
         * copy a file data to another file
         * @param src file to copy
         * @param dest file to write
         */
        @Throws(IOException::class)
        fun copy(src: File, dest: File) {
            val inputStream = FileInputStream(src)
            val outputStream = FileOutputStream(dest)
            var exception: Exception? = null

            try {
                copy(inputStream, outputStream)
            } catch (e: Exception) {
                exception = e
            } finally {
                inputStream.close()
                outputStream.flush()
                outputStream.close()
            }

            if (null != exception) throw exception
        }

        /**
         * copy stream data to a file
         * @param inputStream stream to copy
         * @param destFile file to write
         */
        @Throws(IOException::class)
        fun copy(inputStream: InputStream, destFile: File) {
            val outputStream = FileOutputStream(destFile)
            var exception: Exception? = null

            try {
                copy(inputStream, outputStream)
            } catch (e: Exception) {
                exception = e
            } finally {
                inputStream.close()
                outputStream.flush()
                outputStream.close()
            }

            if (null != exception) throw exception
        }
    }
}