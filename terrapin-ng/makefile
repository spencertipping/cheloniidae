# Compiles all of the TeX files into Java files. Also produces documentation
# PDFs.

DOC_DIRECTORY		= doc

MAKEFILE		= makefile

TEX			= pdflatex
TEX_OPTS		= -output-directory=$(DOC_DIRECTORY)

SD			= ./sd
SD_OPTS			= --java

TEX_DEPENDENCIES	= $(DOC_DIRECTORY)/spencer.sty

ALL_FILES		= $(subst .tex,,$(wildcard *.tex))
TEX_FILES		= $(addsuffix  .tex,$(ALL_FILES))
JAVA_FILES		= $(addsuffix .java,$(ALL_FILES))
PDF_FILES		= $(addprefix $(DOC_DIRECTORY)/,$(addsuffix  .pdf,$(ALL_FILES)))

.PHONY: all
all: src doc

.PHONY: src
src: $(JAVA_FILES)

.PHONY: doc
doc: $(PDF_FILES)

%.java: %.tex $(MAKEFILE)
	$(SD) $(SD_OPTS) $<

$(DOC_DIRECTORY)/%.pdf: %.tex $(MAKEFILE) $(TEX_DEPENDENCIES)
	# TeX is invoked twice to square away references.
	$(TEX) $(TEX_OPTS) $< && $(TEX) $(TEX_OPTS) $<
