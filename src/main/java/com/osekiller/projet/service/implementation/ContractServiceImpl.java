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
import com.osekiller.projet.model.user.Signatory;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.ContractRepository;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.repository.user.ManagerRepository;
import com.osekiller.projet.repository.user.SignatoryRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import com.osekiller.projet.service.ContractService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
    OfferRepository offerRepository;
    ManagerRepository managerRepository;
    StudentRepository studentRepository;
    ContractRepository contractRepository ;
    SignatoryRepository signatoryRepository;


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

        PDPage pdPageTasks = pdfDocument.getPage(2) ;

        PDPageContentStream contentStreamTasksPage = new PDPageContentStream(pdfDocument, pdPageTasks); //Create Stream

        writeTasksInPdf(contractTasks, contentStreamTasksPage, pdPageTasks);

        contentStreamTasksPage.close();  //Close Stream

        PDPage pdPageSignatures = pdfDocument.getPage(3) ;

        PDPageContentStream contentStreamTasksSignatures = new PDPageContentStream(pdfDocument, pdPageSignatures);  //Create Stream

        writePageTitle(contentStreamTasksSignatures, "SIGNATURES", pdPageSignatures);

        contentStreamTasksSignatures.close(); //Close Stream

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

    private void writePageTitle(PDPageContentStream contentStream, String Title,  PDPage page) throws IOException {
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

    private void writeTasksInPdf(List<String> contractTasks, PDPageContentStream contentStream, PDPage pdPage) throws IOException {

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
    public Resource signContract(long contractId, long signatoryId) throws IOException {
        Contract contract = contractSignatoryGuardClause(contractId, signatoryId);

        if(!hasSignature(signatoryId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        PDDocument pdf = PDDocument.load(contract.getPdf());
        PDPage page = pdf.getPage(3);

        return signPdf(signatoryId, contract, pdf, page);
    }

    @Override
    public Resource signContract(long contractId, long signatoryId, MultipartFile signature) throws IOException {
        Contract contract = contractSignatoryGuardClause(contractId, signatoryId);

        PDDocument pdf = PDDocument.load(contract.getPdf());
        PDPage page = pdf.getPage(3);

        if(contract.getManager().getId().equals(signatoryId)){
            contract.getManager().setSignature(signature.getBytes());
        }

        if(contract.getStudent().getId().equals(signatoryId)){
            contract.getStudent().setSignature(signature.getBytes());
        }

        if(contract.getOffer().getOwner().getId().equals(signatoryId)){
            contract.getOffer().getOwner().setSignature(signature.getBytes());
        }

        contractRepository.save(contract);

        return signPdf(signatoryId, contract, pdf, page);
    }

    public Contract contractSignatoryGuardClause(long contractId, long signatoryId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        boolean signatoryIsNotInContract = !(
                contract.getManager().getId().equals(signatoryId) ||
                        contract.getStudent().getId().equals(signatoryId) ||
                        contract.getOffer().getOwner().getId().equals(signatoryId)
        );

        if(signatoryIsNotInContract){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return contract;
    }

    private Resource signPdf(long signatoryId, Contract contract, PDDocument pdf, PDPage page) throws IOException {

        if(contract.getManager().getId().equals(signatoryId)){
            if(contract.getManagerSigningDate() != null){
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
            contract.setManagerSigningDate(LocalDate.now());
        }

        if(contract.getStudent().getId().equals(signatoryId)){
            if(contract.getStudentSigningDate() != null){
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
            contract.setStudentSigningDate(LocalDate.now());
        }

        if(contract.getOffer().getOwner().getId().equals(signatoryId)){
            if(contract.getCompanySigningDate() != null){
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
            contract.setCompanySigningDate(LocalDate.now());
        }

        generateSignaturePagePdf(pdf, page, contract);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pdf.save(byteArrayOutputStream);
        pdf.close();
        contract.setPdf(byteArrayOutputStream.toByteArray());
        contractRepository.save(contract);
        return new ByteArrayResource(byteArrayOutputStream.toByteArray());
    }

    private void generateSignaturePagePdf(PDDocument pdf, PDPage page, Contract contract) throws IOException {
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

    @Override
    public boolean hasSignature(long signatoryId) {
        return signatoryRepository.findByIdAndSignatureIsNotNull(signatoryId).isPresent();
    }

    @Override
    public List<ContractDto> getContractsByManagerId(long id) {
        return contractRepository.findAllByManager_Id(id).stream().map(ContractDto::from).toList();
    }

    @Override
    public List<ContractDto> getContractsByStudentId(long id) {
        return contractRepository.findAllByStudent_Id(id).stream().map(ContractDto::from).toList();
    }

    @Override
    public List<ContractDto> getContractsByCompanyId(long id) {
        return contractRepository.findAllByOffer_Owner_Id(id).stream().map(ContractDto::from).toList();
    }

    public List<ContractToEvaluateDto> getUnevaluatedContracts() {
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
                if (workShift.get(0) != null && workShift.get(1) != null) {
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
