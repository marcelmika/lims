create table lims_Conversation (
	cid LONG not null primary key,
	conversationId VARCHAR(256) null,
	conversationType VARCHAR(75) null,
	updatedAt DATE null
);

create table lims_Message (
	mid LONG not null primary key,
	cid LONG,
	creatorId LONG,
	createdAt DATE null,
	messageHash VARCHAR(75) null,
	body TEXT null
);

create table lims_Panel (
	pid LONG not null primary key,
	userId LONG,
	activePanelId VARCHAR(256) null
);

create table lims_Participant (
	pid LONG not null primary key,
	cid LONG,
	participantId LONG,
	unreadMessagesCount INTEGER,
	isOpened BOOLEAN
);

create table lims_Settings (
	sid LONG not null primary key,
	userId LONG,
	presence VARCHAR(75) null,
	mute BOOLEAN,
	chatEnabled BOOLEAN
);