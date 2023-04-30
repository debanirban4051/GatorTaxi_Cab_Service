# Makefile for compiling and building a Java executable

# Compiler and flags
JAVAC = javac
JAVAC_FLAGS = -g

# File names
SOURCE_FILES = gatorTaxi.java
EXECUTABLE = myApp

# Default target
all: $(EXECUTABLE)

# Target for building the executable
$(EXECUTABLE): $(SOURCE_FILES)
	$(JAVAC) $(JAVAC_FLAGS) $(SOURCE_FILES)
	@echo "Java source files compiled successfully!"
	@echo "You can now run the application with: java gatorTaxi file_name"

# Target for cleaning up generated files
clean:
	rm -f *.class
	@echo "Cleaned up generated files."