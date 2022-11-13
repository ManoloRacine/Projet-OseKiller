package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.payload.request.EvaluationDto;
import com.osekiller.projet.controller.payload.request.QuestionAnswerDto;
import com.osekiller.projet.controller.payload.response.ApplicationDto;
import com.osekiller.projet.controller.payload.response.ContractDto;
import com.osekiller.projet.controller.payload.response.ContractToEvaluateDto;
import com.osekiller.projet.controller.payload.response.EvaluationSimpleDto;
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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Manager manager = managerRepository.findById(managerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;

        if (!offer.getApplicants().contains(student)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND) ;
        }

        if (contractRepository.findByStudent_IdAndOffer_Id(studentId, offerId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT) ;
        }

        PDDocument pdfDocument = PDDocument.load(new File("Templates/contratTemplateNewPage.pdf"));

        writeValuesInPdf(student, offer, manager, pdfDocument);

        writeTasksInPdf(contractTasks, pdfDocument);

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

    private void writeValuesInPdf(Student student, Offer offer, Manager manager, PDDocument pdfDocument) throws IOException {

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

    private void writeTasksInPdf(List<String> contractTasks, PDDocument pdfDocument) throws IOException {
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
                        student -> {
                            Optional<Contract> contract = contractRepository.findByStudent_IdAndOffer_Id(student.getId(), offer.getId());
                            dtos.add(ApplicationDto.from(
                                    offer,
                                    student,
                                    contract.orElse(null)
                            )
                            );
                        })
                );
        return dtos;
    }

    public List<ContractDto> getContracts() {
        List<ContractDto> dtos = new ArrayList<>() ;
        contractRepository.findAll().forEach(
                contract -> dtos.add(ContractDto.from(contract))
        );
        return dtos ;
    }

    public Resource getContract(long contractId) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;
        return new ByteArrayResource(contract.getPdf()) ;
    }

    @Override
    public List<ContractToEvaluateDto> getUnEvaluatedContracts() {
        List<ContractToEvaluateDto> dtos = new ArrayList<>() ;
        contractRepository.findAllByEvaluationPdfIsNull().stream().forEach(contract -> dtos.add(ContractToEvaluateDto.from(contract)));
        return dtos ;
    }

    @Override
    public void evaluateIntership(Long contractId, EvaluationDto dto) throws IOException {
        Contract contract = contractRepository.findById(contractId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;

        if (contract.getEvaluationPdf() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT) ;
        }

        PDDocument pdfDocument = PDDocument.load(new File("Templates/êvaluation du milieu de stage.pdf"));

        writeValuesInEvaluationPdf(dto, contract, pdfDocument);

        writeQuestionsAndAnswers(dto, pdfDocument) ;

        writeObservations(dto, pdfDocument);

        PDDocumentInformation pdd = pdfDocument.getDocumentInformation() ;
        pdd.setTitle(contract.getOffer().getOwner().getName() + "-" + contract.getStudent().getName() + "-Evaluation");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream() ;
        pdfDocument.save(byteArrayOutputStream);
        pdfDocument.save("./CV/test.pdf");

        pdfDocument.close();

        contract.setEvaluationPdf(byteArrayOutputStream.toByteArray());
        contractRepository.save(contract) ;
    }



    private void writeValuesInEvaluationPdf(EvaluationDto evaluationDto, Contract contract, PDDocument pdfDocument) throws IOException {

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

    private void writeQuestionsAndAnswers(EvaluationDto dto, PDDocument pdfDocument) throws IOException {
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

    private void writeObservations(EvaluationDto dto, PDDocument pdfDocument) throws IOException {
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
                if (!workShift.get(0).equals("") && !workShift.get(1).equals("")) {
                    addParagraph(contentStream, width, 0, -FONT_SIZE, workShift.get(0) + " - " + workShift.get(1), true);
                }
            }
        }

        addParagraph(contentStream, width, 0, -FONT_SIZE, "Date : " + LocalDate.now(), true);

        contentStream.endText();

        contentStream.close();
    }

    @Override
    public List<EvaluationSimpleDto> getEvaluations() {
        List<EvaluationSimpleDto> dtos = new ArrayList<>() ;
        contractRepository.findAllByEvaluationPdfIsNotNull().forEach(
                contract -> dtos.add(EvaluationSimpleDto.from(contract))
        );
        return dtos ;
    }

    @Override
    public Resource getEvaluationPdf(Long contractId) {
        return new ByteArrayResource(contractRepository.findById(contractId).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getEvaluationPdf()) ;
    }


}
