package com.wangbin.mydome.tools

import android.graphics.Bitmap
import android.util.Log
import java.io.*
import java.text.DecimalFormat

/**
 * @ClassName FileUtil
 * @Description 文件工具类
 * @Author WangBin
 * @Date 2019/3/20 17:59
 */
object FileUtil {

    private const val SIZETYPE_B = 1//获取文件大小单位为B的double值
    private const val SIZETYPE_KB = 2//获取文件大小单位为KB的double值
    private const val SIZETYPE_MB = 3//获取文件大小单位为MB的double值
    private const val SIZETYPE_GB = 4//获取文件大小单位为GB的double值

    /**
     * 判断文件是否存在
     * @param strFolder 文件路径
     */
    fun isFolderExists(strFolder: String): Boolean {
        val file = File(strFolder)
        return if (!file.exists()) {
            file.mkdirs()
        } else true
    }

    /**
     * 把bitmap 转file
     *
     * @param bitmap    传入的bitmap
     * @param filepath  将要保存图片的路径
     */
    fun saveBitmapFile(bitmap: Bitmap, filepath: String): File {
        val file = File(filepath)
        try {
            val bos = BufferedOutputStream(FileOutputStream(file))
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos)
            bos.flush()
            bos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @return Long值的大小
     */
    private fun getFileSize(filePath: String): Long {
        val file = File(filePath)
        val blockSize: Long
        try {
            //是否为文件
            blockSize = if (file.isDirectory) {
                getFileSizes(file)
            } else {
                getFileSize(file)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("获取文件大小", "获取失败!")
            return 0
        }
        return blockSize
    }

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    fun getFileOrFileSize(filePath: String, sizeType: Int): Double {
        return formetFileSize(getFileSize(filePath), sizeType)
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    fun getAutoFileOrFilesSize(filePath: String): String {
        return formetFileSize(getFileSize(filePath))
    }

    /**
     * 获取指定文件大小
     *
     * @param file 传入文件
     * @return Long
     */
    private fun getFileSize(file: File): Long {
        try {
            if (!file.exists()) {
                file.createNewFile()
                Log.e("获取文件大小", "文件不存在!")
                return 0
            }
            val fis: FileInputStream?
            fis = FileInputStream(file)
            return fis.available().toLong()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return 0
        }
    }

    /**
     * 获取指定文件夹大小
     *
     * @param f
     * @return
     */
    private fun getFileSizes(f: File): Long {
        return try {
            var size: Long = 0
            val fList = f.listFiles()
            for (i in fList!!.indices) {
                size = if (fList[i].isDirectory) {
                    size + getFileSizes(fList[i])
                } else {
                    size + getFileSize(fList[i])
                }
            }
            size
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            0
        }
    }

    /**
     * 转换文件大小
     *
     * @param fileS     文件的长度
     * @return String
     */
    private fun formetFileSize(fileS: Long): String {
        val df = DecimalFormat("#.00")
        val fileSizeString: String
        val wrongSize = "0B"
        if (fileS == 0L) {
            return wrongSize
        }
        fileSizeString = when {
            fileS < 1024 -> df.format(fileS.toDouble()) + "B"
            fileS < 1048576 -> df.format(fileS.toDouble() / 1024) + "KB"
            fileS < 1073741824 -> df.format(fileS.toDouble() / 1048576) + "MB"
            else -> df.format(fileS.toDouble() / 1073741824) + "GB"
        }
        return fileSizeString
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS     文件长度
     * @param sizeType  长度类型
     * @return
     */
    private fun formetFileSize(fileS: Long, sizeType: Int): Double {
        val df = DecimalFormat("#.00")
        var fileSizeLong = 0.0
        when (sizeType) {
            SIZETYPE_B -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble()))!!
            SIZETYPE_KB -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble() / 1024))!!
            SIZETYPE_MB -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble() / 1048576))!!
            SIZETYPE_GB -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble() / 1073741824))!!
            else -> {
            }
        }
        return fileSizeLong
    }

}
