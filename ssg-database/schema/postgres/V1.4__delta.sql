ALTER TABLE HOMEWORK ADD MODULE_ID int4;

UPDATE homework h SET MODULE_ID=(SELECT hm.MODULE_ID FROM HOMEWORK_MODULE hm WHERE hm.HW_ID = h.ID);

DROP TABLE HOMEWORK_MODULE;