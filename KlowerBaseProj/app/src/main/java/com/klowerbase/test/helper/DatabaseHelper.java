package com.klowerbase.test.helper;

import java.io.File;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.klower.model.UserInfo;
import com.klower.utilities.LogTool;
import com.klower.utilities.Utils;

/**
 * Database helper class used to manage the creation and upgrading of your
 * database. This class also usually provides the DAOs used by the other
 * classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	// name of the database file for your application -- change to something
	// appropriate for your app
	private static final String DATABASE_NAME = "MYDB.db";

	private static final String DATABASE_JOURNAL_NAME = DATABASE_NAME
			+ "-journal";

	// any time you make changes to your database objects, you may have to
	// increase the database version
	private static final int DATABASE_VERSION = 4;

	// SDCard 路径
	private static String DATABASE_PATH = Environment
			.getExternalStorageDirectory() + "/" + DATABASE_NAME;

	private static String DATABASE_PATH_JOURN = Environment
			.getExternalStorageDirectory() + "/" + DATABASE_JOURNAL_NAME;

	private Context mContext;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mContext = context;

		// 以下代码表示将DB文件存储到自己定义的目录下
		initDtaBasePath();
		try {
			File f = new File(DATABASE_PATH);
			if (!f.exists()) {
				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
						DATABASE_PATH, null);
				onCreate(db);
				db.close();
			}
		} catch (Exception e) {
		}
	}

	// 判断SDCard是否存在
	private void initDtaBasePath() {
		if (!Utils.ExistSDCard()) {
			DATABASE_PATH = mContext.getFilesDir().getAbsolutePath() + "/"
					+ DATABASE_NAME;
			DATABASE_PATH_JOURN = mContext.getFilesDir().getAbsolutePath()
					+ "/" + DATABASE_JOURNAL_NAME;
		}
	}

	// 以下2个方法表示把DB 文件存储到SDCard目录
	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
		return SQLiteDatabase.openDatabase(DATABASE_PATH, null,
				SQLiteDatabase.OPEN_READWRITE);
	}

	public synchronized SQLiteDatabase getReadableDatabase() {
		return SQLiteDatabase.openDatabase(DATABASE_PATH, null,
				SQLiteDatabase.OPEN_READONLY);
	}

	private ConnectionSource connectionSource = null;

	@Override
	public ConnectionSource getConnectionSource() {
		if (connectionSource == null) {
			connectionSource = super.getConnectionSource();
		}
		return connectionSource;

	}

	/**
	 * This is called when the database is first created. Usually you should
	 * call createTable statements here to create the tables that will store
	 * your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		LogTool.i(DatabaseHelper.class.getName(), "onCreate");
		try {
			TableUtils.createTable(connectionSource, UserInfo.class);
		} catch (java.sql.SQLException e) {
			LogTool.e(DatabaseHelper.class.getName(), "Can't create database",
					e);
			throw new RuntimeException(e);
		}

	}

	/**
	 * This is called when your application is upgraded and it has a higher
	 * version number. This allows you to adjust the various data to match the
	 * new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		LogTool.i(DatabaseHelper.class.getName(), "onUpgrade");
		// Upgrade to Version 2
		if (oldVersion < 2) {
			// is_clicked
		}
		if (oldVersion < 3) {
		}
		if (oldVersion < 4) {
		}
	}

	public void deleteDB() {
		if (mContext != null) {
			File f = mContext.getDatabasePath(DATABASE_NAME);
			if (f.exists()) {
				// mContext.deleteDatabase(DATABASE_NAME);
				LogTool.e("DB", "---delete SDCard DB---");
				f.delete();
			} else {
				LogTool.e("DB", "---delete App DB---");
				mContext.deleteDatabase(DATABASE_NAME);
			}

			File file = mContext.getDatabasePath(DATABASE_PATH);
			if (file.exists()) {
				LogTool.e("DB", "---delete SDCard DB 222---");
				file.delete();
			}

			File file2 = mContext.getDatabasePath(DATABASE_PATH_JOURN);
			if (file2.exists()) {
				LogTool.e("DB", "---delete SDCard DB 333---");
				file2.delete();
			}
		}
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
	}
}
