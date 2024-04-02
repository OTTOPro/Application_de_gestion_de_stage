package crosemont.dti.g55.application_gestion_de_stage.présentation.modèles

import crosemont.dti.g55.application_gestion_de_stage.domaine.Candidature
import crosemont.dti.g55.application_gestion_de_stage.domaine.Etudiant
import crosemont.dti.g55.application_gestion_de_stage.domaine.Offre
import crosemont.dti.g55.application_gestion_de_stage.domaine.Stage

interface IModèle {

    suspend fun login(courriel : String, mot_de_passe : String ) : String?

    fun obtenirOffresSauvegardées(offres: List<Offre>): List<Offre>

    suspend fun obtenirToutesLesOffres(): List<Offre>

    suspend fun ajouterCandidature(code_offre: Int)

    suspend fun supprimerCandidature(code_offre: Int)

    suspend fun obtenirEtudiant(): List<Etudiant>

    suspend fun obtenirAvatar(): String

    suspend fun obtenirCandidatures(): List<Candidature>
    suspend fun obtenirStage(): List<Stage>

}