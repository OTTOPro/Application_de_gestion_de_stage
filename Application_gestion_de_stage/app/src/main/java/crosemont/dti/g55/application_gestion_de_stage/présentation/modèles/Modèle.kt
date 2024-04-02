package crosemont.dti.g55.application_gestion_de_stage.présentation.modèles

import crosemont.dti.g55.application_gestion_de_stage.domaine.Candidature
import crosemont.dti.g55.application_gestion_de_stage.domaine.Etudiant
import crosemont.dti.g55.application_gestion_de_stage.domaine.Offre
import crosemont.dti.g55.application_gestion_de_stage.domaine.Stage
import crosemont.dti.g55.application_gestion_de_stage.sourceDeDonnées.DecodeurJSON
import crosemont.dti.g55.application_gestion_de_stage.sourceDeDonnées.SourceDeDonnees
import crosemont.dti.g55.application_gestion_de_stage.sourceDeDonnées.SourceDeDonneesHTTP
import crosemont.dti.g55.application_gestion_de_stage.sourceDeDonnées.Source_bidon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.CompletableFuture

class Modèle() : IModèle {

    private var token : String = ""
    private var tokenId : String = ""
    private var avatarUrl : String = ""
    var source: SourceDeDonnees = Source_bidon()

    companion object {
        var instance = Modèle()
    }

    override suspend fun login(courriel: String, mot_de_passe: String): String? {
        token = source.effectuerLogin( courriel, mot_de_passe )
        tokenId = source.effectuerLoginAvatar(courriel, mot_de_passe)
        avatarUrl = DecodeurJSON.décoderJsonVersAvatarUrl(tokenId)
        return token
    }

    override fun obtenirOffresSauvegardées(offres: List<Offre>): List<Offre> {
        return offres.filter { it.offreSauvegardée }
    }

    override suspend fun obtenirToutesLesOffres(): List<Offre> {
        return source.obtenirDonnéesOffres()
    }

    override suspend fun ajouterCandidature(code_offre: Int) {
        source.AjouterUneCandidature(token,code_offre)
    }

    override suspend fun supprimerCandidature(code_offre: Int) {
        source.supprimerCandidature(token,code_offre)
    }

    override suspend fun obtenirEtudiant(): List<Etudiant> {
        return source.obtenirDonnéesEtudiant(token)
    }

    override suspend fun obtenirAvatar(): String {
        return avatarUrl
    }

    override suspend fun obtenirCandidatures(): List<Candidature> {
        return source.obtenirDonnéesCandidatures(token)
    }

    override suspend fun obtenirStage(): List<Stage> {
        return source.obtenirStage(token)
    }

    fun verifierEtudiant(callback: (Etudiant?) -> Unit) {
        val future = CompletableFuture<Etudiant?>()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val etudiants = source.obtenirDonnéesEtudiant(token)
                val etudiant = etudiants.firstOrNull()
                future.complete(etudiant)
            } catch (e: Exception) {
                future.complete(null)
            }
        }

        future.thenAccept(callback)
    }

}