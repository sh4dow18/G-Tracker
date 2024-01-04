INSERT INTO roles (id, name) VALUES (1, 'Player') ON CONFLICT (id) DO NOTHING;

INSERT INTO action_types (id, name) VALUES (1, 'Register') ON CONFLICT (id) DO NOTHING;
INSERT INTO action_types (id, name) VALUES (2, 'Update') ON CONFLICT (id) DO NOTHING;
INSERT INTO action_types (id, name) VALUES (3, 'Delete') ON CONFLICT (id) DO NOTHING;
INSERT INTO action_types (id, name) VALUES (4, 'Send') ON CONFLICT (id) DO NOTHING;