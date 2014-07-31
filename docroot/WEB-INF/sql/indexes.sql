create index IX_520AD229 on LiferayLIMS_Conversation (conversationId);
create index IX_912E8671 on LiferayLIMS_Conversation (userId);
create index IX_BF514F63 on LiferayLIMS_Conversation (userId, conversationId);

create index IX_F054532F on LiferayLIMS_Message (cid);
create index IX_2C006F58 on LiferayLIMS_Message (creatorId);

create index IX_CC60DBBA on LiferayLIMS_OpenedConversation (userId);
create index IX_BDA9F2AC on LiferayLIMS_OpenedConversation (userId, conversationId);

create index IX_DE463716 on LiferayLIMS_Panel (userId);

create index IX_E681B383 on LiferayLIMS_Participant (cid);
create index IX_ECE172FB on LiferayLIMS_Participant (cid, participantId);
create index IX_AA3C32B3 on LiferayLIMS_Participant (participantId);
create index IX_EBAFC35A on LiferayLIMS_Participant (participantId, isOpened);

create index IX_ED764BBD on LiferayLIMS_Settings (status);
create index IX_8EF56511 on LiferayLIMS_Settings (userId);

create index IX_F3CB5477 on lims_Conversation (conversationId);

create index IX_E3CF317D on lims_Message (cid);
create index IX_E182DB26 on lims_Message (creatorId);

create index IX_5A272288 on lims_Panel (userId);

create index IX_C9A8BAD1 on lims_Participant (cid);
create index IX_2E5D25ED on lims_Participant (cid, participantId);
create index IX_BAA817A8 on lims_Participant (participantId, isOpened);

create index IX_D09D530B on lims_Settings (status);
create index IX_721C6C5F on lims_Settings (userId);