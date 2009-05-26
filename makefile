BUILD_DIR       = build
SRC_DIR         = src
BIN_DIR         = bin

TEX             = pdflatex
TEX_OPTS        =

SD              = ./$(BIN_DIR)/sd
SD_OPTS         = --scala

SCALAC          = scalac
SCALAC_OPTS     = -g:vars -d $(BUILD_DIR)/ -optimise

CD              = cd

$(BUILD_DIR)/%.pdf: $(SRC_DIR)/%.tex
	$(CD) $(BUILD_DIR) && $(TEX) $(TEX_OPTS) ../$< && $(TEX) $(TEX_OPTS) ../$<

$(BUILD_DIR)/%.scala: $(SRC_DIR)/%.tex
	$(CD) $(BUILD_DIR) && ../$(SD) $(SD_OPTS) ../$<

$(BUILD_DIR)/%.class: $(BUILD_DIR)/%.scala
	$(CD) $(BUILD_DIR) && $(SCALAC) $(SCALAC_OPTS) ../$<

doc:     $(BUILD_DIR)/cheloniidae.pdf
classes: $(BUILD_DIR)/cheloniidae.class
unlit:   $(BUILD_DIR)/cheloniidae.scala
clean:
	$(RM) $(BUILD_DIR)/* $(SRC_DIR)/cheloniidae.scala
