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
	userId LONG,
	conversationId VARCHAR(75) null,
	conversationType VARCHAR(75) null,
	conversationVisibility VARCHAR(75) null,
	conversationName VARCHAR(75) null,
	unreadMessages INTEGER
);

create table LiferayLIMS_OpenedConversation (
	ocid LONG not null primary key,
	userId LONG,
	conversationId VARCHAR(75) null
);

create table LiferayLIMS_Settings (
	sid LONG not null primary key,
	userId LONG,
	status VARCHAR(75) null,
	activeRoomType VARCHAR(75) null,
	activePanelId VARCHAR(75) null,
	mute BOOLEAN,
	chatEnabled BOOLEAN
);