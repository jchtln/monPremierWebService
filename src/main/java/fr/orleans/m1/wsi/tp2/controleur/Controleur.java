package fr.orleans.m1.wsi.tp2.controleur;

import fr.orleans.m1.wsi.tp2.modele.Etudiant;
import fr.orleans.m1.wsi.tp2.modele.EtudiantInexistantException;
import fr.orleans.m1.wsi.tp2.modele.FacadePromotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/mpws")
public class Controleur {

    @Autowired
    FacadePromotion facadePromotion;

    @PostMapping(value = "/etudiant")
    public ResponseEntity<String> creerEtudiant(@RequestParam String nom,@RequestParam String prenom, @RequestParam String adresse) {

            String numeroEtudiant = facadePromotion.enregistrerEtudiant(nom,prenom,adresse);
            return ResponseEntity.created(URI.create("http://localhost:8080/mpws/etudiant/"+numeroEtudiant))
                    .body("L'étudiant a bien été crée !");

    }

    @GetMapping("/etudiant/{numeroEtudiant}")
    public ResponseEntity<Etudiant> getEtudiantById(@PathVariable String numeroEtudiant) {
        try {
            Etudiant etudiant = this.facadePromotion.getEtudiantById(numeroEtudiant);
            return ResponseEntity.ok(etudiant);

        } catch (EtudiantInexistantException e) {
            return  ResponseEntity.notFound().build();
        }
    }





    @GetMapping("/etudiant")
    public ResponseEntity<Collection<Etudiant>> GetAllEtudiants(){

            return ResponseEntity.ok(facadePromotion.getEtudiants());
    }
}
