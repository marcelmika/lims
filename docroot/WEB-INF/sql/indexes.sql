create index IX_F3CB5477 on lims_Conversation (conversationId);

create index IX_E3CF317D on lims_Message (cid);
create index IX_E182DB26 on lims_Message (creatorId);

create index IX_5A272288 on lims_Panel (userId);

create index IX_C9A8BAD1 on lims_Participant (cid);
create index IX_2E5D25ED on lims_Participant (cid, participantId);
create index IX_BAA817A8 on lims_Participant (participantId, isOpened);

create index IX_68550414 on lims_Settings (presence);
create index IX_721C6C5F on lims_Settings (userId);