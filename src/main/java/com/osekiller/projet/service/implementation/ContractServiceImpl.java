package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.payload.request.EvaluationDto;
import com.osekiller.projet.controller.payload.request.QuestionAnswerDto;
import com.osekiller.projet.controller.payload.request.StudentEvaluationDto;
import com.osekiller.projet.controller.payload.request.StudentEvaluationSection;
import com.osekiller.projet.controller.payload.response.ApplicationDto;
import com.osekiller.projet.controller.payload.response.ContractDto;
import com.osekiller.projet.controller.payload.response.ContractToEvaluateDto;
import com.osekiller.projet.controller.payload.response.EvaluationSimpleDto;
import com.osekiller.projet.model.Contract;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Manager;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.pdfutils.PdfUtils;
import com.osekiller.projet.repository.ContractRepository;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.repository.user.ManagerRepository;
import com.osekiller.projet.repository.user.SignatoryRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import com.osekiller.projet.service.ContractService;
import com.osekiller.projet.service.NotificationsService;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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

    NotificationsService notificationsService;
    PdfUtils pdfUtils;

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

        pdfUtils.writeValuesInPdf(student, offer, manager, pdfDocument);

        PDPage pdPageTasks = pdfDocument.getPage(2) ;

        PDPageContentStream contentStreamTasksPage = new PDPageContentStream(pdfDocument, pdPageTasks); //Create Stream

        pdfUtils.writeTasksInPdf(contractTasks, contentStreamTasksPage, pdPageTasks);

        contentStreamTasksPage.close();  //Close Stream

        PDPage pdPageSignatures = pdfDocument.getPage(3) ;

        PDPageContentStream contentStreamTasksSignatures = new PDPageContentStream(pdfDocument, pdPageSignatures);  //Create Stream

        pdfUtils.writePageTitle(contentStreamTasksSignatures, "SIGNATURES", pdPageSignatures);

        contentStreamTasksSignatures.close(); //Close Stream

        PDDocumentInformation pdd = pdfDocument.getDocumentInformation() ;
        pdd.setTitle(offer.getOwner().getName() + "-" + student.getName() + "-Contract");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream() ;
        pdfDocument.save(byteArrayOutputStream);

        pdfDocument.close();

        Contract contract = new Contract(student, offer, manager) ;
        contract.setPdf(byteArrayOutputStream.toByteArray());
        contractRepository.save(contract) ;

        notificationsService.addNotification(studentId,
                "Un contrat pour l'offre de " + offer.getOwner().getName() + " a été généré");

        notificationsService.addNotification(offer.getOwner().getId(),
                "Un contrat pour l'offre de " + student.getName() + " a été généré");

        return new ByteArrayResource(byteArrayOutputStream.toByteArray());
    }



    public List<ApplicationDto> getAcceptedApplications() {
        List<ApplicationDto> dtos = new ArrayList<>();
        System.out.println("There is");
        offerRepository.findAllByHasAcceptedApplicants().forEach(
                offer -> offer.getAcceptedApplicants().forEach(
                        student -> {
                            System.out.println("There is");
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
    public Resource getInternEvaluation(long contractId) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;
        return new ByteArrayResource(contract.getStudentEvaluationPdf()) ;
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

        pdfUtils.generateSignaturePagePdf(pdf, page, contract);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pdf.save(byteArrayOutputStream);
        pdf.close();
        contract.setPdf(byteArrayOutputStream.toByteArray());
        contractRepository.save(contract);
        return new ByteArrayResource(byteArrayOutputStream.toByteArray());
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
    public List<ContractDto> getContractWithInternEvaluations() {
        return contractRepository.findAllByStudentEvaluationPdfIsNotNull().stream().map(ContractDto::from).toList();
    }

    @Override
    public void evaluateIntership(Long contractId, EvaluationDto dto) throws IOException {
        Contract contract = contractRepository.findById(contractId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;

        if (contract.getEvaluationPdf() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT) ;
        }

        PDDocument pdfDocument = PDDocument.load(new File("Templates/êvaluation du milieu de stage.pdf"));

        pdfUtils.writeValuesInEvaluationPdf(dto, contract, pdfDocument);

        pdfUtils.writeQuestionsAndAnswers(dto, pdfDocument) ;

        pdfUtils.writeObservations(dto, pdfDocument);

        PDDocumentInformation pdd = pdfDocument.getDocumentInformation() ;
        pdd.setTitle(contract.getOffer().getOwner().getName() + "-" + contract.getStudent().getName() + "-Evaluation");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream() ;
        pdfDocument.save(byteArrayOutputStream);
        pdfDocument.save("./CV/test.pdf");

        pdfDocument.close();

        contract.setEvaluationPdf(byteArrayOutputStream.toByteArray());
        contractRepository.save(contract) ;

        notificationsService.addNotification(contract.getManager().getId(),
                "Le milieu de stage pour le contrat entre " + contract.getOffer().getOwner().getName() +
                "et " + contract.getStudent().getName() + " a été évalué");
    }



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

    @Override
    public void evaluateIntern(long contractId, StudentEvaluationDto dto) throws IOException {
        Contract contract = contractRepository.findById(contractId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;


        if (contract.getStudentEvaluationPdf() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT) ;
        }

        PDDocument pdfDocument = PDDocument.load(new File("Templates/Grille dévaluation du stagiaire_Entreprise.pdf"));

        pdfUtils.writeValuesInStudentEvaluation(dto, contract, pdfDocument);

        pdfUtils.writeSectionEvaluation(dto.productivity(), 1, pdfDocument, "PRODUCTIVITÉ");

        pdfUtils.writeSectionEvaluation(dto.workQuality(), 2, pdfDocument, "QUALITÉ DU TRAVAIL");

        pdfUtils.writeSectionEvaluation(dto.interpersonalQuality(), 3, pdfDocument, "QUALITÉS DES RELATIONS INTERPERSONNELLES");

        pdfUtils.writeSectionEvaluation(dto.personalAbility(), 4, pdfDocument, "HABILETÉS PERSONNELLES");

        pdfUtils.writeGlobalAppreciation(dto, 5, pdfDocument) ;

        pdfUtils.writeNextInternship(dto, 6, pdfDocument);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream() ;
        pdfDocument.save(byteArrayOutputStream);
        pdfDocument.save("./CV/testIntern.pdf");

        pdfDocument.close();

        contract.setStudentEvaluationPdf(byteArrayOutputStream.toByteArray());
        contractRepository.save(contract) ;

    }



    @Override
    public Resource getReport(long contractId) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;
        if (contract.getReport() == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND) ;
        return new ByteArrayResource(contract.getReport());
    }
    
    public void saveReport(MultipartFile file, long contractId, long studentId) throws IOException {
        Contract contract = contractRepository.findById(contractId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;
        if (contract.getStudent().getId() != studentId) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED) ;

        contract.setReport(file.getBytes());
        contractRepository.save(contract) ;
    }
}
