INSERT INTO "doc" (
                   id, doc_client_name,
                   doc_creation_date,
                   doc_deadline_date,
                   doc_file_content,
                   doc_file_name,
                   doc_name,
                   doc_status,
                   doc_worker_name,
                   user_id,
                   is_deleted
                   )
                   VALUES

(1, 'Client1', '2012-12-12', '2013-12-12', lo_import('D:/1.txt'), 'file1.txt', 'Document1', 'COMPLETE', 'Worker1', 1, false),
(2, 'Client2', '2011-11-11', '2014-12-12', lo_import('D:/2.txt'), 'file2.txt', 'Document2', 'IN_WORK', 'Worker2', 2, false);
--Files 1.txt and 2.txt are in the static/test_files directory