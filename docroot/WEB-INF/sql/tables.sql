create table Lims_Conversation (
	cid LONG not null primary key,
	conversationId VARCHAR(255) null,
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
	activePanelId VARCHAR(255) null
);

create table Lims_Participant (
	pid LONG not null primary key,
	cid LONG,
	participantId LONG,
	unreadMessagesCount INTEGER,
	isOpened BOOLEAN,
	openedAt LONG
);

create table Lims_Settings (
	sid LONG not null primary key,
	userId LONG,
	presence VARCHAR(75) null,
	presenceUpdatedAt LONG,
	mute BOOLEAN,
	chatEnabled BOOLEAN,
	adminAreaOpened BOOLEAN
);