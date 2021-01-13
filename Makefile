JAVA = java
CC = $(JAVA)c
OPT =
VERSION =

FLAGS =
WARNINGS =
REMOVE_WARN =
HEADERS =
LIBS = -classpath ../../resources/junit-4.13.1.jar:./libs/commons-io-2.6.jar:./libs/itext5-itextpdf-5.5.12.jar

INPUT = src/**/*.java
OUTPUT = src/**/*.class

all: compiler

compiler:
	$(CC) $(OPT) $(VERSION) $(HEADERS) $(FLAGS) $(WARNINGS) $(REMOVE_WARN) $(LIBS) $(INPUT)
	@echo

run:
	cd src && $(JAVA) client/EntryPoint

#test: compiler
#	cd src && $(JAVA) org.junit.runner.JUnitCore ./test/*/*/*.class

clean:
	$(RM) $(OUTPUT) existing.txt newfile.txt test test_document_raw.txt test_document_annotated.html
