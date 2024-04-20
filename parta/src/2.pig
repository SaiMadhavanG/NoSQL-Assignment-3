records = LOAD './data/yago_full_clean.tsv' USING PigStorage(' ') AS (subject:chararray, predicate:chararray, object:chararray);
REGISTER './jars/SPCount.jar';
records_group_all = GROUP records ALL;
records_sp_count = FOREACH records_group_all GENERATE SPCOUNT(records, '<Alice_Roberts>', '<isCitizenOf>');
STORE records_sp_count INTO './output/2';
