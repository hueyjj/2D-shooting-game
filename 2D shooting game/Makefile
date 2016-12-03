JAVAC      = javac 
MAINCLASS  = TestRun
JAVASRC    = $(wildcard *.java)
SOURCES    = $(JAVASRC) makefile README
FLAGS	   = -Xlint
CLASSES    = $(patsubst %.java, %.class, $(JAVASRC))
JARCLASSES = $(patsubst %.class, %*.class, $(CLASSES))
JARFILE    = $(MAINCLASS) 


all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(JARCLASSES)
	chmod +x $(JARFILE)
	rm Manifest

%.class: %.java
	$(JAVAC) $(FLAGS) $<

clean:
	rm *.class $(JARFILE)
