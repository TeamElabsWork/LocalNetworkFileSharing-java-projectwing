.PHONY: help setup build test check demo gui clean

MVN ?= mvn

help:
	@echo "Local Network File Sharing - development commands"
	@echo ""
	@echo "  make setup   Build and install all modules locally (first time, and after pulling changes in other modules)"
	@echo "  make build   Compile everything (skip tests)"
	@echo "  make test    Run all unit tests in all modules"
	@echo "  make check   Full verification (compile + tests) - run before opening a PR"
	@echo "  make demo    Interactive CLI demo with mock backends (no network needed)"
	@echo "  make gui     Start the JavaFX UI"
	@echo "  make clean   Remove build output"
	@echo ""
	@echo "No 'make' (Windows)? Run the mvn commands from this file directly."

setup:
	$(MVN) -B -q install -DskipTests

build:
	$(MVN) -B -q compile

test:
	$(MVN) -B test

check:
	$(MVN) -B verify

demo:
	$(MVN) -q -pl apps/fileshare-app exec:java

gui:
	$(MVN) -q -pl apps/fileshare-app exec:java -Dexec.args="--gui"

clean:
	$(MVN) -B -q clean
