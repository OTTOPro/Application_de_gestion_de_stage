package crosemont.dti.g55.application_gestion_de_stage.sourceDeDonnées

import crosemont.dti.g55.application_gestion_de_stage.domaine.Candidature
import crosemont.dti.g55.application_gestion_de_stage.domaine.Etudiant
import crosemont.dti.g55.application_gestion_de_stage.domaine.Offre
import crosemont.dti.g55.application_gestion_de_stage.domaine.Stage


class SourceDeDonnéesException( message: String) : Exception( message ) {}
interface SourceDeDonnees {

    suspend fun obtenirDonnéesCandidatures(token:String) : List<Candidature>

    suspend fun obtenirDonnéesOffres(): List<Offre>

    suspend fun obtenirDonnéesEtudiant(token:String): List<Etudiant>

    suspend fun AjouterUneCandidature(token: String,code_offre: Int): Boolean

    suspend fun supprimerCandidature(token: String, codeCandidature: Int): Boolean

    suspend fun obtenirUrl(lien: String) : String

    suspend fun effectuerLogin(courriel: String, mot_de_passe: String) : String

    suspend fun effectuerLoginAvatar(courriel: String, mot_de_passe: String) : String

    suspend fun obtenirStage(token: String): List<Stage>

}