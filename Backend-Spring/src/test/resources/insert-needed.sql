INSERT INTO roles (id, name) VALUES (1, 'Player') ON CONFLICT (id) DO NOTHING;