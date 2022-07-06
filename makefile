PDFMerge: PDFMerge.jar
	echo '#!/usr/bin/java -jar'>PDFMerge
	cat PDFMerge.jar >> PDFMerge
	chmod +x PDFMerge

PDFMerge.jar: PDFMerge.java Libraries/*
	javac -cp  Libraries/itextpdf-5.5.13.3.jar:Libraries/bcpkix-jdk15on-1.70.jar:Libraries/bcprov-jdk15to18-1.71.jar:Libraries/xmlsec-3.0.0.jar:Libraries/barcodes-7.1.3.jar:Libraries/font-asian-7.1.3.jar:Libraries/forms-7.1.3.jar:Libraries/hyph-7.1.3.jar:Libraries/io-7.1.3.jar:Libraries/kernel-7.1.3.jar:Libraries/layout-7.1.3.jar:Libraries/pdfa-7.1.3.jar:Libraries/pdftest-7.1.3.jar:Libraries/sign-7.1.3.jar:Libraries/styled-xml-parser-7.1.3.jar:Libraries/svg-7.1.3.jar PDFMerge.java
	printf "Manifest-Version: 1.0\nClass-Path: Libraries/itextpdf-5.5.13.3.jar\n  Libraries/bcpkix-jdk15on-1.70.jar\n  Libraries/bcprov-jdk15to18-1.71.jar\n  Libraries/xmlsec-3.0.0.jar\n  Libraries/barcodes-7.1.3.jar\n  Libraries/font-asian-7.1.3.jar\n  Libraries/forms-7.1.3.jar\n  Libraries/hyph-7.1.3.jar\n  Libraries/io-7.1.3.jar\n  Libraries/kernel-7.1.3.jar\n  Libraries/layout-7.1.3.jar\n  Libraries/pdfa-7.1.3.jar\n  Libraries/pdftest-7.1.3.jar\n  Libraries/sign-7.1.3.jar\n  Libraries/styled-xml-parser-7.1.3.jar\n  Libraries/svg-7.1.3.jar\nMain-Class: PDFMerge\n" >> MANIFEST.MF
	jar -cvfm PDFMerge.jar ./MANIFEST.MF PDFMerge.class 

clean:
	rm *.class MANIFEST.MF PDFMerge.jar .makefile.swp  PDFMerge *.pdf