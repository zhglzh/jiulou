INSERT INTO `users` (`id`, `name`, `full_name`, `password`, `login_count`, `last_login`, `email`, `admin`, `guest`, `hash`, `url`, `locale`) 
VALUES    
    (1, 'guest', 'Guest User', '', 0, 0, NULL, 0, 1, NULL, NULL, NULL), 
    (2, 'admin', 'Gallery Administrator', '', 0, 0, 'unknown@unknown.com', 1, 0, NULL, NULL, NULL) 
