create index IX_912E8671 on LiferayLIMS_Conversation (userId);
create index IX_BF514F63 on LiferayLIMS_Conversation (userId, conversationId);

create index IX_CC60DBBA on LiferayLIMS_OpenedConversation (userId);
create index IX_BDA9F2AC on LiferayLIMS_OpenedConversation (userId, conversationId);

create index IX_DE463716 on LiferayLIMS_Panel (userId);

create index IX_ED764BBD on LiferayLIMS_Settings (status);
create index IX_8EF56511 on LiferayLIMS_Settings (userId);