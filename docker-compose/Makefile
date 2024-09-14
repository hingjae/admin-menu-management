local-db-up:
	docker compose -f ./local/docker-compose-local.yml --env-file ./.env up -d
local-db-down:
	docker compose -f ./local/docker-compose-local.yml --env-file ./.env down --volumes
test-db-up:
	docker compose -f ./test/docker-compose-test.yml --env-file ./.env up -d
test-db-down:
	docker compose -f ./test/docker-compose-test.yml --env-file ./.env down --volumes
