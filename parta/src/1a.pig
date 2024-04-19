lines = LOAD 'data/yago_full_clean.tsv' using PigStorage(' ') AS (subject:chararray, predicate:chararray, object:chararray);
predicate_groups = GROUP lines BY predicate;
predicate_counts = FOREACH predicate_groups GENERATE group AS predicate, COUNT(lines) AS count;
ordered_predicate_counts = ORDER predicate_counts BY count DESC, predicate ASC;
top10 = LIMIT ordered_predicate_counts 10;
STORE top10 INTO './1a';
