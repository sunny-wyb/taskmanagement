package com.task.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.task.entity.Attachment;
import com.task.entity.SimpleTask;
import com.task.entity.Task;
import com.task.entity.TaskPackage;
import com.task.service.DBHelper;
import com.task.service.DataAccess;
import java.sql.PreparedStatement;

public class MySqlDataAccess implements DataAccess {

	// TODO log db exception

	@Override
	public void deleteAttachment(int attachmentDescCode)
			throws ClassNotFoundException, SQLException {
		Connection conn = DBHelper.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn
					.prepareStatement("delete from attachment where attachmentDescCode = ?");
			ps.setInt(1, attachmentDescCode);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			// TODO log
			throw e;
		}

		DBHelper.close(conn);
	}

	@Override
	public void deleteSimpleTask(int simpleTaskCode)
			throws ClassNotFoundException, SQLException {
		Connection conn = DBHelper.getConnection();
		try {
			PreparedStatement ps = conn
					.prepareStatement("delete from task where taskType = ? and code = ?");
			ps.setInt(1, 0);
			ps.setInt(2, simpleTaskCode);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			// TODO log
			throw e;
		}
		DBHelper.close(conn);
	}

	@Override
	public void deleteTaskPackage(int taskPackageCode)
			throws ClassNotFoundException, SQLException {
		Connection conn = DBHelper.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn
					.prepareStatement("delete from task where taskType = ? and code = ?");
			ps.setInt(1, 1);
			ps.setInt(2, taskPackageCode);
			ps.execute();
		} catch (SQLException e) {
			// TODO log
			throw e;
		}

		List<Integer> simpleTaskCodes = new ArrayList<Integer>();
		try {
			ps = conn
					.prepareStatement("select code , taskType from task where parentCode = ?");
			ps.setInt(1, taskPackageCode);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int code = rs.getInt(1);
				int taskType = rs.getInt(2);
				if (taskType == 1) {
					deleteTaskPackage(code);
				} else {
					simpleTaskCodes.add(code);
				}
			}
			deleteSimpleTasks(simpleTaskCodes);
			ps.close();
		} catch (SQLException e) {
			// TODO log
			throw e;
		}
		DBHelper.close(conn);
	}

	@Override
	public Attachment getAttachment(int attachmentDescCode)
			throws ClassNotFoundException, SQLException {
		Connection conn = DBHelper.getConnection();
		Attachment attachment = null;
		try {
			PreparedStatement ps = conn
					.prepareStatement("select code , attachmentClass , attachPath , realFilename , physicalFilename , comments , size , uploadTime from attachment_desc where code = ?");
			ps.setInt(1, attachmentDescCode);
			ResultSet rs = ps.executeQuery();
			if (!rs.next())
				return null;

			int code = rs.getInt(1);
			int attachmentClass = rs.getInt(2);
			String attachPath = rs.getString(3);
			String realFilename = rs.getString(4);
			String physicalFilename = rs.getString(5);
			String comments = rs.getString(6);
			//TODO filesize use int is it ok?
			long size = rs.getInt(7);
			Timestamp uploadTime = rs.getTimestamp(8);
			
			ps.close();
			attachment = new Attachment(code, attachmentClass, attachPath,
					realFilename, physicalFilename, comments, size , uploadTime);
		} catch (SQLException e) {
			// TODO log
			throw e;
		}
		DBHelper.close(conn);
		return attachment;
	}

	@Override
	public List<Attachment> getAttachments(int attachmentIndex)
			throws ClassNotFoundException, SQLException {
		Connection conn = DBHelper.getConnection();
		List<Attachment> ret = new ArrayList<Attachment>();
		try {
			PreparedStatement ps = conn
					.prepareStatement("select code , attachmentClass , attachPath , realFilename , physicalFilename , comments , size , uploadTime from attachment_desc where code in (select attachmentDescCode from attachment where attachmentIndex = ?)");
			ps.setInt(1, attachmentIndex);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int code = rs.getInt(1);
				int attachmentClass = rs.getInt(2);
				String attachPath = rs.getString(3);
				String realFilename = rs.getString(4);
				String physicalFilename = rs.getString(5);
				String comments = rs.getString(6);
				long size = rs.getInt(7);
				Timestamp uploadTime = rs.getTimestamp(8);
				
				Attachment attachment = new Attachment(code, attachmentClass,
						attachPath, realFilename, physicalFilename, comments, size , uploadTime);
				ret.add(attachment);
			}

			ps.close();
		} catch (SQLException e) {
			// TODO log
			throw e;
		}
		DBHelper.close(conn);
		return ret;
	}

	/*
	 * code int(10) auto_increment primary key, parentCode int(10), createTime
	 * Timestamptime, startTime Timestamptime, endTime TimestampTime,
	 * description varchar(2048), attachment int(10), taskType int(1)
	 */
	@Override
	public SimpleTask getSimpleTask(int simpleTaskCode)
			throws ClassNotFoundException, SQLException {
		Connection conn = DBHelper.getConnection();
		SimpleTask task = null;
		try {
			PreparedStatement ps = conn
					.prepareStatement("select code , parentCode , createTime , startTime , endTime , description , attachment , taskType , finishTime , isFinished from task where code = ? order by startTime ASC");
			ps.setInt(1, simpleTaskCode);
			ResultSet rs = ps.executeQuery();
			if (!rs.next())
				return null;
			int code = rs.getInt(1);
			int parentCode = rs.getInt(2);
			Timestamp createTime = rs.getTimestamp(3);
			Timestamp startTime = rs.getTimestamp(4);
			Timestamp endTime = rs.getTimestamp(5);
			String description = rs.getString(6);
			int attachment = rs.getInt(7);
			Timestamp finishTime = rs.getTimestamp(9);
			boolean isFinished = rs.getBoolean(10);
			ps.close();
			/*
			 * 	public SimpleTask(int code , Timestamp createTime , Timestamp startTime , Timestamp endTime , String description , int attachment , int parentCode , Timestamp finishTime , boolean isFinished) {

			 */
			task = new SimpleTask(code, createTime, startTime, endTime,
					description, attachment, parentCode , finishTime , isFinished);
		} catch (SQLException e) {
			// TODO log
			throw e;
		}
		DBHelper.close(conn);

		return task;
	}

	/*
	 * code int(10) auto_increment primary key, parentCode int(10), createTime
	 * datetime, startTime datetime, endTime dateTime, description
	 * varchar(2048), attachment int(10), taskType int(1)
	 */
	@Override
	public List<Task> getTasksByStartTime(Timestamp timestamp)
			throws ClassNotFoundException, SQLException {
		Connection conn = DBHelper.getConnection();
		List<Task> ret = new ArrayList<Task>();
		try {
			PreparedStatement ps = conn
					.prepareStatement("select code , parentCode , createTime , startTime , endTime , description , attachment , taskType , finishTime , isFinished from task where startTime = ? order by startTime ASC");
			ps.setTimestamp(1, timestamp);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int code = rs.getInt(1);
				int parentCode = rs.getInt(2);
				Timestamp createTime = rs.getTimestamp(3);
				Timestamp startTime = rs.getTimestamp(4);
				Timestamp endTime = rs.getTimestamp(5);
				String description = rs.getString(6);
				int attachment = rs.getInt(7);
				int taskType = rs.getInt(8);
				Timestamp finishTime = rs.getTimestamp(9);
				boolean isFinished = rs.getBoolean(10);
				Task task = null;
				if (taskType == 0) {
					task = new SimpleTask(code, createTime, startTime, endTime,
							description, attachment, parentCode , finishTime , isFinished);
				} else if (taskType == 1) {
					task = new TaskPackage(code, createTime, startTime,
							endTime, description, attachment, parentCode , finishTime , isFinished);
				}
				ret.add(task);
			}
			ps.close();
		} catch (SQLException e) {
			// TODO log
			throw e;
		}
		DBHelper.close(conn);
		return ret;
	}

	@Override
	public List<Task> getTasksByStartTime(Timestamp startTimestamp,
			Timestamp endTimestamp) throws SQLException, ClassNotFoundException {
		Connection conn = DBHelper.getConnection();
		List<Task> ret = new ArrayList<Task>();
		try {
			PreparedStatement ps = conn
					.prepareStatement("select code , parentCode , createTime , startTime , endTime , description , attachment , taskType , finishTime , isFinished from task where startTime > ? and startTime < ? order by startTime ASC");
			ps.setTimestamp(1, startTimestamp);
			ps.setTimestamp(2, endTimestamp);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int code = rs.getInt(1);
				int parentCode = rs.getInt(2);
				Timestamp createTime = rs.getTimestamp(3);
				Timestamp startTime = rs.getTimestamp(4);
				Timestamp endTime = rs.getTimestamp(5);
				String description = rs.getString(6);
				int attachment = rs.getInt(7);
				int taskType = rs.getInt(8);
				Timestamp finishTime = rs.getTimestamp(9);
				boolean isFinished = rs.getBoolean(10);
				Task task = null;
				if (taskType == 0) {
					task = new SimpleTask(code, createTime, startTime, endTime,
							description, attachment, parentCode , finishTime , isFinished);
				} else if (taskType == 1) {
					task = new TaskPackage(code, createTime, startTime, endTime,
							description, attachment, parentCode , finishTime , isFinished);
				}
				ret.add(task);
			}
			ps.close();

		} catch (SQLException e) {
			// TODO log
			throw e;
		}
		DBHelper.close(conn);
		return ret;
	}

	@Override
	public List<Task> getSubTasks(int taskPackageCode)
			throws ClassNotFoundException, SQLException {
		Connection conn = DBHelper.getConnection();
		List<Task> ret = new ArrayList<Task>();
		try {
			PreparedStatement ps = conn
					.prepareStatement("select code , parentCode , createTime , startTime , endTime , description , attachment , taskType , finishTime , isFinished from task where parentCode = ? order by startTime ASC");
			ps.setInt(1, taskPackageCode);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int code = rs.getInt(1);
				int parentCode = rs.getInt(2);
				Timestamp createTime = rs.getTimestamp(3);
				Timestamp startTime = rs.getTimestamp(4);
				Timestamp endTime = rs.getTimestamp(5);
				String description = rs.getString(6);
				int attachment = rs.getInt(7);
				int taskType = rs.getInt(8);
				Timestamp finishTime = rs.getTimestamp(9);
				boolean isFinished = rs.getBoolean(10);
				Task task = null;
				if (taskType == 0) {
					task = new SimpleTask(code, createTime, startTime, endTime,
							description, attachment, parentCode , finishTime , isFinished);

				} else {
					task = new TaskPackage(code, createTime, startTime, endTime,
							description, attachment, parentCode , finishTime , isFinished);
				}
				ret.add(task);
			}

			ps.close();
		} catch (SQLException e) {
			// TODO log
			throw e;
		}
		DBHelper.close(conn);

		return ret;
	}

	/*
	 * 
	 * create table attachment_desc( code int(10) auto_increment primary key,
	 * attachmentClass int(12) not null, attachPath varchar(255) not null,
	 * realFilename varchar(255) not null, physicalFilename varchar(255) not
	 * null, comments varchar(2048) );
	 */
	@Override
	public int saveAttachment(Attachment attachment) throws SQLException,
			ClassNotFoundException {
		Connection conn = DBHelper.getConnection();

		int code = attachment.getCode();
		if (code == -1) {
			try {
				PreparedStatement ps = conn
						.prepareStatement(
								"insert into attachment_desc(attachmentClass , attachPath , realFilename , physicalFilename , comments , size , uploadTime) values(?,?,?,?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, attachment.getAttachmentClass());
				ps.setString(2, attachment.getAttachPath());
				ps.setString(3, attachment.getRealFilename());
				ps.setString(4, attachment.getPhysicalFilename());
				ps.setString(5, attachment.getComments());
				ps.setInt(6, (int)attachment.getSize());
				ps.setTimestamp(7, attachment.getUploadTime());
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				if (!rs.next()) {
					code = -1;
				} else {
					code = rs.getInt(1);
				}
				ps.close();
			} catch (SQLException e) {
				// TODO log
				throw e;
			}
		} else {
			try {
				PreparedStatement ps = conn
						.prepareStatement("update attachment_desc set attachmentClass = ? , attachPath=? , realFilename=? , physicalFilename=? , comments=? , size = ? , uploadTime = ? where code = ?");
				ps.setInt(1, attachment.getAttachmentClass());
				ps.setString(2, attachment.getAttachPath());
				ps.setString(3, attachment.getRealFilename());
				ps.setString(4, attachment.getPhysicalFilename());
				ps.setString(5, attachment.getPhysicalFilename());
				ps.setInt(6, (int) attachment.getSize());
				ps.setTimestamp(7, attachment.getUploadTime());
				ps.setInt(8, code);
				ps.executeUpdate();
				ps.close();
			} catch (SQLException e) {
				// TODO log
				throw e;
			}
		}

		DBHelper.close(conn);

		return code;
	}

	/*
	 * create table task( code int(10) auto_increment primary key, parentCode
	 * int(10), createTime Timestamptime, startTime Timestamptime, endTime
	 * TimestampTime, description varchar(2048), attachment int(10), taskType
	 * int(1) );
	 */
	@Override
	public int saveTask(Task task) throws ClassNotFoundException, SQLException {
		Connection conn = DBHelper.getConnection();

		int code = task.getCode();
		if (code == -1) {
			int attachment = getNextSequenceNumber();
			try {
				PreparedStatement ps = conn
						.prepareStatement(
								"insert into task(parentCode , createTime , startTime , endTime , description , attachment , taskType , finishTime , isFinished) values(?,?,?,?,?,?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, task.getParentCode());
				ps.setTimestamp(2, task.getCreateTime());
				ps.setTimestamp(3, task.getStartTime());
				ps.setTimestamp(4, task.getEndTime());
				ps.setString(5, task.getDescription());
				ps.setInt(6, attachment);
				ps.setInt(7, task.getTaskType());
				ps.setTimestamp(8, task.getFinishTime());
				ps.setBoolean(9, task.isFinished());
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				if (!rs.next()) {
					code = -1;
				} else {
					code = rs.getInt(1);
				}
				ps.close();
			} catch (SQLException e) {
				// TODO log
				throw e;
			}
		} else {
			try {
				PreparedStatement ps = conn
						.prepareStatement("update task set parentCode = ? , createTime = ? , startTime = ?, endTime = ?, description = ?, attachment = ?, taskType = ? , finishTime = ? , isFinished = ? where code = ?");
				ps.setInt(1, task.getParentCode());
				ps.setTimestamp(2, task.getCreateTime());
				ps.setTimestamp(3, task.getStartTime());
				ps.setTimestamp(4, task.getEndTime());
				ps.setString(5, task.getDescription());
				ps.setInt(6, task.getAttachment());
				ps.setInt(7, task.getTaskType());
				ps.setTimestamp(8, task.getFinishTime());
				ps.setBoolean(9, task.isFinished());
				ps.setInt(10, task.getCode());
				ps.executeUpdate();
				ps.close();
			} catch (SQLException e) {
				// TODO log
				throw e;
			}
		}

		DBHelper.close(conn);

		return code;
	}

	@Override
	public void deleteSimpleTasks(List<Integer> simpleTaskCodes)
			throws ClassNotFoundException, SQLException {
		Connection conn = DBHelper.getConnection();
		String sql = "delete from task where code in (%s)";
		sql = String.format(sql, DBHelper.preparePlaceHolders(simpleTaskCodes
				.size()));
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			DBHelper.setValues(ps, simpleTaskCodes.toArray());
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			// TODO log
			throw e;
		}
		DBHelper.close(conn);
	}

	@Override
	public int getNextSequenceNumber() throws ClassNotFoundException,
			SQLException {
		Connection conn = DBHelper.getConnection();
		int ret = -1;
		try {
			PreparedStatement ps = conn.prepareStatement(
					"insert into sequence(temp) values('1')",
					Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();

			ps.close();
			if (!rs.next())
				ret = -1;
			else
				ret = rs.getInt(1);
		} catch (SQLException e) {
			// TODO log
			throw e;
		}
		DBHelper.close(conn);

		return ret;
	}

	@Override
	public Task getTask(int code_para) throws ClassNotFoundException, SQLException {
		Connection conn = DBHelper.getConnection();
		Task task = null;
		try {
			PreparedStatement ps = conn
					.prepareStatement("select code , parentCode , createTime , startTime , endTime , description , attachment , taskType , finishTime , isFinished from task where code = ? order by startTime ASC");
			ps.setInt(1, code_para);
			ResultSet rs = ps.executeQuery();
			if (!rs.next())
				return null;
			int code = rs.getInt(1);
			int parentCode = rs.getInt(2);
			Timestamp createTime = rs.getTimestamp(3);
			Timestamp startTime = rs.getTimestamp(4);
			Timestamp endTime = rs.getTimestamp(5);
			String description = rs.getString(6);
			int attachment = rs.getInt(7);
			int taskType = rs.getInt(8);
			Timestamp finishTime = rs.getTimestamp(9);
			boolean isFinished = rs.getBoolean(10);
			ps.close();
			/*
			 * 	public SimpleTask(int code , Timestamp createTime , Timestamp startTime , Timestamp endTime , String description , int attachment , int parentCode , Timestamp finishTime , boolean isFinished) {

			 */
			if (taskType == 0)
				task = new SimpleTask(code, createTime, startTime, endTime,
					description, attachment, parentCode , finishTime , isFinished);
			else 
				task = new TaskPackage(code, createTime, startTime, endTime,
						description, attachment, parentCode , finishTime , isFinished);
		} catch (SQLException e) {
			// TODO log
			throw e;
		}
		DBHelper.close(conn);

		return task;
	}

	@Override
	public void finishedTask(int code) throws ClassNotFoundException,
			SQLException {
		if (code == 2) return ;
		Task task = getTask(code);
		if (task.isSimpleTask()) {
			task.setFinished(true);
			task.setFinishTime(new Timestamp(System.currentTimeMillis()));
			saveTask(task);
			finishedTask(task.getParentCode());
		}
		else {
			List<Task> tasks = getSubTasks(code);
			for (Task subTask : tasks) {
				if (!(subTask.isFinished())) return ;
			}
			task.setFinished(true);
			saveTask(task);
			finishedTask(task.getParentCode());
		}
	}

	@Override
	public void saveAttachment(int attachmentIndex, Attachment attachment) throws ClassNotFoundException, SQLException {
		Connection conn = DBHelper.getConnection();
		PreparedStatement ps = conn.prepareStatement("insert into attachment(attachmentIndex , attachmentDescCode) values(?,?)");
		ps.setInt(1, attachmentIndex);
		ps.setInt(2, attachment.getCode());
		ps.execute();
	}

	@Override
	public void deleteAttachment(int attachmentIndex, Attachment attachment) throws SQLException, ClassNotFoundException {
		Connection conn = DBHelper.getConnection();
		PreparedStatement ps = conn.prepareStatement("delete from attachment where attachmentIndex = ? and attachmentDescCode = ?");
		ps.setInt(1, attachmentIndex);
		ps.setInt(2, attachment.getCode());
		ps.execute();
	}
}

