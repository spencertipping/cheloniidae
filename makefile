BUILD_DIR       = build
SRC_DIR         = src
BIN_DIR         = bin

TEX             = pdflatex
TEX_OPTS        =

SD              = ./$(BIN_DIR)/sd
SD_OPTS         = --scala

SCALAC          = scalac
SCALAC_OPTS     = -g:vars -optimise

CD              = cd
CP              = cp

all: doc classes

$(BUILD_DIR)/%.pdf: $(SRC_DIR)/%.tex $(SRC_DIR)/*.sty
	$(CP) $(SRC_DIR)/*.sty $(BUILD_DIR)/
	$(CD) $(BUILD_DIR) && $(TEX) $(TEX_OPTS) ../$< && $(TEX) $(TEX_OPTS) ../$<

$(BUILD_DIR)/%.class: $(SRC_DIR)/%.scala
	$(CD) $(BUILD_DIR) && $(SCALAC) $(SCALAC_OPTS) ../$<

$(SRC_DIR)/%.scala: $(SRC_DIR)/%.tex
	$(CD) $(BUILD_DIR) && ../$(SD) $(SD_OPTS) ../$<

doc:     $(BUILD_DIR)/cheloniidae.pdf
classes: $(BUILD_DIR)/cheloniidae.class
unlit:   $(SRC_DIR)/cheloniidae.scala
clean:
	$(RM) -r $(BUILD_DIR)/* $(SRC_DIR)/cheloniidae.scala
