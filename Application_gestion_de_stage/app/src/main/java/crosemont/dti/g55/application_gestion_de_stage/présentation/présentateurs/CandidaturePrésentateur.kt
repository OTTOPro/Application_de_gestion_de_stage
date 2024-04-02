package crosemont.dti.g55.application_gestion_de_stage.présentation.présentateurs

import crosemont.dti.g55.application_gestion_de_stage.domaine.Candidature
import crosemont.dti.g55.application_gestion_de_stage.ecran_candidatures_et_offres_sauvegardees
import crosemont.dti.g55.application_gestion_de_stage.présentation.modèles.IModèle
import crosemont.dti.g55.application_gestion_de_stage.présentation.modèles.Modèle
import crosemont.dti.g55.application_gestion_de_stage.sourceDeDonnées.SourceDeDonnéesException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CandidaturePrésentateur(val vue: ecran_candidatures_et_offres_sauvegardees,  modèle: IModèle = Modèle.instance) {

    private val _modèle =modèle
    private var candidatures: List<Candidature> = emptyList()


    private fun chargerToutesLesCandidatures() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                withContext(Dispatchers.Main) {
                    vue.afficherEcranChargement(true)
                }
                delay(1000)
                candidatures = _modèle.obtenirCandidatures()
                withContext(Dispatchers.Main) {
                    vue.afficherCandidatures(candidatures)
                    vue.afficherEcranChargement(false)
                }
            } catch (e: SourceDeDonnéesException) {
                withContext(Dispatchers.Main) {
                    vue.afficherErreur(e.message ?: "Erreur inconnue")
                    vue.afficherEcranChargement(false)
                }
            }
        }
    }

    private fun supprimerCandidature(code_candidature: Int){
        CoroutineScope(Dispatchers.IO).launch {
            try {

                _modèle.supprimerCandidature(code_candidature)

                candidatures = candidatures.filter { it.codeCandidature != code_candidature }

                withContext(Dispatchers.Main) {
                    vue.afficherCandidatures(candidatures)
                }
            }catch (e: SourceDeDonnéesException) {
                withContext(Dispatchers.Main) {
                    vue.afficherErreur( "Erreur lors de la suppression")
                }
            }
        }
    }

    fun obtenirCandidatures() {
        chargerToutesLesCandidatures()
    }

    fun supprimerLaCandidature(code_candidature: Int){
        supprimerCandidature(code_candidature)
    }

}