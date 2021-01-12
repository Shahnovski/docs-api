INSERT INTO "doc" (
                   id, doc_client_name,
                   doc_creation_date,
                   doc_deadline_date,
                   doc_file_content,
                   doc_file_name,
                   doc_name,
                   doc_status,
                   doc_worker_name,
                   user_id
                   )
                   VALUES

(1, 'Client 1', '2012-12-12', '2013-12-12', lo_import('D:/1.txt'), 'file1.txt', 'Document 1', 'COMPLETE', 'Worker 1', 1),
(2, 'Client 2', '2011-11-11', '2014-12-12', lo_import('D:/2.txt'), 'file2.txt', 'Document 2', 'IN_WORK', 'Worker 2', 2);
--Files 1.txt and 2.txt are in the static/test_files directory