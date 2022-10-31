package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.payload.response.ApplicationDto;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Manager;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.repository.user.ManagerRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import com.osekiller.projet.service.ContractService;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ContractServiceImpl implements ContractService {
    OfferRepository offerRepository ;
    ManagerRepository managerRepository ;
    StudentRepository studentRepository ;
    @Override
    public Resource generateContract(List<String> contractTasks, long offerId, long studentId, long managerId) throws IOException {
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;
        Manager manager = managerRepository.findById(managerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;

        //TODO lancer exception si application/offer pas dans le student

        PDDocument pdfDocument = PDDocument.load(new File("Templates/contratTemplateFieldsFinal.pdf"));
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

        PDPage pdPage = new PDPage() ;

        pdfDocument.addPage(pdPage);

        PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, pdPage);

        contentStream.beginText();

        contentStream.newLineAtOffset(25, 700);

        contentStream.setFont(PDType1Font.TIMES_ROMAN, 24);

        contentStream.setLeading(14.5f);

        contentStream.showText("TACHES ET RESPONSABILITES DU STAGIAIRE");

        for (String task : contractTasks) {
            contentStream.newLine() ;
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.showText("-" + task);
        }

        contentStream.endText();

        contentStream.close();

        acroForm.flatten();
        pdfDocument.save(new File("final.pdf"));

        return null ;
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
