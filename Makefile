ifneq (,$(wildcard .env))
include .env
export
endif

local-db-up:
	docker compose -f $(DOCKER_LOCAL_PATH)/docker-compose-local.yml --env-file .env up -d
local-db-down:
	docker compose -f $(DOCKER_LOCAL_PATH)/docker-compose-local.yml --env-file .env down --volumes
test-db-up:
	docker compose -f $(DOCKER_TEST_PATH)/docker-compose-test.yml --env-file .env up -d
test-db-down:
	docker compose -f $(DOCKER_TEST_PATH)/docker-compose-test.yml --env-file .env down --volumes
