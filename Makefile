JAVA = java
CC = $(JAVA)c
OPT =
VERSION =

FLAGS =
WARNINGS =
REMOVE_WARN =
HEADERS =
LIBS = -classpath ./libs/commons-io-2.6.jar:./libs/itext5-itextpdf-5.5.12.jar

INPUT = src/client/*.java \
		src/dataload/*.java \
		src/datamodel/*/*.java \
		src/engine/*.java \
		src/exporters/*.java \
		src/reporter/*.java

OUTPUT = src/client/*.class \
		 src/dataload/*.class \
		 src/datamodel/*/*.class \
		 src/engine/*.class \
		 src/exporters/*.class \
		 src/reporter/*.class

all: compiler

compiler:
	$(CC) $(OPT) $(VERSION) $(HEADERS) $(FLAGS) $(WARNINGS) $(REMOVE_WARN) $(LIBS) $(INPUT)
	@echo

run:
	cd src && $(JAVA) client/EntryPoint

clean:
	$(RM) -r $(OUTPUT)
