install:
		./gradlew clean install
run-dist:
		./build/install/app/bin/app
check-updates:
		./gradlew dependencyUpdates
lint:
	./gradlew checkstyleMain checkstyleTest
build:
	./gradlew clean build
build-run: build run

.PHONY: build