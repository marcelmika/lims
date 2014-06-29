create table LiferayLIMS_Buddy (
	bid LONG not null primary key,
	userId LONG,
	companyId LONG,
	portraitId LONG,
	fullName VARCHAR(75) null,
	screenName VARCHAR(75) null,
	statusMessage VARCHAR(75) null,
	isTyping BOOLEAN,
	awake BOOLEAN,
	status VARCHAR(75) null
);

create table LiferayLIMS_Conversation (
	cid LONG not null primary key,
	conversationId VARCHAR(75) null,
	conversationType VARCHAR(75) null
);

create table LiferayLIMS_Message (
	mid LONG not null,
	cid LONG not null,
	creatorId LONG,
	createdAt LONG,
	messageHash VARCHAR(75) null,
	text_ VARCHAR(75) null,
	primary key (mid, cid)
);

create table LiferayLIMS_OpenedConversation (
	ocid LONG not null primary key,
	userId LONG,
	conversationId VARCHAR(75) null
);

create table LiferayLIMS_Panel (
	pid LONG not null primary key,
	userId LONG,
	activePanelId VARCHAR(75) null
);

create table LiferayLIMS_Participant (
	pid LONG not null,
	cid LONG not null,
	participantId LONG,
	unreadMessagesCount INTEGER,
	isOpened BOOLEAN,
	primary key (pid, cid)
);

create table LiferayLIMS_Settings (
	sid LONG not null primary key,
	userId LONG,
	status VARCHAR(75) null,
	mute BOOLEAN,
	chatEnabled BOOLEAN
);