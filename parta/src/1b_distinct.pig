records = LOAD 'data/yago_full_clean.tsv' using PigStorage(' ') AS (subject:chararray, predicate:chararray, object:chararray);

--get all subjects with more than one livesIn predicate

lives_in_records = FILTER records BY predicate == '<livesIn>';
lives_in_groups = GROUP lives_in_records BY subject;
subject_li_count = FOREACH lives_in_groups GENERATE group as li_subject, COUNT(lives_in_records) AS count;
valid_subjets = FILTER subject_li_count BY count > 1;

--join this with hasGivenName

given_name_records = FILTER records BY predicate == '<hasGivenName>';
joined_records = JOIN valid_subjets BY li_subject, given_name_records BY subject;
objects = FOREACH joined_records GENERATE object;
distinct_objects = DISTINCT objects;

STORE distinct_objects INTO './output/1b_distinct';
