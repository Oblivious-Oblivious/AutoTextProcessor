JAVA = java
CC = $(JAVA)c
OPT =
VERSION =
OUTPUT = text_editor

FLAGS =
WARNINGS =
REMOVE_WARN =
HEADERS =
LIBS =

INPUT = src/client/*.java \
		src/dataload/*.java \
		src/datamodel/*/*.java \
		src/engine/*.java \
		src/exporters/*.java

OUTPUT = src/client/*.class \
		 src/dataload/*.class \
		 src/datamodel/*/*.class \
		 src/engine/*.class \
		 src/exporters/*.class \
		 src/test/*.class

all: compiler

compiler:
	$(CC) $(OPT) $(VERSION) $(HEADERS) $(FLAGS) $(WARNINGS) $(REMOVE_WARN) $(LIBS) $(INPUT)
	@echo

run:
	cd src && $(JAVA) client/Main

clean:
	$(RM) -r $(OUTPUT)
