INSERT INTO user (user_id, user_fullname, user_name, user_password, user_role) VALUES (1, "TonyMonatana", "Tony", "$2y$10$mp0UK/hRFQ57hBluSGY5ou9jC1xG66XnpgbLHVtFrc5J7IGxF/uz6", "ADMIN");
INSERT INTO user (user_id, user_fullname, user_name, user_password, user_role) VALUES (2, "FranckSerpico", "Franck", "$2y$10$dJVisOd9SG5YFucSVbgPF.9Lm1QoJS4Saie0faM/RXjZT0kdDqmy6", "USER");

INSERT INTO bid (bid_id, account, type, bid_quantity) VALUES (1, "GamblingAccount", "Gambling", 500.00);
INSERT INTO bid (bid_id, account, type, bid_quantity) VALUES (2, "SavingAccount", "Saving", 100.00);

INSERT INTO curve_point (curve_point_id, curve_id, term) VALUES (1, 1, 10.0);
INSERT INTO curve_point (curve_point_id, curve_id, term) VALUES (2, 2, 20.0);

INSERT INTO rating (rating_id, moodys_rating) VALUES (1, "Aa1");
INSERT INTO rating (rating_id, moodys_rating) VALUES (2, "Aa2");

INSERT INTO rule (rule_id, name) VALUES (1, "TallionRule");
INSERT INTO rule (rule_id, name) VALUES (2, "StrongerRule");

INSERT INTO trade (trade_id, account) VALUEs (1, "NbaAccount");
INSERT INTO trade (trade_id, account) VALUEs (2, "MlbAccount");
