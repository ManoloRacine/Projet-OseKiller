package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.payload.response.ApplicationDto;
import com.osekiller.projet.model.Contract;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Manager;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.ContractRepository;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.repository.user.ManagerRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import com.osekiller.projet.service.ContractService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ContractServiceImpl implements ContractService {
    OfferRepository offerRepository ;
    ManagerRepository managerRepository ;
    StudentRepository studentRepository ;
    ContractRepository contractRepository ;

    private static final PDFont FONT = PDType1Font.TIMES_ROMAN;
    private static final float FONT_SIZE = 12;
    private static final float LEADING = -1.5f * FONT_SIZE;

    @Override
    public Resource generateContract(List<String> contractTasks, long offerId, long studentId, long managerId) throws IOException {
        Offer offer = offerRepository.findByIdAndFetchApplicants(offerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;
        log.info("offer loaded");
        Manager manager = managerRepository.findById(managerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;
        log.info("manager loaded");
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;
        log.info("student loaded");

        //if (!offer.getApplicants().contains(student)) {
        //    throw new ResponseStatusException(HttpStatus.NOT_FOUND) ;
        //}

        PDDocument pdfDocument = PDDocument.load(new File("Templates/contratTemplateNewPage.pdf"));
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

        PDPage pdPage = pdfDocument.getPage(2) ;

        PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, pdPage);



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
            addParagraph(contentStream, width, 0, -FONT_SIZE, "-" + task, true);
        }

        contentStream.endText();

        contentStream.close();

        acroForm.flatten() ;

        PDDocumentInformation pdd = pdfDocument.getDocumentInformation() ;

        pdd.setTitle(offer.getOwner().getName() + "-" + student.getName() + "-Contract");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream() ;
        pdfDocument.save(byteArrayOutputStream);

        pdfDocument.close();

        Contract contract = new Contract(student, offer, manager) ;

        contract.setPdf(byteArrayOutputStream.toByteArray());

        contractRepository.save(contract) ;

        return new ByteArrayResource(byteArrayOutputStream.toByteArray());
    }

    //https://memorynotfound.com/apache-pdfbox-adding-multiline-paragraph/
    private static void addParagraph(PDPageContentStream contentStream, float width, float sx,
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

    //https://memorynotfound.com/apache-pdfbox-adding-multiline-paragraph/
    private static List<String> parseLines(String text, float width) throws IOException {
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

    public List<ApplicationDto> getAcceptedApplications() {
        List<ApplicationDto> dtos = new ArrayList<>();
        offerRepository.findAllByHasAcceptedApplicants().forEach(
                offer -> offer.getAcceptedApplicants().forEach(
                        student -> dtos.add(ApplicationDto.from(offer, student))
                )
        );
        return dtos;
    }
}
