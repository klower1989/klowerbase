package com.klower.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

public class FileUtils {

	
	public static void delete(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}

		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}

			for (int i = 0; i < childFiles.length; i++) {
				delete(childFiles[i]);
			}
			file.delete();
		}
	}
	
	/**
	 * Get the external app cache directory.
	 * 
	 * @param context
	 *            The context to use
	 * @return The external cache dir
	 */
	@SuppressLint("NewApi")
	public static File getExternalCacheDir(Context context) {
		if (hasExternalCacheDir()) {
			return context.getExternalCacheDir();
		}

		// Before Froyo we need to construct the external cache dir ourselves
		final String cacheDir = "/Android/data/" + context.getPackageName()
				+ "/cache/";
		return new File(Environment.getExternalStorageDirectory().getPath()
				+ cacheDir);
	}
	
	/**
	 * Check if OS version has built-in external cache dir method.
	 * 
	 * @return
	 */
	public static boolean hasExternalCacheDir() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}
	
	/**
	 * Check how much usable space is available at a given path.
	 * 
	 * @param path
	 *            The path to check
	 * @return The space available in bytes
	 */
	@SuppressLint("NewApi")
	public static long getUsableSpace(File path) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			return path.getUsableSpace();
		}
		final StatFs stats = new StatFs(path.getPath());
		return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
	}
	
	// 存储String到文件
		public static void saveStringTofile(String content, File file) {
			OutputStreamWriter out = null;
			try {
				out = new OutputStreamWriter(new FileOutputStream(file));
				out.write(content);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
						out = null;
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// 读取文件内容
		public static String getStringFromFile(File file) {
			BufferedReader reader = null;
			StringBuffer buffer = new StringBuffer();
			try {
				reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(file)));
				String temp = null;
				while ((temp = reader.readLine()) != null) {
					buffer.append(temp + "\r\n");

				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				buffer.delete(0, buffer.length());
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				reader = null;
			}
			LogTool.d("", "File content: " + buffer.toString());
			return buffer.toString();
		}
		
		public static void unZip(String zipFile, String newPath) throws IOException {
			int BUFFER = 2048;
			File file = new File(zipFile);

			ZipFile zip = new ZipFile(file);

			new File(newPath).mkdir();
			Enumeration zipFileEntries = zip.entries();

			// Process each entry
			while (zipFileEntries.hasMoreElements()) {
				// grab a zip file entry
				ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
				String currentEntry = entry.getName();
				File destFile = new File(newPath, currentEntry);
				// destFile = new File(newPath, destFile.getName());
				File destinationParent = destFile.getParentFile();

				// create the parent directory structure if needed
				destinationParent.mkdirs();

				if (!entry.isDirectory()) {
					BufferedInputStream is = new BufferedInputStream(
							zip.getInputStream(entry));
					int currentByte;
					// establish buffer for writing file
					byte data[] = new byte[BUFFER];

					// write the current file to disk
					FileOutputStream fos = new FileOutputStream(destFile);
					BufferedOutputStream dest = new BufferedOutputStream(fos,
							BUFFER);

					// read and write until last byte is encountered
					while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, currentByte);
					}
					dest.flush();
					dest.close();
					is.close();
				}

				if (currentEntry.endsWith(".zip")) {
					// found a zip file, try to open
					unZip(destFile.getAbsolutePath(), destFile.toString());
				}
			}
		}

		
		/**
		 * 解压缩功能. 将zipFile文件解压到folderPath目录下.
		 * 
		 * @throws Exception
		 */
		public static int upZipFile(File zipFile, String folderPath)
				throws ZipException, IOException {
			ZipFile zfile = new ZipFile(zipFile);
			Enumeration<?> zList = zfile.entries();
			ZipEntry ze = null;
			byte[] buf = new byte[1024];
			while (zList.hasMoreElements()) {
				ze = (ZipEntry) zList.nextElement();
				if (ze.isDirectory()) {
					String dirstr = folderPath + ze.getName();
					// dirstr.trim();
					dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
					File f = new File(dirstr);
					f.mkdir();
					continue;
				}
				OutputStream os = new BufferedOutputStream(new FileOutputStream(
						getRealFileName(folderPath, ze.getName())));
				InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
				int readLen = 0;
				while ((readLen = is.read(buf, 0, 1024)) != -1) {
					os.write(buf, 0, readLen);
				}
				is.close();
				os.close();
			}
			zfile.close();
			return 0;
		}

		/**
		 * 给定根目录，返回一个相对路径所对应的实际文件名.
		 * 
		 * @param baseDir
		 *            指定根目录
		 * @param absFileName
		 *            相对路径名，来自于ZipEntry中的name
		 * @return java.io.File 实际的文件
		 */
		public static File getRealFileName(String baseDir, String absFileName) {
			String[] dirs = absFileName.split("/");
			File ret = new File(baseDir);
			String substr = null;
			if (dirs.length > 1) {
				for (int i = 0; i < dirs.length - 1; i++) {
					substr = dirs[i];
					try {
						// substr.trim();
						substr = new String(substr.getBytes("8859_1"), "GB2312");

					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ret = new File(ret, substr);

				}
				if (!ret.exists())
					ret.mkdirs();
				substr = dirs[dirs.length - 1];
				try {
					// substr.trim();
					substr = new String(substr.getBytes("8859_1"), "GB2312");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ret = new File(ret, substr);
				return ret;
			}
			return ret;
		}
		
		/**
		 * @Description: 读取Assists文件
		 * @param context
		 * @return
		 * @return String
		 */
		public static String getAssetsFileString(Context context, String fileName) {
			String jsonString = "";
			InputStreamReader mInputStreamReader = null;
			BufferedReader mbufReader = null;
			StringBuffer mStringBuffer = null;
			String temp = "";
			try {
				mInputStreamReader = new InputStreamReader(context.getResources()
						.getAssets().open(fileName));
				mbufReader = new BufferedReader(mInputStreamReader);
				mStringBuffer = new StringBuffer();
				while ((temp = mbufReader.readLine()) != null) {
					mStringBuffer.append(temp);
				}
				jsonString = mStringBuffer.toString().trim();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					mbufReader.close();
					mInputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return jsonString;

		}
		
		
}
