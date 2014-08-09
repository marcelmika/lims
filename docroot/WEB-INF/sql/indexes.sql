create index IX_18B35C57 on Lims_Conversation (conversationId);

create index IX_2E82F95D on Lims_Message (cid);
create index IX_5B793B06 on Lims_Message (creatorId);

create index IX_65EC56A8 on Lims_Panel (userId);

create index IX_837592B1 on Lims_Participant (cid);
create index IX_409920D on Lims_Participant (cid, participantId);
create index IX_127A3F88 on Lims_Participant (participantId, isOpened);

create index IX_E24B63F4 on Lims_Settings (presence);
create index IX_2BE9443F on Lims_Settings (userId);