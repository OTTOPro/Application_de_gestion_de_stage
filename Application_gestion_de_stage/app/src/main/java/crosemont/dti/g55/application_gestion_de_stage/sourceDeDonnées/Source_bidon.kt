package crosemont.dti.g55.application_gestion_de_stage.sourceDeDonnées

import crosemont.dti.g55.application_gestion_de_stage.domaine.Candidature
import crosemont.dti.g55.application_gestion_de_stage.domaine.Employeur
import crosemont.dti.g55.application_gestion_de_stage.domaine.Entreprise
import crosemont.dti.g55.application_gestion_de_stage.domaine.Etudiant
import crosemont.dti.g55.application_gestion_de_stage.domaine.ModeEmploi
import crosemont.dti.g55.application_gestion_de_stage.domaine.Offre

import crosemont.dti.g55.application_gestion_de_stage.domaine.Stage
import crosemont.dti.g55.application_gestion_de_stage.domaine.ProfilInformatique
import crosemont.dti.g55.application_gestion_de_stage.domaine.StageProgression
import crosemont.dti.g55.application_gestion_de_stage.domaine.ÉtatCandiature


class Source_bidon(): SourceDeDonnees {

    companion object {

        var etudiants: MutableList<Etudiant> = mutableListOf(
            Etudiant(
                codeUtilisateur = "123",
                nomUtilisateur = "Nom",
                prénomUtilisateur = "Prénom",
                courrielUtilisateur = "nom.prenom@example.com",
                téléphoneUtilisateur = "123456789",
                profilInformatique = ProfilInformatique.PROGRAMMATION,
                stageIntégration = true,
                adresseÉtudiant = "123 Rue de l'Étudiant"
            )
        )

        var offres: MutableList<Offre> = mutableListOf(
            Offre(
                idOffre = 1,
                employeur = Employeur(
                    codeUtilisateur = "123",
                    codeEntreprise = Entreprise(1,"CGI", "456 Rue de l'Entreprise"),
                    nomUtilisateur = "NomEmployeur",
                    prénomUtilisateur = "PrénomEmployeur",
                    courrielUtilisateur = "nom.employeur@example.com",
                    téléphoneUtilisateur = "987654321"),
                titrePoste = "Développeur Android",
                modeEmploi = ModeEmploi.PRÉSENTIEL,
                description = "Description de l'offre 1",
                offreSauvegardée = false,
                candidatures = mutableListOf()
            )
        )

        var candidatures: MutableList<Candidature> = mutableListOf(
            Candidature(1,offres[0], etudiants[0], ÉtatCandiature.Accepté )
        )

    }

    override suspend fun obtenirDonnéesCandidatures(token:String): List<Candidature> {
        return candidatures
    }

    override suspend fun obtenirDonnéesOffres(): List<Offre> {
        return offres
    }

    override suspend fun obtenirDonnéesEtudiant(token: String): List<Etudiant> {
        return etudiants
    }

    override suspend fun AjouterUneCandidature(code_etudiant: String, code_offre: Int): Boolean {
        val etudiant = etudiants.find { it.codeUtilisateur == code_etudiant }
        val offre = offres.find { it.idOffre == code_offre }

        if (etudiant != null && offre != null) {
            val nouvelleCandidature = Candidature(
                codeCandidature = candidatures.size + 1,
                offre = offre,
                etudiant = etudiant,
                étatCandiature = ÉtatCandiature.Encours
            )

            candidatures.add(nouvelleCandidature)

            return true
        }

        return false
    }

    override suspend fun supprimerCandidature(token: String, codeCandidature: Int): Boolean {
       return  candidatures.remove(candidatures.find { it.codeCandidature == codeCandidature })
    }


    override suspend fun obtenirUrl(lien: String) : String{
         return "Url"
     }

     override suspend fun effectuerLogin(courriel: String, mot_de_passe: String): String {
         return "Login"
     }

     override suspend fun effectuerLoginAvatar(courriel: String, mot_de_passe: String): String {
         return "avatar"
     }

    override suspend fun obtenirStage(token: String): List<Stage> {
        return mutableListOf()
    }

}