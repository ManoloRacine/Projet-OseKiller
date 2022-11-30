package com.osekiller.projet.pdfutils;

import com.osekiller.projet.controller.payload.request.EvaluationDto;
import com.osekiller.projet.controller.payload.request.QuestionAnswerDto;
import com.osekiller.projet.controller.payload.request.StudentEvaluationDto;
import com.osekiller.projet.controller.payload.request.StudentEvaluationSection;
import com.osekiller.projet.model.Contract;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Manager;
import com.osekiller.projet.model.user.Student;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PdfUtils {

    private static final PDFont FONT = PDType1Font.TIMES_ROMAN;
    private static final float FONT_SIZE = 12;
    private static final float LEADING = -1.5f * FONT_SIZE;

    public void writeValuesInStudentEvaluation(StudentEvaluationDto dto, Contract contract, PDDocument pdfDocument) throws IOException {
        PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();

        PDField fieldCompanyName = acroForm.getField( "companyName" );
        fieldCompanyName.setValue(contract.getOffer().getOwner().getName());

        PDField fieldCompanyContact = acroForm.getField( "supervisorName" );
        fieldCompanyContact.setValue(dto.supervisorName());

        PDField fieldProgram = acroForm.getField( "program" );
        fieldProgram.setValue("TO ADD");

        PDField fieldPhoneNumber = acroForm.getField( "phoneNumber" );
        fieldPhoneNumber.setValue(dto.phoneNumber());

        PDField fieldStudentName = acroForm.getField( "studentName" );
        fieldStudentName.setValue(contract.getStudent().getName());

        acroForm.flatten() ;
    }

    public void addParagraph(PDPageContentStream contentStream, float width, float sx,
                              float sy, String text, boolean justify) throws IOException {
        List<String> lines = parseLines(text, width);
        contentStream.setFont(FONT, FONT_SIZE);
        contentStream.newLineAtOffset(sx, sy);
        for (String line: lines) {
            float charSpacing = 0;
            if (justify){
                if (line.length() > 1) {
                    float size = FONT_SIZE * FONT.getStringWidth(line) / 1000;
                    float free = width - size;
                    if (free > 0 && !lines.get(lines.size() - 1).equals(line)) {
                        charSpacing = free / (line.length() - 1);
                    }
                }
            }
            contentStream.setCharacterSpacing(charSpacing);
            contentStream.showText(line);
            contentStream.newLineAtOffset(0, LEADING);
        }
    }

    public void writeTasksInPdf(List<String> contractTasks, PDPageContentStream contentStream, PDPage pdPage) throws IOException {

        PDRectangle mediaBox = pdPage.getMediaBox();

        float marginY = 80;
        float marginX = 60;
        float width = mediaBox.getWidth() - 2 * marginX;
        float startX = mediaBox.getLowerLeftX() + marginX;
        float startY = mediaBox.getUpperRightY() - marginY;

        contentStream.beginText();

        addParagraph(contentStream, width, startX, startY, "TACHES ET RESPONSABILITES DU STAGIAIRE", true);

        for (String task : contractTasks) {
            System.out.println(task);
            addParagraph(contentStream, width, 0, -FONT_SIZE, "- " + task, true);
        }

        contentStream.endText();
    }

    public void writeValuesInPdf(Student student, Offer offer, Manager manager, PDDocument pdfDocument) throws IOException {

        PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();

        PDField fieldManager = acroForm.getField( "nom_gestionnaire" );
        fieldManager.setValue(manager.getName());

        PDField fieldEtudiant = acroForm.getField( "nom_etudiant" );
        fieldEtudiant.setValue(student.getName());

        PDField fieldCompany = acroForm.getField( "nom_employeur" );
        fieldCompany.setValue(offer.getOwner().getName());

        PDField fieldStart = acroForm.getField( "start_date" );
        fieldStart.setValue(offer.getStartDate().toString());

        PDField fieldEnd = acroForm.getField( "end_date" );
        fieldEnd.setValue(offer.getEndDate().toString());

        PDField fieldSalary = acroForm.getField( "offre_tauxHoraire" );
        fieldSalary.setValue(String.valueOf(offer.getSalary()));

        PDField fieldCompanySign = acroForm.getField( "nom_employeur_sign" );
        fieldCompanySign.setValue(offer.getOwner().getName());

        PDField fieldManagerSign = acroForm.getField( "nom_gestionnaire_sign" );
        fieldManagerSign.setValue(manager.getName());

        PDField fieldEtudiantSign = acroForm.getField( "nom_etudiant_sign" );
        fieldEtudiantSign.setValue(student.getName());

        acroForm.flatten() ;
    }

    //https://memorynotfound.com/apache-pdfbox-adding-multiline-paragraph/


    //https://memorynotfound.com/apache-pdfbox-adding-multiline-paragraph/
    public static List<String> parseLines(String text, float width) throws IOException {
        List<String> lines = new ArrayList<String>();
        int lastSpace = -1;
        while (text.length() > 0) {
            int spaceIndex = text.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0)
                spaceIndex = text.length();
            String subString = text.substring(0, spaceIndex);
            float size = FONT_SIZE * FONT.getStringWidth(subString) / 1000;
            if (size > width) {
                if (lastSpace < 0){
                    lastSpace = spaceIndex;
                }
                subString = text.substring(0, lastSpace);
                lines.add(subString);
                text = text.substring(lastSpace).trim();
                lastSpace = -1;
            } else if (spaceIndex == text.length()) {
                lines.add(text);
                text = "";
            } else {
                lastSpace = spaceIndex;
            }
        }
        return lines;
    }

    public void writePageTitle(PDPageContentStream contentStream, String Title,  PDPage page) throws IOException {
        PDRectangle mediaBox = page.getMediaBox();

        float marginY = 80;
        float marginX = 60;
        float width = mediaBox.getWidth() - 2 * marginX;
        float startX = mediaBox.getLowerLeftX() + marginX;
        float startY = mediaBox.getUpperRightY() - marginY;

        contentStream.beginText();

        addParagraph(contentStream, width, startX, startY, Title, true);

        contentStream.endText();
    }

    public void generateSignaturePagePdf(PDDocument pdf, PDPage page, Contract contract) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(pdf, page); //Create Stream

        PDRectangle mediaBox = page.getMediaBox();

        float marginY = 80;
        float marginX = 60;
        float width = mediaBox.getWidth() - 2 * marginX;
        float startX = mediaBox.getLowerLeftX() + marginX;
        float startY = mediaBox.getUpperRightY() - marginY;

        contentStream.beginText();

        addParagraph(contentStream, width, startX, startY, "SIGNATURES", true);

        addParagraph(contentStream, width, 0, -FONT_SIZE, "Signature du gestionnaire de stage: " + contract.getManager().getName(), true);

        addParagraph(
                contentStream,
                width,
                0,
                -FONT_SIZE * 7,
                contract.getManagerSigningDate() == null ? "En attente de signature" : "Signé le: " + contract.getManagerSigningDate(),
                true);


        addParagraph(contentStream, width, 0, -FONT_SIZE, "Signature de l'employeur: " + contract.getOffer().getOwner().getName(), true);

        addParagraph(
                contentStream,
                width,
                0,
                -FONT_SIZE * 7,
                contract.getCompanySigningDate() == null ? "En attente de signature" : "Signé le: " + contract.getCompanySigningDate(),
                true);

        addParagraph(contentStream, width, 0, -FONT_SIZE, "Signature de l'étudiant: " + contract.getStudent().getName(), true);

        addParagraph(
                contentStream,
                width,
                0,
                -FONT_SIZE * 7,
                contract.getStudentSigningDate() == null ? "En attente de signature" : "Signé le: " + contract.getStudentSigningDate(),
                true);

        contentStream.endText();

        if(contract.getManagerSigningDate() != null) {
            PDImageXObject pdImage = PDImageXObject.createFromByteArray(
                    pdf,
                    contract.getManager().getSignature(),
                    "manager-signature"
            );

            contentStream.drawImage(pdImage, startX, FONT_SIZE * 50, (float) ((70 * pdImage.getWidth()) / pdImage.getHeight()), 70);
        }

        if(contract.getCompanySigningDate() != null) {
            PDImageXObject pdImage = PDImageXObject.createFromByteArray(
                    pdf,
                    contract.getOffer().getOwner().getSignature(),
                    "company-signature"
            );

            contentStream.drawImage(pdImage, startX, FONT_SIZE * 39, (float) ((70 * pdImage.getWidth()) / pdImage.getHeight()), 70);
        }

        if(contract.getStudentSigningDate() != null) {
            PDImageXObject pdImage = PDImageXObject.createFromByteArray(
                    pdf,
                    contract.getStudent().getSignature(),
                    "student-signature"
            );

            contentStream.drawImage(pdImage, startX, FONT_SIZE * 28, (float) ((70 * pdImage.getWidth()) / pdImage.getHeight()), 70);
        }

        contentStream.close(); //Close Stream
    }

    public void writeValuesInEvaluationPdf(EvaluationDto evaluationDto, Contract contract, PDDocument pdfDocument) throws IOException {

        PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();

        PDField fieldCompanyName = acroForm.getField( "companyName" );
        fieldCompanyName.setValue(contract.getOffer().getOwner().getName());

        PDField fieldCompanyContact = acroForm.getField( "companyContact" );
        fieldCompanyContact.setValue(evaluationDto.companyContact());

        PDField fieldAddress = acroForm.getField( "address" );
        fieldAddress.setValue(evaluationDto.address());

        PDField fieldCity = acroForm.getField( "city" );
        fieldCity.setValue(evaluationDto.city());

        PDField fieldPostalCode = acroForm.getField( "postalCode" );
        fieldPostalCode.setValue(evaluationDto.postalCode());

        PDField fieldPhoneNumber = acroForm.getField( "phoneNumber" );
        fieldPhoneNumber.setValue(evaluationDto.phoneNumber());

        PDField fieldFax = acroForm.getField( "fax" );
        fieldFax.setValue(evaluationDto.fax());

        PDField fieldStudentName = acroForm.getField( "studentName" );
        fieldStudentName.setValue(contract.getStudent().getName());

        PDField fieldInternshipDate = acroForm.getField( "internshipDate" );
        fieldInternshipDate.setValue(contract.getOffer().getStartDate().toString());

        PDField fieldInternshipNo = acroForm.getField( "internshipNo" );
        fieldInternshipNo.setValue(evaluationDto.internshipNo().toString());

        acroForm.flatten() ;
    }

    public void writeQuestionsAndAnswers(EvaluationDto dto, PDDocument pdfDocument) throws IOException {
        PDPage pdPage = pdfDocument.getPage(1) ;
        PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, pdPage);

        PDRectangle mediaBox = pdPage.getMediaBox();

        float marginY = 80;
        float marginX = 60;
        float width = mediaBox.getWidth() - 2 * marginX;
        float startX = mediaBox.getLowerLeftX() + marginX;
        float startY = mediaBox.getUpperRightY() - marginY;

        contentStream.beginText();

        addParagraph(contentStream, width, startX, startY, "EVALUATION", true);

        for (QuestionAnswerDto questionAnswerDto : dto.evaluation()) {
            addParagraph(contentStream, width, 0, -FONT_SIZE, "-" + questionAnswerDto.question() + " : " + getAnswerString(questionAnswerDto.answer()), true);
        }

        addParagraph(contentStream, width, 0, -FONT_SIZE, "COMMENTAIRES : " + dto.comment(), true);

        contentStream.endText();

        contentStream.close();
    }

    public void writeObservations(EvaluationDto dto, PDDocument pdfDocument) throws IOException {
        PDPage pdPage = pdfDocument.getPage(2) ;
        PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, pdPage);

        PDRectangle mediaBox = pdPage.getMediaBox();
        float marginY = 80;
        float marginX = 60;
        float width = mediaBox.getWidth() - 2 * marginX;
        float startX = mediaBox.getLowerLeftX() + marginX;
        float startY = mediaBox.getUpperRightY() - marginY;

        contentStream.beginText();
        addParagraph(contentStream, width, startX, startY, "OBSERVATIONS GÉNÉRALES", true);

        addParagraph(contentStream, width, 0, -FONT_SIZE, "Ce milieu est à privilégier pour le stage : " + dto.preferredInternship(), true);

        addParagraph(contentStream, width, 0, -FONT_SIZE, "Ce milieu est ouvert à accueillir : " + dto.internNbs() + " stagiaire(s)", true);

        addParagraph(contentStream, width, 0, -FONT_SIZE, "Ce milieu désire accueillir le même stagiaire pour un prochain stage : " + (dto.keepIntern() ? "Oui" : "Non"), true);

        addParagraph(contentStream, width, 0, -FONT_SIZE, "Ce milieu désire accueillir le même stagiaire pour un prochain stage : " + (dto.keepIntern() ? "Oui" : "Non"), true);

        addParagraph(contentStream, width, 0, -FONT_SIZE, "Ce milieu offre des quarts de travail variables: " + (dto.variableWorkShifts() ? "Oui" : "Non"), true);

        if (dto.variableWorkShifts()) {
            for (List<String> workShift : dto.workShifts()) {
                if (workShift.get(0) != null && workShift.get(1) != null) {
                    addParagraph(contentStream, width, 0, -FONT_SIZE, workShift.get(0) + " - " + workShift.get(1), true);
                }
            }
        }

        addParagraph(contentStream, width, 0, -FONT_SIZE, "Date : " + LocalDate.now(), true);

        contentStream.endText();

        contentStream.close();
    }

    public void writeSectionEvaluation(StudentEvaluationSection section, int pageIndex, PDDocument pdfDocument, String sectionName) throws IOException {
        PDPage pdPage = pdfDocument.getPage(pageIndex) ;
        PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, pdPage);

        PDRectangle mediaBox = pdPage.getMediaBox();
        float marginY = 80;
        float marginX = 60;
        float width = mediaBox.getWidth() - 2 * marginX;
        float startX = mediaBox.getLowerLeftX() + marginX;
        float startY = mediaBox.getUpperRightY() - marginY;

        contentStream.beginText();
        addParagraph(contentStream, width, startX, startY, sectionName, true);

        for (QuestionAnswerDto questionAnswerDto : section.questionsAnswers()) {
            addParagraph(contentStream, width, 0, -FONT_SIZE, "-" + questionAnswerDto.question() + " : " + getAnswerString(questionAnswerDto.answer()), true);
        }

        addParagraph(contentStream, width, 0, -FONT_SIZE, "COMMENTAIRES : " + section.comments(), true);

        contentStream.endText();

        contentStream.close();
    }

    public void writeGlobalAppreciation(StudentEvaluationDto dto, int pageIndex, PDDocument pdfDocument) throws IOException {
        PDPage pdPage = pdfDocument.getPage(pageIndex) ;
        PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, pdPage);

        PDRectangle mediaBox = pdPage.getMediaBox();
        float marginY = 80;
        float marginX = 60;
        float width = mediaBox.getWidth() - 2 * marginX;
        float startX = mediaBox.getLowerLeftX() + marginX;
        float startY = mediaBox.getUpperRightY() - marginY;

        contentStream.beginText();
        addParagraph(contentStream, width, startX, startY, "APPRÉCIATION GLOBALE", true);

        addParagraph(contentStream, width, 0, -FONT_SIZE, "-Les habiletés démontrées : " + getAbilityString(dto.expectationsAchieved()), true);

        addParagraph(contentStream, width, 0, -FONT_SIZE, "Appréciation : " + dto.expectationsComment(), true);

        addParagraph(contentStream, width, 0, -FONT_SIZE, dto.internInformed() ? "Cette évaluation a été discutée avec le stagiaire " :
                "Cette évaluation n'a pas été discutée avec le stagiaire" , true);

        addParagraph(contentStream, width, 0, -FONT_SIZE,
                "Le nombre d'heures réel par semain d'encadrement accordé au stagiaire : " + dto.hoursOfSupportPerWeek(), true);

        contentStream.endText();

        contentStream.close();
    }

    public void writeNextInternship(StudentEvaluationDto dto, int pageIndex, PDDocument pdfDocument) throws IOException {
        PDPage pdPage = pdfDocument.getPage(pageIndex) ;
        PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, pdPage);

        PDRectangle mediaBox = pdPage.getMediaBox();
        float marginY = 80;
        float marginX = 60;
        float width = mediaBox.getWidth() - 2 * marginX;
        float startX = mediaBox.getLowerLeftX() + marginX;
        float startY = mediaBox.getUpperRightY() - marginY;

        contentStream.beginText();
        addParagraph(contentStream, width, startX, startY, "APPRÉCIATION GLOBALE", true);

        addParagraph(contentStream, width, 0, -FONT_SIZE,
                "L'entreprise aimerait accueillir cet élève pour son prochain stage : " + getKeepInternString(dto.keepIntern()) , true);

        addParagraph(contentStream, width, 0, -FONT_SIZE,
                "La formation technique du stagiaire était-elle suffisante pour accomplir le mandat de stage? : " + dto.expectationsComment(), true);

        contentStream.endText();

        contentStream.close();
    }

    private String getKeepInternString(int value) {
        return switch (value) {
            case 0 -> "Oui";
            case 1 -> "Non";
            case 2 -> "Peut-être";
            default -> "ERROR";
        };
    }

    private String getAbilityString(int value) {
        return switch (value) {
            case 0 -> "ne répondent pas aux attentes";
            case 1 -> "répondent partiellement aux attentes";
            case 2 -> "répondent pleinement aux attentes";
            case 3 -> "dépassent les attentes";
            case 4 -> "dépassent de beaucoup les attentes";
            default -> "ERROR";
        };
    }

    private String getAnswerString(int value) {
        return switch (value) {
            case 0 -> "Impossible de se prononcer";
            case 1 -> "Totalement en désaccord";
            case 2 -> "Plutôt en désaccord";
            case 3 -> "Plutôt en accord";
            case 4 -> "Totalement en accord";
            default -> "ERROR";
        };
    }
}
