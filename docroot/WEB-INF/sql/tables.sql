create table Lims_Conversation (
	cid LONG not null primary key,
	conversationId VARCHAR(256) null,
	conversationType VARCHAR(75) null,
	updatedAt DATE null
);

create table Lims_Message (
	mid LONG not null primary key,
	cid LONG,
	creatorId LONG,
	createdAt DATE null,
	body TEXT null
);

create table Lims_Panel (
	pid LONG not null primary key,
	userId LONG,
	activePanelId VARCHAR(256) null
);

create table Lims_Participant (
	pid LONG not null primary key,
	cid LONG,
	participantId LONG,
	unreadMessagesCount INTEGER,
	isOpened BOOLEAN
);

create table Lims_Settings (
	sid LONG not null primary key,
	userId LONG,
	presence VARCHAR(75) null,
	mute BOOLEAN,
	chatEnabled BOOLEAN
);