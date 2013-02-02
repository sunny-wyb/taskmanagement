create table task(
	code int(10) auto_increment primary key,
	parentCode int(10),
	createTime datetime,
	startTime datetime,
	endTime dateTime,
	finishTime dateTime,
	description varchar(2048),
	attachment int(10),
	taskType int(1),
	isFinished boolean
)AUTO_INCREMENT = 3;

create table attachment_desc(
	code int(10) auto_increment primary key,
	attachmentClass int(12) not null,
	attachPath varchar(255) not null,
	realFilename varchar(255) not null,
	physicalFilename varchar(255) not null,
	size int(15),
	uploadTime datetime,
	comments varchar(2048)
);

create table attachment(
	attachmentIndex int(10),
	attachmentDescCode int(10)
);

create table sequence (
	code int(12) auto_increment primary key,
	temp char(1)
);